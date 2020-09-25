package com.touristskaya.old_homoseq.homeoseq.common.communication_messages;

public class CommunicationMessages {
    public static final String CONFIRM_RECEIVE = "CONFIRM_RECEIVE_MESSAGE";
    public static final String REQUEST_RESULT = "REQUEST_RESULT_MESSAGE";

    public static ServerMessage confirmReceiveRequestMessage(String requestUuid) {
        return new ServerMessage(CONFIRM_RECEIVE, requestUuid);
    }

    public static ServerMessage requestResultMessage(String requestUuid, String stringifiedResult) {
        return new ServerMessage(REQUEST_RESULT, requestUuid, stringifiedResult);
    }
}
