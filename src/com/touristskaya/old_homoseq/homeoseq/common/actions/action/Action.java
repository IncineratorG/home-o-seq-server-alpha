package com.touristskaya.old_homoseq.homeoseq.common.actions.action;

import com.touristskaya.old_homoseq.homeoseq.common.promise.Promise;

import java.util.UUID;

public class Action {
    private String mType;
    private Object mPayload;
    private UUID mUuid;
    private Promise mCompletionPromise;

    public Action(String type) {
        this.mType = type;
        this.mPayload = null;
        mUuid = UUID.randomUUID();
    }

    public Action(String type, Promise completionPromise) {
        this.mType = type;
        this.mPayload = null;
        mUuid = UUID.randomUUID();
        mCompletionPromise = completionPromise;
    }

    public Action(String type, Object payload) {
        this.mType = type;
        this.mPayload = payload;
        mUuid = UUID.randomUUID();
    }

    public Action(String type, Object payload, Promise completionPromise) {
        this.mType = type;
        this.mPayload = payload;
        mUuid = UUID.randomUUID();
        mCompletionPromise = completionPromise;
    }

    public Action(Action other) {
        mType = other.mType;
        mPayload = other.mPayload;
        mUuid = other.mUuid;
        mCompletionPromise = other.mCompletionPromise;
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

    @SuppressWarnings (value="unchecked")
    public void complete(Object value) {
        if (mCompletionPromise != null) {
            mCompletionPromise.resolve(value);
        }
    }
}
