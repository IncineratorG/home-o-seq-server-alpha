package com.touristskaya.homeoseq.server.services.test_service;

import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.actions.actions_buffer.ActionsBuffer;
import com.touristskaya.homeoseq.common.actions.actions_dispatcher.ActionsDispatcher;
import com.touristskaya.homeoseq.common.actions.actions_processor.ActionsProcessor;
import com.touristskaya.homeoseq.common.notifications.notifications_dispatcher.NotificationsDispatcher;
import com.touristskaya.homeoseq.common.services.service.Service;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.server_actions_dispatcher.ServerActionsDispatcher;
import com.touristskaya.homeoseq.server.services.test_service.actions_processor.TestServiceActionsProcessor;
import com.touristskaya.homeoseq.server.services.test_service.service_description.TestServiceDescription;

import java.util.concurrent.LinkedBlockingQueue;

public class TestService extends Thread implements Service {
    private ActionsDispatcher mActionsDispatcher;
    private NotificationsDispatcher mNotificationsDispatcher;

    private ActionsBuffer mActionsBuffer;
    private LinkedBlockingQueue<Action> mActionsQueue;
    private TestServiceDescription mServiceDescription;
    private ActionsProcessor mActionsProcessor;

    public TestService(ActionsDispatcher actionsDispatcher, NotificationsDispatcher notificationsDispatcher) {
        mActionsDispatcher = actionsDispatcher;
        mNotificationsDispatcher = notificationsDispatcher;

        mServiceDescription = new TestServiceDescription();

        mActionsQueue = new LinkedBlockingQueue<>();

        mActionsProcessor = new TestServiceActionsProcessor(
                mActionsDispatcher,
                mNotificationsDispatcher,
                mServiceDescription
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
        SystemEventsHandler.onInfo("TestService->startService()");

        start();
    }

    @Override
    public void stopService() {
        SystemEventsHandler.onInfo("TestService->stopService()");

        mActionsDispatcher.dispatch(mServiceDescription.actionCreators.stopServiceAction());
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
