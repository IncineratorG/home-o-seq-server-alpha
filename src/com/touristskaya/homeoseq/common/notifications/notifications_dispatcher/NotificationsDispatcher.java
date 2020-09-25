package com.touristskaya.homeoseq.common.notifications.notifications_dispatcher;

import com.touristskaya.homeoseq.common.events.events_source.EventsSource;

public interface NotificationsDispatcher extends EventsSource {
    void dispatch();
}
