package com.touristskaya.homeoseq.server;

import com.touristskaya.homeoseq.data.common.actions.action_handler.ActionsHandler;
import com.touristskaya.homeoseq.data.common.actions.actions_dispatcher.ActionsDispatcher;
import com.touristskaya.homeoseq.data.common.services.service.Service;
import com.touristskaya.homeoseq.data.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.data.specific.server.server_actions_dispatcher.ServerActionsDispatcher;
import com.touristskaya.homeoseq.server.services.cameras_service.CamerasService;
import com.touristskaya.homeoseq.server.services.communication_service.CommunicationService;
import com.touristskaya.homeoseq.server.services.test_service.TestService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Server extends Service {
    private static Server mInstance;
    private static final String STOP_SERVER_ACTION = "STOP_SERVER_ACTION";
    private ActionsHandler mActionHandler;
    private ServerActionsDispatcher mActionsDispatcher;
    private List<Service> mServices;

    public static synchronized Server get() {
        if (mInstance != null) {
            return mInstance;
        }

        mInstance = new Server();
        return mInstance;
    }

    private Server() {
        mActionHandler = (action -> {});
        mActionsDispatcher = new ServerActionsDispatcher();

        mServices = Arrays.asList(
                new TestService(mActionsDispatcher),
                new CamerasService(mActionsDispatcher),
                new CommunicationService(mActionsDispatcher)
        );

        initService();
    }

    public ActionsDispatcher getActionsDispatcher() {
        return mActionsDispatcher;
    }

    public void startServer() {
        startService();
    }

    public void stopServer() {
        stopService();
    }

    @Override
    protected ServerActionsDispatcher systemDispatcher() {
        return mActionsDispatcher;
    }

    @Override
    protected List<String> actionTypes() {
        return Collections.singletonList(STOP_SERVER_ACTION);
    }

    @Override
    protected String terminateServiceActionType() {
        return STOP_SERVER_ACTION;
    }

    @Override
    protected ActionsHandler actionsHandler() {
        return mActionHandler;
    }

    @Override
    protected void beforeStart() {
        mServices.forEach(Service::startService);
    }

    @Override
    protected void cleanup() {
        mServices.forEach(service -> {
            service.stopService();
            try {
                service.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                SystemEventsHandler.onError(e.getMessage());
            }
        });
    }
}
