package com.touristskaya.homeoseq.server.services.communication_service.common.communication_manager;

import com.touristskaya.homeoseq.server.services.communication_service.common.client_request.ClientRequest;
import com.touristskaya.homeoseq.server.services.communication_service.common.server_message.ServerMessage;

import java.util.function.Consumer;

public interface CommunicationManager {
    void start();
    void stop();
    void sendResponseMessage(ServerMessage message);
    void sendNotificationMessage(ServerMessage message);
    String onRequestReceived(Consumer<ClientRequest> requestConsumer);
    void unsubscribe(String requestConsumerId);
}
