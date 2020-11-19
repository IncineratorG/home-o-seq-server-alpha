package com.touristskaya.homeoseq.server.services.cameras_service.service_description.actions.action_creators;

import com.touristskaya.homeoseq.data.common.actions.action.Action;
import com.touristskaya.homeoseq.data.common.actions.action_creators.ActionCreators;
import com.touristskaya.homeoseq.data.common.camera.Camera;
import com.touristskaya.homeoseq.data.common.promise.Promise;
import com.touristskaya.homeoseq.server.services.cameras_service.service_description.actions.action_types.CamerasServiceActionTypes;

import java.awt.image.BufferedImage;
import java.util.List;

public class CamerasServiceActionCreators implements ActionCreators {
    private CamerasServiceActionTypes mActionTypes;

    public CamerasServiceActionCreators() {
        mActionTypes = new CamerasServiceActionTypes();
    }

    public Action stopAction() {
        return new Action(mActionTypes.STOP_SERVICE);
    }

    public Action testAction(Promise<String> result) {
        return new Action(mActionTypes.TEST_ACTION, result);
    }

    public Action getImageAction(Promise<String> result) {
        return new Action(mActionTypes.GET_IMAGE, result);
    }

    public Action getAllCamerasAction(Promise<List<Camera>> result) {
        return new Action(mActionTypes.GET_ALL_CAMERAS, result);
    }

    public Action getCameraImageAction(String cameraId, Promise<BufferedImage> result) {
        return new Action(mActionTypes.GET_CAMERA_IMAGE, cameraId, result);
    }
}
