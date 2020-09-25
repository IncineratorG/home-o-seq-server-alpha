package com.touristskaya.homeoseq.server.server_notifications_dispatcher;

import com.touristskaya.homeoseq.common.events.event_handler.EventHandler;
import com.touristskaya.homeoseq.common.notifications.notifications_dispatcher.NotificationsDispatcher;
import com.touristskaya.homeoseq.common.notifier.Notifier;
import com.touristskaya.homeoseq.common.subscription.UnsubscribeHandler;

public class ServerNotificationsDispatcher implements NotificationsDispatcher {
    public static final String NEW_NOTIFICATION_EVENT = "SYSTEM_NOTIFICATIONS_DISPATCHER_NEW_NOTIFICATION_EVENT";

    private Notifier mNotifier;

    public ServerNotificationsDispatcher() {
        mNotifier = new Notifier();
    }

    @Override
    public UnsubscribeHandler subscribe(String eventType, EventHandler handler) {
        return mNotifier.subscribe(eventType, handler);
    }

    @Override
    public synchronized void dispatch() {

    }
}
