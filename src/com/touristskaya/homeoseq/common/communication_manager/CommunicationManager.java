package com.touristskaya.homeoseq.common.communication_manager;

import com.touristskaya.homeoseq.common.communication_messages.ServerMessage;
import com.touristskaya.homeoseq.common.client_requests.ClientRequest;
import com.touristskaya.homeoseq.common.communication_bridge.CommunicationBridge;
import com.touristskaya.homeoseq.common.message_serializer.MessageSerializer;
import com.touristskaya.homeoseq.common.request_parser.RequestParser;
import com.touristskaya.homeoseq.common.request_parser.socket_request_parser.SocketRequestParser;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class CommunicationManager {
    private int mIdsCounter;
    private Map<String, Consumer<ClientRequest>> mRequestConsumers;
    private CommunicationBridge mCommunicationBridge;
    private RequestParser mSocketRequestParser;
    private MessageSerializer mMessageSerializer;

    public CommunicationManager(CommunicationBridge communicationBridge) {
        mIdsCounter = 1;

        mRequestConsumers = new ConcurrentHashMap<>();

        mSocketRequestParser = new SocketRequestParser();

        mMessageSerializer = new MessageSerializer();

        mCommunicationBridge = communicationBridge;
        mCommunicationBridge.onReceived(data -> {
            ClientRequest request = mSocketRequestParser.parse(data);
            if (!request.isEmpty()) {
                notifyRequestConsumers(request);
            } else {
                SystemEventsHandler.onError("CommunicationManager->RECEIVE_EMPTY_REQUEST: " + data);
            }
        });
    }

    public void start() {
        mCommunicationBridge.open();
    }

    public void stop() {
        mCommunicationBridge.close();
    }

    public void sendMessage(ServerMessage message) {
        SystemEventsHandler.onInfo("CommunicationManager->sendMessage()");
        mCommunicationBridge.send(mMessageSerializer.serialize(message));
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
