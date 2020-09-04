package com.touristskaya.homeoseq.common.communication_messages;

import java.util.HashMap;
import java.util.Map;

public class ServerMessage {
    private String type;
    private String requestUuid;
    private Map<String, String> values;

    public ServerMessage(String type) {
        this.type = type;
        this.values = new HashMap<>();
    }

    public ServerMessage(String type, String requestUuid) {
        this.type = type;
        this.requestUuid = requestUuid;
        this.values = new HashMap<>();
    }

    public String getType() {
        return type;
    }

    public String getValue(String key) {
        if (values.containsKey(key)) {
            return values.get(key);
        }

        return "";
    }

    public void setValue(String key, String value) {
        values.put(key, value);
    }
}
