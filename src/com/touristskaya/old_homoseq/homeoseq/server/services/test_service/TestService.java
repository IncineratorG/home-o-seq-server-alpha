package com.touristskaya.old_homoseq.homeoseq.server.services.test_service;

import com.touristskaya.old_homoseq.homeoseq.common.actions.ActionsDispatcher;
import com.touristskaya.old_homoseq.homeoseq.common.actions.action.Action;
import com.touristskaya.old_homoseq.homeoseq.common.payload.Payload;
import com.touristskaya.old_homoseq.homeoseq.common.service.ActionsBuffer;
import com.touristskaya.old_homoseq.homeoseq.common.service.Service;
import com.touristskaya.old_homoseq.homeoseq.common.service.ServiceActions;
import com.touristskaya.old_homoseq.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.old_homoseq.homeoseq.server.services.test_service.service_actions.TestServiceActions;
import com.touristskaya.old_homoseq.homeoseq.server.system_actions.dispatcher.SystemActionsDispatcher;

import java.util.concurrent.LinkedBlockingQueue;

public class TestService extends Thread implements Service {
    private LinkedBlockingQueue<Action> mActionsQueue = new LinkedBlockingQueue<>();

    private ActionsDispatcher mActionsDispatcher;
    private TestServiceActions mServiceActions;
    private ActionsBuffer mActionsBuffer;

    public TestService(ActionsDispatcher actionsDispatcher) {
        mActionsDispatcher = actionsDispatcher;

        mServiceActions = new TestServiceActions();

        mActionsBuffer = new ActionsBuffer(mActionsDispatcher,
                SystemActionsDispatcher.NEW_ACTION_EVENT,
                mServiceActions);
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
        SystemEventsHandler.onInfo("TestService->stopService()");

        mActionsDispatcher.dispatch(mServiceActions.stopServiceAction());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Action action = mActionsQueue.take();

                SystemEventsHandler.onInfo("TestService->takeAction(): " + action.getType());

                if (action.getType().equals(mServiceActions.STOP_SERVICE)) {
                    break;
                } else if (action.getType().equals(mServiceActions.GET_DATA)) {
                    SystemEventsHandler.onInfo("GET_DATA_ACTION");

                    Payload payload = new Payload();
                    payload.set("a", 2);
                    payload.set("b", 3);

                    action.complete(payload);

//                    @SuppressWarnings (value="unchecked")
//                    Promise<Payload> promise = (Promise<Payload>) action.getPayload();
//
//                    promise.resolve(payload);
                } else if (action.getType().equals(mServiceActions.RUN_LONG_RUNNING_TASK)) {
                    SystemEventsHandler.onInfo("TestService->RUN_LONG_RUNNING_TASK");

                    Thread.sleep(1000);

                    action.complete(new Object());

                    SystemEventsHandler.onInfo("TestService->RUN_LONG_RUNNING_TASK->FINISHED");
                }
            } catch (InterruptedException e) {
                SystemEventsHandler.onInfo("TestService->run(): INTERRUPTED");
                e.printStackTrace();
            }
        }
    }

//        @Override
//    public void run() {
//        System.out.println("TestService_IS_RUNNING");
//
//        int counter = 20;
//        while (true) {
//            // ===
//            if (mActionsBuffer.hasAvailableActions()) {
//                Action action = mActionsBuffer.takeLatest();
//                System.out.println("PROCESSED_ACTION: " + action.getType());
//            }
//
//            if (counter == 14) {
//                mActionsDispatcher.dispatch(SystemActions.anotherTestServiceActions.anotherFirstAction());
//            }
//
//            if (counter == 13) {
//                mActionsDispatcher.dispatch(SystemActions.anotherTestServiceActions.anotherSecondAction());
//            }
//
//            if (counter == 12) {
//                mActionsDispatcher.dispatch(SystemActions.anotherTestServiceActions.anotherThirdAction());
//            }
//
//            if (counter == 11) {
//                mActionsDispatcher.dispatch(SystemActions.anotherTestServiceActions.anotherFourthAction());
//            }
//            // ===
//
//            try {
//                Thread.sleep(174);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            --counter;
//        }
//
////        mActionsDispatcher.dispatch(SystemActions.serverActions.stopServerAction());
//
//        System.out.println("TestService_IS_STOPPING");
//    }
}
