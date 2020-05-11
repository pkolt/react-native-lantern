
import { NativeModules, NativeEventEmitter } from 'react-native';

const { ReactNativeLantern } = NativeModules;

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
