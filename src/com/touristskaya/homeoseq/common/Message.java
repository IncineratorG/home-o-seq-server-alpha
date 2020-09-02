package com.touristskaya.homeoseq.common;

import java.util.HashMap;
import java.util.Map;

public class Message {
    private String type;
    private Map<String, String> values;

    public Message(String type) {
        this.type = type;
        values = new HashMap<>();
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
