package com.touristskaya.homeoseq.common.actions.actions_dispatcher;

import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.events.event_handler.EventHandler;
import com.touristskaya.homeoseq.common.events.events_source.EventsSource;

public interface ActionsDispatcher extends EventsSource {
    void dispatch(Action action);
}
