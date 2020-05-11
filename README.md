
# react-native-lantern

Flashlight support on React Native

## Warning!!! Support only Android (>= API 23 (>= Android 6.0))

## Getting started

`npm i react-native-lantern`

### Mostly automatic installation

`npx react-native link react-native-lantern` (use `npx react-native unlink react-native-lantern` to uninstall)

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.reactnative.lantern.ReactNativeLanternPackage;` to the imports at the top of the file
2. Append the following lines to `android/settings.gradle`:
      ```
      include ':react-native-lantern'
      project(':react-native-lantern').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-lantern/android')
      ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
      ```
      compile project(':react-native-lantern')
      ```


## Usage
```javascript
import React, { useState, useEffect, useCallback } from 'react';
import { View, Button } from 'react-native';
import lantern from 'react-native-lantern';

const Main = () => {
    const [isDisabled, setDisabled] = useState(true);
    const [isTurnOn, setTurnState] = useState(false);

    useEffect(() => {
        // call on change turn state (fire on subscribe, return current turn state)
        const unsubscribe = lantern.subscribe('onTurn', (event) => setTurnState(event.value));
        return unsubscribe;
    }, []);

    useEffect(() => {
        (async () => {
            // initialize module
            await lantern.ready();
            setDisabled(false);
        })();
    }, []);

    const onPress = useCallback(async () => {
        if (isTurnOn) {
            await lantern.turnOff();
        } else {
            await lantern.turnOn();
        }
        // or `await lantern.turn(!isTurnOn)`
    }, [isTurnOn]);

    return (
        <View>
            <Button title={isTurnOn ? 'Off' : 'On'} onPress={onPress} disabled={isDisabled} />
        </View>
    );
}
```

## API

### ready() -> Promise<void>

  Initialize flashlight. This method should be called at the very beginning, before calling other methods.

### turn(<Boolean>turnState) -> Promise<void>

  Change turn (on/off).

### turnOn() -> Promise<void>

  Turn on flashlight.

### turnOff() -> Promise<void>

  Turn off flashlight.

### subscribe(<String>eventName, <Function>callback) -> <Function>unsubscribe

  Subscribing to event. Use the `onTurn` event to subscribe to a state change `turnState`.

## License

  [MIT](LICENSE.md)
