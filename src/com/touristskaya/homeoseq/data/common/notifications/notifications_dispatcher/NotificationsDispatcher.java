package com.touristskaya.homeoseq.data.common.notifications.notifications_dispatcher;

import com.touristskaya.homeoseq.data.common.events.events_source.EventsSource;

public interface NotificationsDispatcher extends EventsSource {
    void dispatch();
}
