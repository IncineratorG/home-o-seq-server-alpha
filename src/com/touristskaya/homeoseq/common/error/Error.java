package com.touristskaya.homeoseq.common.error;

import com.google.gson.Gson;

public class Error {
    private String code;
    private String description;

    public Error(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
