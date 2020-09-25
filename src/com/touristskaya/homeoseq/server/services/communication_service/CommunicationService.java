package com.touristskaya.homeoseq.server.services.communication_service;

import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.actions.actions_buffer.ActionsBuffer;
import com.touristskaya.homeoseq.common.actions.actions_dispatcher.ActionsDispatcher;
import com.touristskaya.homeoseq.common.notifications.notifications_dispatcher.NotificationsDispatcher;
import com.touristskaya.homeoseq.common.services.service.Service;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.server_actions_dispatcher.ServerActionsDispatcher;
import com.touristskaya.homeoseq.server.services.communication_service.common.client_requests_processor.ClientRequestsProcessor;
import com.touristskaya.homeoseq.server.services.communication_service.common.communication_bridge.firebase_communication_bridge.FirebaseCommunicationBridge;
import com.touristskaya.homeoseq.server.services.communication_service.common.communication_manager.CommunicationManager;
import com.touristskaya.homeoseq.server.services.communication_service.common.communication_manager.firebase_communication_manager.FirebaseCommunicationManager;
import com.touristskaya.homeoseq.server.services.communication_service.service_description.CommunicationServiceDescription;

import java.util.concurrent.LinkedBlockingQueue;

public class CommunicationService extends Thread implements Service {
    private ActionsDispatcher mActionsDispatcher;
    private NotificationsDispatcher mNotificationsDispatcher;

    private ActionsBuffer mActionsBuffer;
    private LinkedBlockingQueue<Action> mActionsQueue;
    private CommunicationServiceDescription mServiceDescription;

    private CommunicationManager mCommunicationManager;
    private ClientRequestsProcessor mClientRequestsProcessor;

    public CommunicationService(ActionsDispatcher actionsDispatcher, NotificationsDispatcher notificationsDispatcher) {
        mActionsDispatcher = actionsDispatcher;
        mNotificationsDispatcher = notificationsDispatcher;

        mServiceDescription = new CommunicationServiceDescription();

        mActionsQueue = new LinkedBlockingQueue<>();

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

        mCommunicationManager = new FirebaseCommunicationManager(new FirebaseCommunicationBridge());

        mClientRequestsProcessor = new ClientRequestsProcessor(mCommunicationManager, mActionsDispatcher);

        mCommunicationManager.onRequestReceived(clientRequest -> mClientRequestsProcessor.process(clientRequest));
    }

    @Override
    public void startService() {
        SystemEventsHandler.onInfo("CommunicationService->startService()");
        start();
    }

    @Override
    public void stopService() {
        SystemEventsHandler.onInfo("CommunicationService->stopService()");
        mCommunicationManager.stop();

        mActionsDispatcher.dispatch(mServiceDescription.actionCreators.stopServiceAction());
    }

    @Override
    public void run() {
        mCommunicationManager.start();

        while (true) {
            try {
                Action action = mActionsQueue.take();

                if (action.getType().equals(mServiceDescription.actionTypes.STOP_SERVICE)) {
                    SystemEventsHandler.onInfo("STOP_COMMUNICATION_SERVICE");
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
