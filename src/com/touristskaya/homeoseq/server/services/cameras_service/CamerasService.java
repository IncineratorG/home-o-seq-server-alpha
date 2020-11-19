package com.touristskaya.homeoseq.server.services.cameras_service;

import com.touristskaya.homeoseq.data.common.actions.action_handler.ActionHandler;
import com.touristskaya.homeoseq.data.common.services.service.Service;
import com.touristskaya.homeoseq.data.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.data.specific.server.server_actions_dispatcher.ServerActionsDispatcher;
import com.touristskaya.homeoseq.server.services.cameras_service.actions_processor.CamerasServiceActionsProcessor;
import com.touristskaya.homeoseq.server.services.cameras_service.common.cameras_manager.CamerasManager;
import com.touristskaya.homeoseq.server.services.cameras_service.service_description.CamerasServiceDescription;

import java.util.List;

public class CamerasService extends Service {
    private CamerasServiceDescription mServiceDescription;
    private ServerActionsDispatcher mActionsDispatcher;
    private ActionHandler mActionsHandler;

    public CamerasService(ServerActionsDispatcher dispatcher) {
        mActionsDispatcher = dispatcher;
        mServiceDescription = new CamerasServiceDescription();
        mActionsHandler = new CamerasServiceActionsProcessor(
                this,
                mServiceDescription,
                new CamerasManager()
        );

        initService();
    }

    @Override
    protected ServerActionsDispatcher systemDispatcher() {
        return mActionsDispatcher;
    }

    @Override
    protected List<String> actionTypes() {
        return mServiceDescription.actionTypes.getTypes();
    }

    @Override
    protected String terminateServiceActionType() {
        return mServiceDescription.actionTypes.STOP_SERVICE;
    }

    @Override
    protected ActionHandler actionHandler() {
        return mActionsHandler;
    }

    @Override
    protected void beforeStart() {
        SystemEventsHandler.onInfo("CamerasService->start()");
    }

    @Override
    protected void cleanup() {
        SystemEventsHandler.onInfo("CamerasService->stop()");
    }
}


//package com.touristskaya.homeoseq.server.services.cameras_service;
//
//import com.touristskaya.homeoseq.data.common.actions.action.Action;
//import com.touristskaya.homeoseq.data.common.actions.actions_buffer.ActionsBuffer;
//import com.touristskaya.homeoseq.data.common.actions.actions_dispatcher.ActionsDispatcher;
//import com.touristskaya.homeoseq.data.common.actions.actions_processor.ActionsProcessor;
//import com.touristskaya.homeoseq.data.common.notifications.notifications_dispatcher.NotificationsDispatcher;
//import com.touristskaya.homeoseq.data.common.services.service.Service;
//import com.touristskaya.homeoseq.data.common.system_events_handler.SystemEventsHandler;
//import com.touristskaya.homeoseq.data.specific.server.server_actions_dispatcher.ServerActionsDispatcher;
//import com.touristskaya.homeoseq.server.services.cameras_service.actions_processor.CamerasServiceActionsProcessor;
//import com.touristskaya.homeoseq.server.services.cameras_service.common.cameras_manager.CamerasManager;
//import com.touristskaya.homeoseq.server.services.cameras_service.service_description.CamerasServiceDescription;
//
//import java.util.concurrent.LinkedBlockingQueue;
//
//public class CamerasService extends Thread implements Service {
//    private ActionsDispatcher mActionsDispatcher;
//    private NotificationsDispatcher mNotificationsDispatcher;
//
//    private ActionsBuffer mActionsBuffer;
//    private LinkedBlockingQueue<Action> mActionsQueue;
//    private CamerasServiceDescription mServiceDescription;
//    private ActionsProcessor mActionsProcessor;
//
//    private CamerasManager mCamerasManager;
//
//    public CamerasService(ActionsDispatcher actionsDispatcher, NotificationsDispatcher notificationsDispatcher) {
//        mActionsDispatcher = actionsDispatcher;
//        mNotificationsDispatcher = notificationsDispatcher;
//
//        mServiceDescription = new CamerasServiceDescription();
//
//        mActionsQueue = new LinkedBlockingQueue<>();
//
//        mCamerasManager = new CamerasManager();
//
//        mActionsProcessor = new CamerasServiceActionsProcessor(
//                mActionsDispatcher,
//                mNotificationsDispatcher,
//                mServiceDescription,
//                mCamerasManager
//        );
//
//        mActionsBuffer = new ActionsBuffer(
//                mActionsDispatcher,
//                ServerActionsDispatcher.NEW_ACTION_EVENT,
//                mServiceDescription.getActionTypes()
//        );
//
//        mActionsBuffer.subscribe(
//                ActionsBuffer.NEW_ACTION_AVAILABLE_EVENT,
//                data -> {
//                    try {
//                        mActionsQueue.put(mActionsBuffer.takeLatest());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        SystemEventsHandler.onError(e.getMessage());
//                    }
//                }
//        );
//    }
//
//    @Override
//    public void startService() {
//        SystemEventsHandler.onInfo("CamerasService->startService()");
//
//        start();
//    }
//
//    @Override
//    public void stopService() {
//        SystemEventsHandler.onInfo("CamerasService->stopService()");
//
//        mActionsDispatcher.dispatch(mServiceDescription.actionCreators.stopAction());
//    }
//
//    @Override
//    public void run() {
//        while (true) {
//            try {
//                Action action = mActionsQueue.take();
//
//                boolean stopService = mActionsProcessor.process(action);
//                if (stopService) {
//                    break;
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                SystemEventsHandler.onError(e.getMessage());
//            }
//        }
//    }
//}
