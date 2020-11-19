package com.touristskaya.homeoseq.server.services.test_service;

import com.touristskaya.homeoseq.common.actions.action_handler.ActionHandler;
import com.touristskaya.homeoseq.common.services.service.NewService;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.server_actions_dispatcher.ServerActionsDispatcher;
import com.touristskaya.homeoseq.server.services.test_service.actions_handler.TestServiceActionsHandler;
import com.touristskaya.homeoseq.server.services.test_service.service_description.TestServiceDescription;

import java.util.List;

public class TestService extends NewService {
    private TestServiceDescription mServiceDescription;
    private ServerActionsDispatcher mActionsDispatcher;
    private ActionHandler mActionsHandler;

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
    protected ActionHandler actionHandler() {
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
