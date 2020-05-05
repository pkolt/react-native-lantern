package com.reactlibrarylantern;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;

// >= API 23 (Android 6.0)
@TargetApi(Build.VERSION_CODES.M)
final public class Lantern23 extends LanternBase {
    private CameraManager camManager;
    private String camId = "";
    private boolean turnState = false;

    public Lantern23(Context context) {
        try {
            camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String findCameraId() {
        String res = "";
        try {
            String[] camIds = camManager.getCameraIdList();
            for (String camId : camIds) {
                CameraCharacteristics characteristics = camManager.getCameraCharacteristics(camId);
                boolean isAvailableFlash = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                if (isAvailableFlash) {
                    res = camId;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public void turn(boolean state) {
        try {
            camManager.setTorchMode(camId, state);
            turnState = state;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isTurnOn() {
        return turnState;
    }
}
