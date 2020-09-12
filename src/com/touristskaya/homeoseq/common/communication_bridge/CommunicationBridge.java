package com.touristskaya.homeoseq.common.communication_bridge;

import java.util.function.Consumer;

public interface CommunicationBridge {
    void open();
    void close();
    void sendResponse(String data);
    void sendNotification(String data);
    void onReceived(Consumer<String> dataConsumer);
}
