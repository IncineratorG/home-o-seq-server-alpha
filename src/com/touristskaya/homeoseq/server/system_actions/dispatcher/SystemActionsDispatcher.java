package com.touristskaya.homeoseq.server.system_actions.dispatcher;

import com.touristskaya.homeoseq.common.actions.ActionsDispatcher;
import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.notifier.Notifier;
import com.touristskaya.homeoseq.common.notifier.events.EventHandler;
import com.touristskaya.homeoseq.common.notifier.subscription.UnsubscribeHandler;

public class SystemActionsDispatcher implements ActionsDispatcher {
    public static final String NEW_ACTION_EVENT = "SYSTEM_ACTIONS_DISPATCHER_NEW_ACTION_EVENT";

    private Notifier mNotifier;

    public SystemActionsDispatcher() {
        mNotifier = new Notifier();
    }

    public UnsubscribeHandler subscribe(String eventType, EventHandler handler) {
        return mNotifier.subscribe(eventType, handler);
    }

    public synchronized void dispatch(Action action) {
        mNotifier.notify(NEW_ACTION_EVENT, action);
    }
}
