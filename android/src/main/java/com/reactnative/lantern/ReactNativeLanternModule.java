
package com.reactnative.lantern;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.PromiseImpl;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;

public class ReactNativeLanternModule extends ReactContextBaseJavaModule {
  private final ReactApplicationContext reactContext;
  private CameraManager camManager = null;
  private String camId = null;
  private boolean isReady = false;

  public ReactNativeLanternModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "ReactNativeLantern";
  }

  @ReactMethod
  private void ready(Promise promise) {
    if (isReady) {
      promise.resolve(null);
      return;
    }

    try {
      camManager = (CameraManager) reactContext.getSystemService(Context.CAMERA_SERVICE);
      camId = findCameraId();

      CameraManager.TorchCallback torchCallback = new CameraManager.TorchCallback() {
        @Override
        public void onTorchModeChanged(String id, boolean enabled) {
          super.onTorchModeChanged(id, enabled);
          if (camId.equals(id)) {
            WritableMap params = Arguments.createMap();
            params.putBoolean("value", enabled);
            reactContext
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit("onTurn", params);
          }
        }

        @Override
        public void onTorchModeUnavailable(String id) {
          super.onTorchModeUnavailable(id);
          if (camId.equals(id)) {
            camId = findCameraId();
          }
        }
      };

      // fires onTorchModeChanged upon register
      camManager.registerTorchCallback(torchCallback, null);

      LifecycleEventListener lifecycleEventListener = new LifecycleEventListener() {
        @Override
        public void onHostResume() {}

        @Override
        public void onHostPause() {}

        @Override
        public void onHostDestroy() {
          turnOff(new PromiseImpl(null, null));
        }
      };

      reactContext.addLifecycleEventListener(lifecycleEventListener);

      isReady = true;
      promise.resolve(null);
    } catch (Exception e) {
      promise.reject(e);
    }
  }

  private String findCameraId() {
    try {
      String[] camIds = camManager.getCameraIdList();
      for (String camId : camIds) {
        CameraCharacteristics characteristics = camManager.getCameraCharacteristics(camId);
        boolean isAvailableFlash = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
        if (isAvailableFlash) {
          return camId;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @ReactMethod
  private void turn(boolean state, Promise promise) {
    try {
      if (!isReady) {
        throw new Exception("First you need to call ready()");
      }
      if (camId == null) {
        throw new Exception("Not found camera");
      }
      camManager.setTorchMode(camId, state);
      promise.resolve(null);
    } catch (Exception e) {
      promise.reject(e);
    }
  }

  @ReactMethod
  public void turnOn(Promise promise) {
    turn(true, promise);
  }

  @ReactMethod
  public void turnOff(Promise promise) {
    turn(false, promise);
  }
}