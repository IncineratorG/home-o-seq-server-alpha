package com.touristskaya.homeoseq.data.common.subscription;

import com.touristskaya.homeoseq.data.common.events.event_handler.EventHandler;

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