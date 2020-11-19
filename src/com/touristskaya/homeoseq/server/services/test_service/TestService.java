package com.touristskaya.homeoseq.server.services.test_service;

import com.touristskaya.homeoseq.data.common.actions.action_handler.ActionsHandler;
import com.touristskaya.homeoseq.data.common.services.service.Service;
import com.touristskaya.homeoseq.data.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.data.specific.server.server_actions_dispatcher.ServerActionsDispatcher;
import com.touristskaya.homeoseq.server.services.test_service.actions_handler.TestServiceActionsHandler;
import com.touristskaya.homeoseq.server.services.test_service.service_description.TestServiceDescription;

import java.util.List;

public class TestService extends Service {
    private TestServiceDescription mServiceDescription;
    private ServerActionsDispatcher mActionsDispatcher;
    private ActionsHandler mActionsHandler;

    public TestService(ServerActionsDispatcher dispatcher) {
        mActionsDispatcher = dispatcher;
        mServiceDescription = new TestServiceDescription();
        mActionsHandler = new TestServiceActionsHandler(this, mServiceDescription);

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
    protected ActionsHandler actionsHandler() {
        return mActionsHandler;
    }

    @Override
    protected void beforeStart() {
        SystemEventsHandler.onInfo("TestService->start()");
    }

    @Override
    protected void cleanup() {
        SystemEventsHandler.onInfo("TestService->stop()");
    }
}
