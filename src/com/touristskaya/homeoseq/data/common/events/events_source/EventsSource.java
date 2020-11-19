package com.touristskaya.homeoseq.data.common.events.events_source;

import com.touristskaya.homeoseq.data.common.events.event_handler.EventHandler;
import com.touristskaya.homeoseq.data.common.subscription.UnsubscribeHandler;

public interface EventsSource {
    UnsubscribeHandler subscribe(String eventType, EventHandler handler);
}
