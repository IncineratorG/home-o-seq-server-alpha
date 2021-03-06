package com.touristskaya.homeoseq.server.services.cameras_service.service_description.action_results;

import com.touristskaya.homeoseq.common.actions.action_results.ActionResults;
import com.touristskaya.homeoseq.common.camera.Camera;

import java.awt.image.BufferedImage;
import java.util.List;

public class CamerasServiceActionResults implements ActionResults {
    public String getImageActionResult(String result) {
        return result;
    }

    public List<Camera> getAllCamerasActionResult(List<Camera> result) {
        return result;
    }

    public BufferedImage getCameraImageActionResult(BufferedImage image) {
        return image;
    }
}
