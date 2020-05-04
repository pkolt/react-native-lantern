
# react-native-lantern

## Getting started

`$ npm install react-native-lantern --save`

### Mostly automatic installation

`$ react-native link react-native-lantern`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNReactNativeLanternPackage;` to the imports at the top of the file
  - Add `new RNReactNativeLanternPackage()` to the list returned by the `getPackages()` method
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
import RNReactNativeLantern from 'react-native-lantern';

// TODO: What to do with the module?
RNReactNativeLantern;
```
  