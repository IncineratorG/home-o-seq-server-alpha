package com.touristskaya.old_homoseq.homeoseq.common.message_serializer;

import com.google.gson.Gson;
import com.touristskaya.old_homoseq.homeoseq.common.communication_messages.ServerMessage;

public class MessageSerializer {
    private Gson mGson;

    public MessageSerializer() {
        mGson = new Gson();
    }

    public String serialize(ServerMessage message) {
        return mGson.toJson(message);
    }
}
