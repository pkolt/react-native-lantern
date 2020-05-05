
import { NativeModules, NativeEventEmitter } from 'react-native';

const { ReactNativeLantern } = NativeModules;

const subscribe = (cb) => {
    const eventEmitter = new NativeEventEmitter(ReactNativeLantern);
    const eventListener = eventEmitter.addListener(
        'onChangeTurnState',
        (event) => cb(event.value),
    );
    return () => eventListener.remove();
}

export default {
    ...ReactNativeLantern,
    subscribe,
};
