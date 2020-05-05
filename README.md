
# react-native-lantern

## Warning!!! Support only Android (>= API 23 (Android 6.0))

## Getting started

`npm i react-native-lantern`

### Mostly automatic installation

`npx react-native link react-native-lantern`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.ReactNativeLanternPackage;` to the imports at the top of the file
  - Add `new ReactNativeLanternPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
      ```
      include ':react-native-lantern'
      project(':react-native-lantern').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-lantern/android')
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
        const unsubscribe = lantern.subscribe(setTurnState);
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

### ready() -> Promise

  Initialize flashlight. This method should be called at the very beginning, before calling other methods.

### turn(turnState) -> Promise

  Change turn (on/off).

#### `turnState {Boolean}`

### turnOn() -> Promise

  Turn on flashlight.

### turnOff() -> Promise

  Turn off flashlight.

### subscribe(onChangeTurnState) -> unsubscribe

  Subscribing to a state change.

#### `onChangeTurnState {Function}`

  Function that will be called when the `turnState` changes. The first argument of the function `turnState`. Will be called when subscribing.

#### `unsubscribe {Function}`

  Unsubscribe from listening to changes `turnState`.

## License

  [MIT](LICENSE.md)
