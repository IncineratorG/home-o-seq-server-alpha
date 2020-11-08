package com.touristskaya.homeoseq.server;

import com.touristskaya.homeoseq.common.actions.actions_dispatcher.ActionsDispatcher;
import com.touristskaya.homeoseq.common.notifications.notifications_dispatcher.NotificationsDispatcher;
import com.touristskaya.homeoseq.common.services.service.Service;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.server_actions_dispatcher.ServerActionsDispatcher;
import com.touristskaya.homeoseq.server.server_notifications_dispatcher.ServerNotificationsDispatcher;
import com.touristskaya.homeoseq.server.services.cameras_service.CamerasService;
import com.touristskaya.homeoseq.server.services.communication_service.CommunicationService;
import com.touristskaya.homeoseq.server.services.test_service.TestService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Server extends Thread {
    private static Server mInstance;

    private LinkedBlockingQueue<String> mServerActionsQueue;
    private static final String STOP_SERVER_ACTION = "STOP_SERVER_ACTION";

    private List<Service> mServices;

    private ActionsDispatcher mActionsDispatcher;
    private NotificationsDispatcher mNotificationsDispatcher;

    public static synchronized Server get() {
        if (mInstance != null) {
            return mInstance;
        }

        mInstance = new Server();
        return mInstance;
    }

    private Server() {
        mActionsDispatcher = new ServerActionsDispatcher();
        mNotificationsDispatcher = new ServerNotificationsDispatcher();

        mServerActionsQueue = new LinkedBlockingQueue<>();

        mServices = new ArrayList<>(
                Arrays.asList(
//                        new CommunicationService(mActionsDispatcher, mNotificationsDispatcher),
                        new CamerasService(mActionsDispatcher, mNotificationsDispatcher),
                        new TestService(mActionsDispatcher, mNotificationsDispatcher)
                )
        );
    }

    public ActionsDispatcher getActionsDispatcher() {
        return mActionsDispatcher;
    }

    public NotificationsDispatcher getNotificationsDispatcher() {
        return mNotificationsDispatcher;
    }

    public void startServer() {
        start();
    }

    public void stopServer() {
        try {
            mServerActionsQueue.put(STOP_SERVER_ACTION);
        } catch (InterruptedException e) {
            e.printStackTrace();
            SystemEventsHandler.onError(e.getMessage());
        }
    }

    @Override
    public void run() {
        SystemEventsHandler.onInfo("SERVER_IS_RUNNING");

        mServices.forEach(Service::startService);

        while (true) {
            try {
                String actionType = mServerActionsQueue.take();

                if (actionType.equals(STOP_SERVER_ACTION)) {
                    mServices.forEach(service -> {
                        service.stopService();

                        if (service instanceof Thread) {
                            Thread threadedService = (Thread) service;
                            try {
                                threadedService.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                SystemEventsHandler.onError(e.getMessage());
                            }
                        }
                    });

                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                SystemEventsHandler.onError(e.getMessage());
            }
        }
    }
}
