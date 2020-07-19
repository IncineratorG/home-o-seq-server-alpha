package com.touristskaya.homeoseq.server.services.another_test_service;

import com.touristskaya.homeoseq.server.common.actions.ActionsDispatcher;
import com.touristskaya.homeoseq.server.common.actions.action.Action;
import com.touristskaya.homeoseq.server.common.service.ActionsBuffer;
import com.touristskaya.homeoseq.server.common.service.Service;
import com.touristskaya.homeoseq.server.common.service.ServiceActions;
import com.touristskaya.homeoseq.server.system_actions.actions.SystemActions;
import com.touristskaya.homeoseq.server.system_actions.dispatcher.SystemActionsDispatcher;

public class AnotherTestService extends Thread implements Service {
    private ActionsDispatcher mActionsDispatcher;
    private AnotherTestServiceActions mServiceActions;
    private ActionsBuffer mActionsBuffer;

    public AnotherTestService(ActionsDispatcher actionsDispatcher) {
        mActionsDispatcher = actionsDispatcher;
        mServiceActions = new AnotherTestServiceActions();
        mActionsBuffer = new ActionsBuffer(mActionsDispatcher, SystemActionsDispatcher.NEW_ACTION_EVENT, mServiceActions);
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
        System.out.println("AnotherTestService_IS_RUNNING");

        int counter = 20;
        while (counter > 0) {
            if (mActionsBuffer.hasAvailableActions()) {
                Action action = mActionsBuffer.takeLatest();
                System.out.println("ANOTHER_PROCESSED_ACTION: " + action.getType());
            }

            if (counter == 16) {
                mActionsDispatcher.dispatch(SystemActions.testServiceActions.firstAction());
            }
            if (counter == 15) {
                mActionsDispatcher.dispatch(SystemActions.testServiceActions.secondAction());
            }
            if (counter == 15) {
                mActionsDispatcher.dispatch(SystemActions.testServiceActions.thirdAction());
            }
            if (counter == 14) {
                mActionsDispatcher.dispatch(SystemActions.testServiceActions.fourthAction());
            }

            try {
                Thread.sleep(357);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            --counter;
        }

        System.out.println("AnotherTestService_IS_STOPPING");
    }
}