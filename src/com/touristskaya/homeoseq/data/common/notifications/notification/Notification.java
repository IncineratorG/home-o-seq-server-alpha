package com.touristskaya.homeoseq.data.common.notifications.notification;

import java.util.UUID;

public class Notification<T> {
    private String mType;
    private T mPayload;
    private UUID mUuid;

    public Notification(String type) {
        mType = type;
        mUuid = UUID.randomUUID();
    }

    public Notification(String type, T payload) {
        mType = type;
        mUuid = UUID.randomUUID();
        mPayload = payload;
    }

    public Notification(Notification<T> other) {
        mType = other.mType;
        mPayload = other.mPayload;
        mUuid = other.mUuid;
    }

    public String type() {
        return mType;
    }

    public UUID uuid() {
        return mUuid;
    }

    public T payload() {
        return mPayload;
    }
}
