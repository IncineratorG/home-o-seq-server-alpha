package com.touristskaya.old_homoseq.homeoseq.common.notifier.subscription;

import com.touristskaya.old_homoseq.homeoseq.common.notifier.events.EventHandler;

public class Subscription {
    private int mId;
    private String mEvent;
    private EventHandler mEventHandler;


    public Subscription(int id, String eventType, EventHandler eventHandler) {
        this.mId = id;
        mEvent = eventType;
        mEventHandler = eventHandler;
    }

    public int getId() {
        return mId;
    }

    public String getEventType() {
        return mEvent;
    }

    public void handleEvent(Object data) {
        mEventHandler.handle(data);
    }
}