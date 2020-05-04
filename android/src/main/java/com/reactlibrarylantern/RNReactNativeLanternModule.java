
package com.reactlibrarylantern;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;

public class RNReactNativeLanternModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNReactNativeLanternModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @TargetApi(Build.VERSION_CODES.M)
  @ReactMethod
  public void turnOn() {
    try {
      Context context = getReactApplicationContext();
      CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
      String[] camIds = cameraManager.getCameraIdList();
      for (String cameraId : camIds) {
        CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
        boolean isAvailableFlash = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
        if (isAvailableFlash) {
          cameraManager.setTorchMode(cameraId, true);
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @TargetApi(Build.VERSION_CODES.M)
  @ReactMethod
  public void turnOff() {
    try {
      Context context = getReactApplicationContext();
      CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
      String[] camIds = cameraManager.getCameraIdList();
      for (String cameraId : camIds) {
        CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
        boolean isAvailableFlash = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
        if (isAvailableFlash) {
          cameraManager.setTorchMode(cameraId, true);
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getName() {
    return "RNReactNativeLantern";
  }
}