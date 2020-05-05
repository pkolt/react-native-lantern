package com.reactnative.lantern;

import android.hardware.Camera;

// >= API 16 (Android 4.1)
public final class Lantern16 extends LanternBase {
    private Camera cam;
    private Camera.Parameters camParams;

    public Lantern16() {
        try {
            cam = Camera.open();
            camParams = cam.getParameters();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void turn(boolean state) {
        try {
            if (state) {
                camParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                cam.setParameters(camParams);
                cam.startPreview();
            } else {
                camParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                cam.setParameters(camParams);
                cam.stopPreview();
                cam.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isTurnOn() {
        String flashMode = camParams.getFlashMode();
        return flashMode.equals(Camera.Parameters.FLASH_MODE_TORCH);
    }
}
