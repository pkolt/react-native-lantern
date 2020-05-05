
package com.reactnative.lantern;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.os.Build;

public class ReactNativeLanternModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private LanternBase lantern;

  public ReactNativeLanternModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;

    // >= API 23 (Android 6.0)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      lantern = new Lantern23(reactContext);
    } else {
      lantern = new Lantern16();
    }

    LifecycleEventListener lifecycleEventListener = new LifecycleEventListener() {
      @Override
      public void onHostResume() {}

      @Override
      public void onHostPause() {}

      @Override
      public void onHostDestroy() {
        lantern.turnOff();
      }
    };

    reactContext.addLifecycleEventListener(lifecycleEventListener);
  }

  @ReactMethod
  private void turn(boolean state) {
    lantern.turn(state);
  }

  @ReactMethod
  public void turnOn() {
    lantern.turnOn();
  }

  @ReactMethod
  public void turnOff() {
    lantern.turnOff();
  }

  @ReactMethod
  public void requestTurnState(Callback cb) {
    cb.invoke(lantern.getTurnState());
  }

  @Override
  public String getName() {
    return "RNReactNativeLantern";
  }
}