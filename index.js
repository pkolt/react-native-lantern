
import { NativeModules, NativeEventEmitter } from 'react-native';

const { ReactNativeLantern } = NativeModules;

// Not found native module.
if (!ReactNativeLantern) {
    throw new Error(`react-native-lantern: NativeModule.ReactNativeLantern is null. Perform automatic or manual installation of the module. https://github.com/pkolt/react-native-lantern#automatic-installation`);
}

const subscribe = (eventName, callback) => {
    const eventEmitter = new NativeEventEmitter(ReactNativeLantern);
    const eventListener = eventEmitter.addListener(
        eventName,
        (event) => callback(event),
    );
    return () => eventListener.remove();
}

export default {
    ...ReactNativeLantern,
    subscribe,
};
