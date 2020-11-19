package com.touristskaya.homeoseq.server.services.cameras_service.common.cameras_manager;

import com.touristskaya.homeoseq.data.common.camera.Camera;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CamerasManager {
    private List<Camera> mCameras;

    public CamerasManager() {
        Camera camera1 = new Camera();
        Camera camera2 = new Camera();
        Camera camera3 = new Camera();

        mCameras = new ArrayList<>(
                Arrays.asList(
                        camera1,
                        camera2,
                        camera3
                )
        );
    }

    public List<Camera> getCameras() {
        return mCameras;
    }

    public BufferedImage getCameraImage(String cameraId) {
        if (cameraId == null) {
            return null;
        }

        for (Camera camera : mCameras) {
            if (camera.getId().equals(cameraId)) {
                return camera.getImage();
            }
        }

        return null;
    }
}
