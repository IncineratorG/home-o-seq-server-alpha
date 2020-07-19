package com.touristskaya.homeoseq.server.common.notifier.events;

import com.touristskaya.homeoseq.server.common.notifier.subscription.UnsubscribeHandler;

public interface EventsSource {
    UnsubscribeHandler subscribe(String eventType, EventHandler handler);
}
