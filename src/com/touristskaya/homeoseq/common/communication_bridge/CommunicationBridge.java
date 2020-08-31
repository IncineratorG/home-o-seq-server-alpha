package com.touristskaya.homeoseq.common.communication_bridge;

import java.util.function.Consumer;

public interface CommunicationBridge {
    void open();
    void close();
    void send(String data);
    void onReceived(Consumer<String> dataConsumer);
}
