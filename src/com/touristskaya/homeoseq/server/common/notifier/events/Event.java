package com.touristskaya.homeoseq.server.common.notifier.events;

import java.util.Objects;

public class Event {
    private String mEventType;

    public Event(String eventType) {
        mEventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(mEventType, event.mEventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mEventType);
    }
}
