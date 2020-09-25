package com.touristskaya.homeoseq.server.services.communication_service.common.server_message;

import java.util.HashMap;
import java.util.Map;

public class ServerMessage {
    private String type;
    private String requestUuid;
    private String stringifiedPayload;
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

    public ServerMessage(String type, String requestUuid, String stringifiedPayload) {
        this.type = type;
        this.requestUuid = requestUuid;
        this.stringifiedPayload = stringifiedPayload;
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
