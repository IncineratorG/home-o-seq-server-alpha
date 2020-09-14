package com.touristskaya.homeoseq.server.services.communication_service.requests_processor;

import com.touristskaya.homeoseq.common.actions.ActionsDispatcher;
import com.touristskaya.homeoseq.common.client_requests.ClientRequest;
import com.touristskaya.homeoseq.common.client_requests.ClientRequestTypes;
import com.touristskaya.homeoseq.common.communication_manager.CommunicationManager;
import com.touristskaya.homeoseq.common.communication_messages.CommunicationMessages;
import com.touristskaya.homeoseq.common.promise.Promise;
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


                break;
            }
        }
    }
}
