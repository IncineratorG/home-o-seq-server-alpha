package com.touristskaya.homeoseq.server.services.cameras_service;

import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.actions.actions_buffer.ActionsBuffer;
import com.touristskaya.homeoseq.common.actions.actions_dispatcher.ActionsDispatcher;
import com.touristskaya.homeoseq.common.actions.actions_processor.ActionsProcessor;
import com.touristskaya.homeoseq.common.notifications.notifications_dispatcher.NotificationsDispatcher;
import com.touristskaya.homeoseq.common.services.service.Service;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.server_actions_dispatcher.ServerActionsDispatcher;
import com.touristskaya.homeoseq.server.services.cameras_service.actions_processor.CamerasServiceActionsProcessor;
import com.touristskaya.homeoseq.server.services.cameras_service.common.cameras_manager.CamerasManager;
import com.touristskaya.homeoseq.server.services.cameras_service.service_description.CamerasServiceDescription;

import java.util.concurrent.LinkedBlockingQueue;

public class CamerasService extends Thread implements Service {
    private ActionsDispatcher mActionsDispatcher;
    private NotificationsDispatcher mNotificationsDispatcher;

    private ActionsBuffer mActionsBuffer;
    private LinkedBlockingQueue<Action> mActionsQueue;
    private CamerasServiceDescription mServiceDescription;
    private ActionsProcessor mActionsProcessor;

    private CamerasManager mCamerasManager;

    public CamerasService(ActionsDispatcher actionsDispatcher, NotificationsDispatcher notificationsDispatcher) {
        mActionsDispatcher = actionsDispatcher;
        mNotificationsDispatcher = notificationsDispatcher;

        mServiceDescription = new CamerasServiceDescription();

        mActionsQueue = new LinkedBlockingQueue<>();

        mCamerasManager = new CamerasManager();

        mActionsProcessor = new CamerasServiceActionsProcessor(
                mActionsDispatcher,
                mNotificationsDispatcher,
                mServiceDescription,
                mCamerasManager
        );

        mActionsBuffer = new ActionsBuffer(
                mActionsDispatcher,
                ServerActionsDispatcher.NEW_ACTION_EVENT,
                mServiceDescription.getActionTypes()
        );

        mActionsBuffer.subscribe(
                ActionsBuffer.NEW_ACTION_AVAILABLE_EVENT,
                data -> {
                    try {
                        mActionsQueue.put(mActionsBuffer.takeLatest());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        SystemEventsHandler.onError(e.getMessage());
                    }
                }
        );
    }

    @Override
    public void startService() {
        SystemEventsHandler.onInfo("CamerasService->startService()");

        start();
    }

    @Override
    public void stopService() {
        SystemEventsHandler.onInfo("CamerasService->stopService()");

        mActionsDispatcher.dispatch(mServiceDescription.actionCreators.stopAction());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Action action = mActionsQueue.take();

                boolean stopService = mActionsProcessor.process(action);
                if (stopService) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                SystemEventsHandler.onError(e.getMessage());
            }
        }
    }
}
