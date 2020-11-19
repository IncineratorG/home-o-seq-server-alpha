package com.touristskaya.homeoseq.server.services.cameras_service.actions_processor;

import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.actions.action_handler.ActionHandler;
import com.touristskaya.homeoseq.common.actions.actions_dispatcher.ActionsDispatcher;
import com.touristskaya.homeoseq.common.actions.actions_processor.ActionsProcessor;
import com.touristskaya.homeoseq.common.notifications.notifications_dispatcher.NotificationsDispatcher;
import com.touristskaya.homeoseq.server.services.cameras_service.common.cameras_manager.CamerasManager;
import com.touristskaya.homeoseq.server.services.cameras_service.service_description.CamerasServiceDescription;
import com.touristskaya.old_homoseq.homeoseq.common.system_events_handler.SystemEventsHandler;

public class CamerasServiceActionsProcessor implements ActionHandler {
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
        if (action.getType().equals(mServiceDescription.actionTypes.GET_IMAGE)) {
            action.complete(
                    mServiceDescription.actionResults.getImageActionResult("RESULT_IMAGE")
            );
        } else if (action.getType().equals(mServiceDescription.actionTypes.GET_ALL_CAMERAS)) {
            action.complete(
                    mCamerasManager.getCameras()
            );
        } else if (action.getType().equals(mServiceDescription.actionTypes.GET_CAMERA_IMAGE)) {
            String cameraId = (String) action.getPayload();

            action.complete(
                    mServiceDescription.actionResults.getCameraImageActionResult(
                            mCamerasManager.getCameraImage(cameraId)
                    )
            );
        } else if (action.getType().equals(mServiceDescription.actionTypes.TEST_ACTION)) {
            SystemEventsHandler.onInfo("CAMERAS_SERVICE_THREAD: " + Thread.currentThread().getId());
            action.complete("STRING");
        }
    }
}
