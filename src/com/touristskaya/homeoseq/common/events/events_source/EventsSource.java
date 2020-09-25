package com.touristskaya.homeoseq.common.events.events_source;

import com.touristskaya.homeoseq.common.events.event_handler.EventHandler;
import com.touristskaya.homeoseq.common.subscription.UnsubscribeHandler;

public interface EventsSource {
    UnsubscribeHandler subscribe(String eventType, EventHandler handler);
}
