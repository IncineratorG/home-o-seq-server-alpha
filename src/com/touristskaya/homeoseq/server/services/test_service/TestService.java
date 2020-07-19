package com.touristskaya.homeoseq.server.services.test_service;

import com.touristskaya.homeoseq.server.common.actions.ActionsDispatcher;
import com.touristskaya.homeoseq.server.common.actions.action.Action;
import com.touristskaya.homeoseq.server.common.service.ActionsBuffer;
import com.touristskaya.homeoseq.server.common.service.Service;
import com.touristskaya.homeoseq.server.common.service.ServiceActions;
import com.touristskaya.homeoseq.server.system_actions.actions.SystemActions;
import com.touristskaya.homeoseq.server.system_actions.dispatcher.SystemActionsDispatcher;

public class TestService extends Thread implements Service {
    private ActionsDispatcher mActionsDispatcher;
    private TestServiceActions mServiceActions;
    private ActionsBuffer mActionsBuffer;

    public TestService(ActionsDispatcher actionsDispatcher) {
        mActionsDispatcher = actionsDispatcher;
        mServiceActions = new TestServiceActions();
        mActionsBuffer = new ActionsBuffer(mActionsDispatcher,
                SystemActionsDispatcher.NEW_ACTION_EVENT,
                mServiceActions);
    }

    public ServiceActions getActions() {
        return mServiceActions;
    }

    @Override
    public void startService() {
        this.start();
    }

    @Override
    public void run() {
        System.out.println("TestService_IS_RUNNING");

        int counter = 20;
        while (counter > 0) {
            // ===
            if (mActionsBuffer.hasAvailableActions()) {
                Action action = mActionsBuffer.takeLatest();
                System.out.println("PROCESSED_ACTION: " + action.getType());
            }

            if (counter == 14) {
                mActionsDispatcher.dispatch(SystemActions.anotherTestServiceActions.anotherFirstAction());
            }

            if (counter == 13) {
                mActionsDispatcher.dispatch(SystemActions.anotherTestServiceActions.anotherSecondAction());
            }

            if (counter == 12) {
                mActionsDispatcher.dispatch(SystemActions.anotherTestServiceActions.anotherThirdAction());
            }

            if (counter == 11) {
                mActionsDispatcher.dispatch(SystemActions.anotherTestServiceActions.anotherFourthAction());
            }
            // ===

            try {
                Thread.sleep(174);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            --counter;
        }

//        mActionsDispatcher.dispatch(SystemActions.serverActions.stopServerAction());

        System.out.println("TestService_IS_STOPPING");
    }
}
