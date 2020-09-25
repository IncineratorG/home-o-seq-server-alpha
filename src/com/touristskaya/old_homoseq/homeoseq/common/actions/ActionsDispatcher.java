package com.touristskaya.old_homoseq.homeoseq.common.actions;

import com.touristskaya.old_homoseq.homeoseq.common.actions.action.Action;
import com.touristskaya.old_homoseq.homeoseq.common.notifier.events.EventHandler;
import com.touristskaya.old_homoseq.homeoseq.common.notifier.events.EventsSource;
import com.touristskaya.old_homoseq.homeoseq.common.notifier.subscription.UnsubscribeHandler;

public interface ActionsDispatcher extends EventsSource {
    UnsubscribeHandler subscribe(String eventType, EventHandler handler);
    void dispatch(Action action);
}
