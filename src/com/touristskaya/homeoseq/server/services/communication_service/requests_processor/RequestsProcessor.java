package com.touristskaya.homeoseq.server.services.communication_service.requests_processor;

import com.google.gson.Gson;
import com.touristskaya.homeoseq.common.actions.ActionsDispatcher;
import com.touristskaya.homeoseq.common.client_requests.ClientRequest;
import com.touristskaya.homeoseq.common.client_requests.ClientRequestTypes;
import com.touristskaya.homeoseq.common.communication_manager.CommunicationManager;
import com.touristskaya.homeoseq.common.communication_messages.CommunicationMessages;
import com.touristskaya.homeoseq.common.promise.Promise;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.services.surveillance.surveillance.surveillance_status.SurveillanceStatus;
import com.touristskaya.homeoseq.server.system_actions.actions.SystemActions;

public class RequestsProcessor {
    private CommunicationManager mCommunicationManager;
    private ActionsDispatcher mActionsDispatcher;

    public RequestsProcessor(CommunicationManager communicationManager, ActionsDispatcher actionsDispatcher) {
        mCommunicationManager = communicationManager;
        mActionsDispatcher = actionsDispatcher;
    }

    public void process(ClientRequest request) {
        switch (request.getType()) {
            case (ClientRequestTypes.RUN_LONG_RUNNING_TASK): {
                mCommunicationManager.sendResponseMessage(
                        CommunicationMessages.confirmReceiveRequestMessage(request.getUuid())
                );

                mActionsDispatcher.dispatch(
                        SystemActions.testServiceActions.runLongRunningTaskAction(new Promise<>())
                );
                break;
            }

            case (ClientRequestTypes.GET_SURVEILLANCE_STATUS): {
                mCommunicationManager.sendResponseMessage(
                        CommunicationMessages.confirmReceiveRequestMessage(request.getUuid())
                );

                Promise<SurveillanceStatus> resultPromise = new Promise<>();
                resultPromise.then(result -> {
                    Gson gson = new Gson();
                    String stringifiedResult = gson.toJson(result);

                    SystemEventsHandler.onInfo("RequestProcessor->GET_SURVEILLANCE_STATUS->RESULT: " + stringifiedResult);

                    mCommunicationManager.sendResponseMessage(
                            CommunicationMessages.requestResultMessage(request.getUuid(), stringifiedResult)
                    );
                });

                mActionsDispatcher.dispatch(
                        SystemActions.surveillanceServiceActions.getStatusAction(resultPromise)
                );

                break;
            }
        }
    }
}
