package com.touristskaya.homeoseq.common.notifier.events;

import com.touristskaya.homeoseq.common.notifier.subscription.UnsubscribeHandler;

public interface EventsSource {
    UnsubscribeHandler subscribe(String eventType, EventHandler handler);
}
