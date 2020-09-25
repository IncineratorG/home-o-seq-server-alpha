package com.touristskaya.homeoseq.server.services.cameras_service.service_description.action_types;

import com.touristskaya.homeoseq.common.actions.action_types.ActionTypes;

import java.util.Arrays;
import java.util.List;

public class CamerasServiceActionTypes implements ActionTypes {
    public final String STOP_SERVICE = "CSA_STOP_SERVICE";
    public final String GET_IMAGE = "CSA_GET_IMAGE";
    public final String GET_ALL_CAMERAS = "CSA_GET_ALL_CAMERAS";
    public final String GET_CAMERA_IMAGE = "CSA_GET_CAMERA_IMAGE";

    @Override
    public List<String> getTypes() {
        return Arrays.asList(
                STOP_SERVICE,
                GET_IMAGE,
                GET_ALL_CAMERAS,
                GET_CAMERA_IMAGE
        );
    }
}
