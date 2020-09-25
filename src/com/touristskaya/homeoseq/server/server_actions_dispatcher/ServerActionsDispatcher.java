package com.touristskaya.homeoseq.server.server_actions_dispatcher;

import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.actions.actions_dispatcher.ActionsDispatcher;
import com.touristskaya.homeoseq.common.events.event_handler.EventHandler;
import com.touristskaya.homeoseq.common.notifier.Notifier;
import com.touristskaya.homeoseq.common.subscription.UnsubscribeHandler;

public class ServerActionsDispatcher implements ActionsDispatcher {
    public static final String NEW_ACTION_EVENT = "SYSTEM_ACTIONS_DISPATCHER_NEW_ACTION_EVENT";

    private Notifier mNotifier;

    public ServerActionsDispatcher() {
        mNotifier = new Notifier();
    }

    @Override
    public UnsubscribeHandler subscribe(String eventType, EventHandler handler) {
        return mNotifier.subscribe(eventType, handler);
    }

    @Override
    public synchronized void dispatch(Action action) {
        mNotifier.notify(NEW_ACTION_EVENT, action);
    }
}
