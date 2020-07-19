package com.touristskaya.homeoseq.server.common.actions.action;

import java.util.UUID;

public class Action {
    private String mType;
    private Object mPayload;
    private UUID mUuid;

    public Action(String type) {
        this.mType = type;
        this.mPayload = null;
        mUuid = UUID.randomUUID();
    }

    public Action(String type, Object payload) {
        this.mType = type;
        this.mPayload = payload;
        mUuid = UUID.randomUUID();
    }

    public Action(Action other) {
        mType = other.mType;
        mPayload = other.mPayload;
        mUuid = other.mUuid;
    }

    public UUID getUuid() {
        return mUuid;
    }

    public String getType() {
        return mType;
    }

    public Object getPayload() {
        return mPayload;
    }
}
