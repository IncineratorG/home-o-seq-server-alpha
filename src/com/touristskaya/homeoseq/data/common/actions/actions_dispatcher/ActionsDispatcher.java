package com.touristskaya.homeoseq.data.common.actions.actions_dispatcher;

import com.touristskaya.homeoseq.data.common.actions.action.Action;
import com.touristskaya.homeoseq.data.common.events.events_source.EventsSource;

public interface ActionsDispatcher extends EventsSource {
    void dispatch(Action action);
}
