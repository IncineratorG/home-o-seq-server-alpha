package com.touristskaya.homeoseq.common.communication_messages;

public class CommunicationMessages {
    public static final String CONFIRM_RECEIVE = "CONFIRM_RECEIVE_MESSAGE";

    public static ServerMessage confirmReceiveRequestMessage(String requestUuid) {
        return new ServerMessage(CONFIRM_RECEIVE, requestUuid);
    }
}
