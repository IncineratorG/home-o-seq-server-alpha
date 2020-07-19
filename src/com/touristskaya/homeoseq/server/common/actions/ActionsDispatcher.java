package com.touristskaya.homeoseq.server.common.actions;

import com.touristskaya.homeoseq.server.common.actions.action.Action;
import com.touristskaya.homeoseq.server.common.notifier.events.EventHandler;
import com.touristskaya.homeoseq.server.common.notifier.events.EventsSource;
import com.touristskaya.homeoseq.server.common.notifier.subscription.UnsubscribeHandler;

public interface ActionsDispatcher extends EventsSource {
    UnsubscribeHandler subscribe(String eventType, EventHandler handler);
    void dispatch(Action action);
}
