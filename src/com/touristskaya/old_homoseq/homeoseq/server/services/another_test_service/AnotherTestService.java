package com.touristskaya.old_homoseq.homeoseq.server.services.another_test_service;

import com.touristskaya.old_homoseq.homeoseq.common.actions.ActionsDispatcher;
import com.touristskaya.old_homoseq.homeoseq.common.actions.action.Action;
import com.touristskaya.old_homoseq.homeoseq.common.payload.Payload;
import com.touristskaya.old_homoseq.homeoseq.common.service.ActionsBuffer;
import com.touristskaya.old_homoseq.homeoseq.common.service.Service;
import com.touristskaya.old_homoseq.homeoseq.common.service.ServiceActions;
import com.touristskaya.old_homoseq.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.old_homoseq.homeoseq.server.services.another_test_service.service_actions.AnotherTestServiceActions;
import com.touristskaya.old_homoseq.homeoseq.server.system_actions.dispatcher.SystemActionsDispatcher;

import java.util.concurrent.LinkedBlockingQueue;

public class AnotherTestService extends Thread implements Service {
    private LinkedBlockingQueue<Action> mActionsQueue = new LinkedBlockingQueue<>();

    private ActionsDispatcher mActionsDispatcher;
    private AnotherTestServiceActions mServiceActions;
    private ActionsBuffer mActionsBuffer;

    public AnotherTestService(ActionsDispatcher actionsDispatcher) {
        mActionsDispatcher = actionsDispatcher;
        mServiceActions = new AnotherTestServiceActions();
        mActionsBuffer = new ActionsBuffer(
                mActionsDispatcher,
                SystemActionsDispatcher.NEW_ACTION_EVENT,
                mServiceActions
        );

        mActionsBuffer.subscribe(ActionsBuffer.NEW_ACTION_AVAILABLE_EVENT, (data) -> {
            try {
                mActionsQueue.put(mActionsBuffer.takeLatest());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public ServiceActions getActions() {
        return mServiceActions;
    }

    @Override
    public void startService() {
        this.start();
    }

    @Override
    public void stopService() {
        SystemEventsHandler.onInfo("AnotherTestService->stopService()");

        mActionsDispatcher.dispatch(mServiceActions.stopServiceAction());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Action action = mActionsQueue.take();

                SystemEventsHandler.onInfo("AnotherTestService->takeAction(): " + action.getType());

                if (action.getType().equals(mServiceActions.CALCULATE)) {
                    Payload payload = (Payload) action.getPayload();
                    Integer a = (Integer) payload.get("a");
                    Integer b = (Integer) payload.get("b");

                    int result = a + b;

                    action.complete(result);
                }

                if (action.getType().equals(mServiceActions.STOP_SERVICE)) {
                    break;
                }
            } catch (InterruptedException e) {
                SystemEventsHandler.onError("AnotherTestService->run(): INTERRUPTED");
                e.printStackTrace();
            }
        }

//        while (mRunning) {
//            SystemEventsHandler.onInfo("AnotherTestService->running()");
//
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        SystemEventsHandler.onInfo("AnotherTestService->stop()");



//        System.out.println("AnotherTestService_IS_RUNNING");
//
//        int counter = 20;
//        while (mRunning && counter > 0) {
//            if (mActionsBuffer.hasAvailableActions()) {
//                Action action = mActionsBuffer.takeLatest();
//                System.out.println("ANOTHER_PROCESSED_ACTION: " + action.getType());
//            }
//
//            if (counter == 16) {
//                mActionsDispatcher.dispatch(SystemActions.testServiceActions.firstAction());
//            }
//            if (counter == 15) {
//                mActionsDispatcher.dispatch(SystemActions.testServiceActions.secondAction());
//            }
//            if (counter == 15) {
//                mActionsDispatcher.dispatch(SystemActions.testServiceActions.thirdAction());
//            }
//            if (counter == 14) {
//                mActionsDispatcher.dispatch(SystemActions.testServiceActions.fourthAction());
//            }
//
//            try {
//                Thread.sleep(357);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            --counter;
//        }
//
//        System.out.println("AnotherTestService_IS_STOPPING");
    }
}