package com.touristskaya.old_homoseq.homeoseq.common.notifier.events;

import com.touristskaya.old_homoseq.homeoseq.common.notifier.subscription.UnsubscribeHandler;

public interface EventsSource {
    UnsubscribeHandler subscribe(String eventType, EventHandler handler);
}
