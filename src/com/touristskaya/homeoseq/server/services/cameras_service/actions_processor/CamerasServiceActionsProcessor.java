package com.touristskaya.homeoseq.server.services.cameras_service.actions_processor;

import com.touristskaya.homeoseq.data.common.actions.action.Action;
import com.touristskaya.homeoseq.data.common.actions.action_handler.ActionsHandler;
import com.touristskaya.homeoseq.data.common.actions.actions_dispatcher.ActionsDispatcher;
import com.touristskaya.homeoseq.data.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.services.cameras_service.common.cameras_manager.CamerasManager;
import com.touristskaya.homeoseq.server.services.cameras_service.service_description.CamerasServiceDescription;

public class CamerasServiceActionsProcessor implements ActionsHandler {
    private ActionsDispatcher mActionsDispatcher;
    private CamerasServiceDescription mServiceDescription;
    private CamerasManager mCamerasManager;

    public CamerasServiceActionsProcessor(ActionsDispatcher actionsDispatcher,
                                          CamerasServiceDescription camerasServiceDescription,
                                          CamerasManager camerasManager) {
        mActionsDispatcher = actionsDispatcher;
        mServiceDescription = camerasServiceDescription;
        mCamerasManager = camerasManager;
    }

    @Override
    public void onAction(Action action) {
        if (action.type().equals(mServiceDescription.actionTypes.GET_IMAGE)) {
            action.complete(
                    mServiceDescription.actionResults.getImageActionResult("RESULT_IMAGE")
            );
        } else if (action.type().equals(mServiceDescription.actionTypes.GET_ALL_CAMERAS)) {
            action.complete(
                    mCamerasManager.getCameras()
            );
        } else if (action.type().equals(mServiceDescription.actionTypes.GET_CAMERA_IMAGE)) {
            String cameraId = (String) action.payload();

            action.complete(
                    mServiceDescription.actionResults.getCameraImageActionResult(
                            mCamerasManager.getCameraImage(cameraId)
                    )
            );
        } else if (action.type().equals(mServiceDescription.actionTypes.TEST_ACTION)) {
            SystemEventsHandler.onInfo("CAMERAS_SERVICE_THREAD: " + Thread.currentThread().getId());
            action.complete("STRING");
        }
    }
}
