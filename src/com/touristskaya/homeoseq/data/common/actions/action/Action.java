package com.touristskaya.homeoseq.data.common.actions.action;

import com.touristskaya.homeoseq.data.common.promise.Promise;

import java.util.UUID;
import java.util.function.Consumer;

public class Action {
    private String mType;
    private Object mPayload;
    private UUID mUuid;
    private Promise mCompletionPromise;
    Consumer<Object> mCompleteConsumer;

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

    public UUID uuid() {
        return mUuid;
    }

    public String type() {
        return mType;
    }

    public Object payload() {
        return mPayload;
    }

    @SuppressWarnings (value="unchecked")
    public void complete(Object value) {
        if (mCompleteConsumer != null) {
            mCompleteConsumer.accept(value);
        }

        if (mCompletionPromise != null) {
            mCompletionPromise.setResult(value);
        }

//        if (mCompletionPromise != null) {
//            mCompletionPromise.resolve(value);
//        }
    }

    public void onComplete(Consumer<Object> consumer) {
        mCompleteConsumer = consumer;
    }

    public Promise promise() {
        return mCompletionPromise;
    }
}


//package com.touristskaya.homeoseq.data.common.actions.action;
//
//import com.touristskaya.homeoseq.data.common.promise.Promise;
//import com.touristskaya.homeoseq.data.common.system_events_handler.SystemEventsHandler;
//
//import java.util.UUID;
//import java.util.concurrent.LinkedBlockingQueue;
//
//public class Action {
//    private String mType;
//    private Object mPayload;
//    private UUID mUuid;
//    private Promise mCompletionPromise;
//
//    public Action(String type) {
//        this.mType = type;
//        this.mPayload = null;
//        mUuid = UUID.randomUUID();
//    }
//
//    public Action(String type, Promise completionPromise) {
//        this.mType = type;
//        this.mPayload = null;
//        mUuid = UUID.randomUUID();
//        mCompletionPromise = completionPromise;
//    }
//
//    public Action(String type, Object payload) {
//        this.mType = type;
//        this.mPayload = payload;
//        mUuid = UUID.randomUUID();
//    }
//
//    public Action(String type, Object payload, Promise completionPromise) {
//        this.mType = type;
//        this.mPayload = payload;
//        mUuid = UUID.randomUUID();
//        mCompletionPromise = completionPromise;
//    }
//
//    public Action(Action other) {
//        mType = other.mType;
//        mPayload = other.mPayload;
//        mUuid = other.mUuid;
//        mCompletionPromise = other.mCompletionPromise;
//    }
//
//    public UUID uuid() {
//        return mUuid;
//    }
//
//    public String type() {
//        return mType;
//    }
//
//    public Object payload() {
//        return mPayload;
//    }
//
//    @SuppressWarnings (value="unchecked")
//    public void complete(Object value) {
//        if (mCompletionPromise != null) {
//            mCompletionPromise.resolve(value);
//        }
//    }
//}
