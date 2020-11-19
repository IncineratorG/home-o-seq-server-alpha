package com.touristskaya.homeoseq.server.services.communication_service.common.communication_manager.firebase_communication_manager;

import com.touristskaya.homeoseq.data.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.services.communication_service.common.client_request.ClientRequest;
import com.touristskaya.homeoseq.server.services.communication_service.common.client_request_parser.ClientRequestParser;
import com.touristskaya.homeoseq.server.services.communication_service.common.client_request_parser.general_client_request_parser.GeneralClientRequestParser;
import com.touristskaya.homeoseq.server.services.communication_service.common.communication_bridge.CommunicationBridge;
import com.touristskaya.homeoseq.server.services.communication_service.common.communication_manager.CommunicationManager;
import com.touristskaya.homeoseq.server.services.communication_service.common.message_serializer.MessageSerializer;
import com.touristskaya.homeoseq.server.services.communication_service.common.server_message.ServerMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class FirebaseCommunicationManager implements CommunicationManager {
    private int mIdsCounter;
    private Map<String, Consumer<ClientRequest>> mRequestConsumers;
    private CommunicationBridge mCommunicationBridge;
    private ClientRequestParser mSocketRequestParser;
    private MessageSerializer mMessageSerializer;

    public FirebaseCommunicationManager(CommunicationBridge communicationBridge) {
        mIdsCounter = 1;

        mRequestConsumers = new ConcurrentHashMap<>();

        mSocketRequestParser = new GeneralClientRequestParser();

        mMessageSerializer = new MessageSerializer();

        mCommunicationBridge = communicationBridge;
        mCommunicationBridge.onReceived(data -> {
            ClientRequest request = mSocketRequestParser.parse(data);
            if (!request.isEmpty()) {
                notifyRequestConsumers(request);
            } else {
                SystemEventsHandler.onError("FirebaseCommunicationManager->RECEIVE_EMPTY_REQUEST: " + data);
            }
        });
    }

    public void start() {
        mCommunicationBridge.open();
    }

    public void stop() {
        mCommunicationBridge.close();
    }

    public void sendResponseMessage(ServerMessage message) {
        SystemEventsHandler.onInfo("FirebaseCommunicationManager->sendResponseMessage()");
        mCommunicationBridge.sendResponse(mMessageSerializer.serialize(message));
    }

    public void sendNotificationMessage(ServerMessage message) {
        SystemEventsHandler.onInfo("FirebaseCommunicationManager->sendNotificationMessage()");
    }

    public String onRequestReceived(Consumer<ClientRequest> requestConsumer) {
        mIdsCounter = mIdsCounter + 1;
        String id = String.valueOf(mIdsCounter);

        mRequestConsumers.put(id, requestConsumer);

        return id;
    }

    public void unsubscribe(String requestConsumerId) {
        mRequestConsumers.remove(requestConsumerId);
    }

    private synchronized void notifyRequestConsumers(ClientRequest request) {
        mRequestConsumers.values().forEach(requestConsumer -> requestConsumer.accept(request));
    }
}
