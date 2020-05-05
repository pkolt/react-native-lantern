
package com.reactlibrarylantern;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import android.os.Build;

public class RNReactNativeLanternModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private LanternBase lantern;

  public RNReactNativeLanternModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;

    // >= API 23 (Android 6.0)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      lantern = new Lantern23(reactContext);
    } else {
      lantern = new Lantern16();
    }
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
  public boolean isTurnOn() {
    return lantern.isTurnOn();
  }

  @Override
  public String getName() {
    return "RNReactNativeLantern";
  }
}