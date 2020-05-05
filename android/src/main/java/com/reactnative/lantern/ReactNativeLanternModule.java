
package com.reactnative.lantern;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.widget.Toast;

public class ReactNativeLanternModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private CameraManager camManager;
  private String camId;
  private boolean turnState = false;

  public ReactNativeLanternModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;

    showText("start init");

    // >= API 23 (Android 6.0)
    try {
      camManager = (CameraManager) reactContext.getSystemService(Context.CAMERA_SERVICE);
      camId = findCameraId();

      CameraManager.TorchCallback torchCallback = new CameraManager.TorchCallback() {
        @Override
        public void onTorchModeChanged(String id, boolean enabled) {
          super.onTorchModeChanged(id, enabled);
          if (camId.equals(id)) {
              turnState = enabled;
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

      showText("end init");
    } catch (Exception e) {
      e.printStackTrace();
    }

    LifecycleEventListener lifecycleEventListener = new LifecycleEventListener() {
      @Override
      public void onHostResume() {}

      @Override
      public void onHostPause() {}

      @Override
      public void onHostDestroy() {
        turnOff();
      }
    };

    reactContext.addLifecycleEventListener(lifecycleEventListener);
  }

  private void showText(String text) {
    Toast.makeText(getReactApplicationContext(), text, Toast.LENGTH_SHORT).show();
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
  private void turn(boolean state) {
    try {
      camManager.setTorchMode(camId, state);
      turnState = state;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @ReactMethod
  public void turnOn() {
    turn(true);
  }

  @ReactMethod
  public void turnOff() {
    turn(false);
  }

  @ReactMethod
  public void requestTurnState(Callback cb) {
    cb.invoke(turnState);
  }

  @Override
  public String getName() {
    return "ReactNativeLantern";
  }
}