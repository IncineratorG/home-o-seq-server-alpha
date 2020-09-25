package com.touristskaya.homeoseq.server.services.communication_service.common.communication_messages;

import com.touristskaya.homeoseq.common.error.Error;
import com.touristskaya.homeoseq.server.services.communication_service.common.server_message.ServerMessage;

public class CommunicationMessages {
    public static final String CONFIRM_RECEIVE = "CONFIRM_RECEIVE_MESSAGE";
    public static final String REQUEST_RESULT = "REQUEST_RESULT_MESSAGE";
    public static final String ERROR_MESSAGE = "ERROR_MESSAGE";

    public static ServerMessage confirmReceiveRequestMessage(String requestUuid) {
        return new ServerMessage(CONFIRM_RECEIVE, requestUuid);
    }

    public static ServerMessage requestResultMessage(String requestUuid, String stringifiedResult) {
        return new ServerMessage(REQUEST_RESULT, requestUuid, stringifiedResult);
    }

    public static ServerMessage errorMessage(String requestId, Error error) {
        return new ServerMessage(ERROR_MESSAGE, requestId, error.serialize());
    }
}
