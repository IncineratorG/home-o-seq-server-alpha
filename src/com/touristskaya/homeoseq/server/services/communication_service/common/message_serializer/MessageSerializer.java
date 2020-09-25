package com.touristskaya.homeoseq.server.services.communication_service.common.message_serializer;

import com.google.gson.Gson;
import com.touristskaya.homeoseq.server.services.communication_service.common.server_message.ServerMessage;

public class MessageSerializer {
    private Gson mGson;

    public MessageSerializer() {
        mGson = new Gson();
    }

    public String serialize(ServerMessage message) {
        return mGson.toJson(message);
    }
}
