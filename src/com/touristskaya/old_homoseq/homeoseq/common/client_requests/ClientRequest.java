package com.touristskaya.old_homoseq.homeoseq.common.client_requests;

import java.util.Map;
import java.util.UUID;

public class ClientRequest {
    private String uuid;
    private String type;
    private Map<String, Object> payload;

    public ClientRequest() {
    }

    public ClientRequest(String type) {
        this.type = type;
        this.uuid = UUID.randomUUID().toString();
    }

    public ClientRequest(String type, String uuid) {
        this.type = type;
        this.uuid = uuid;
    }

    public boolean isEmpty() {
        if (type == null) {
            return true;
        }
        return type.isEmpty();
    }

    public String getUuid() {
        return uuid;
    }

    public String getType() {
        return type;
    }

    public Object getPayloadItem(String key) {
        return payload.get(key);
    }
}
