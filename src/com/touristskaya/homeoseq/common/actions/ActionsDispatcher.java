package com.touristskaya.homeoseq.common.actions;

import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.notifier.events.EventHandler;
import com.touristskaya.homeoseq.common.notifier.events.EventsSource;
import com.touristskaya.homeoseq.common.notifier.subscription.UnsubscribeHandler;

public interface ActionsDispatcher extends EventsSource {
    UnsubscribeHandler subscribe(String eventType, EventHandler handler);
    void dispatch(Action action);
}
