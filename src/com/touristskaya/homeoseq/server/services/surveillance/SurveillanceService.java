package com.touristskaya.homeoseq.server.services.surveillance;

import com.touristskaya.homeoseq.common.actions.ActionsDispatcher;
import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.promise.Promise;
import com.touristskaya.homeoseq.common.service.ActionsBuffer;
import com.touristskaya.homeoseq.common.service.Service;
import com.touristskaya.homeoseq.common.service.ServiceActions;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.services.surveillance.service_actions.SurveillanceServiceActions;
import com.touristskaya.homeoseq.server.services.surveillance.surveillance.Surveillance;
import com.touristskaya.homeoseq.server.services.surveillance.surveillance.surveillance_status.SurveillanceStatus;
import com.touristskaya.homeoseq.server.system_actions.dispatcher.SystemActionsDispatcher;

import java.util.concurrent.LinkedBlockingQueue;

public class SurveillanceService extends Thread implements Service {
    private LinkedBlockingQueue<Action> mActionsQueue = new LinkedBlockingQueue<>();

    private ActionsDispatcher mActionsDispatcher;
    private SurveillanceServiceActions mServiceActions;
    private ActionsBuffer mActionsBuffer;

    private Surveillance mSurveillance;

    public SurveillanceService(ActionsDispatcher actionsDispatcher) {
        mActionsDispatcher = actionsDispatcher;
        mServiceActions = new SurveillanceServiceActions();
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

        mSurveillance = new Surveillance();
    }

    @Override
    public ServiceActions getActions() {
        return mServiceActions;
    }

    @Override
    public void startService() {
        this.start();
    }

    @Override
    public void stopService() {
        SystemEventsHandler.onInfo("SurveillanceService->stopService()");

        mActionsDispatcher.dispatch(mServiceActions.stopServiceAction());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Action action = mActionsQueue.take();

                SystemEventsHandler.onInfo("SurveillanceService->takeAction(): " + action.getType());

                if (action.getType().equals(mServiceActions.STOP_SERVICE)) {
                    mSurveillance.stop();
                    break;
                } else if (action.getType().equals(mServiceActions.START_SURVEILLANCE)) {
                    boolean startPipelineResult = mSurveillance.start();
                    action.complete(
                            mServiceActions.actionResults.startSurveillanceActionResult(startPipelineResult)
                    );
                } else if (action.getType().equals(mServiceActions.STOP_SURVEILLANCE)) {
                    boolean stopPipelineResult = mSurveillance.stop();
                    action.complete(
                            mServiceActions.actionResults.stopSurveillanceActionResult(stopPipelineResult)
                    );
                } else if (action.getType().equals(mServiceActions.GET_STATUS)) {
                    SystemEventsHandler.onInfo("SurveillanceService->GET_STATUS_REQUEST");

                    SurveillanceStatus status = mSurveillance.getStatus();

                    action.complete(
                            mServiceActions.actionResults.getStatusActionResult(status)
                    );
                }
            } catch (InterruptedException e) {
                SystemEventsHandler.onError("SurveillanceService->run(): INTERRUPTED");
                e.printStackTrace();
            }
        }
    }
}
