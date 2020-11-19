package com.touristskaya.homeoseq.data.specific.server.server_actions_dispatcher;

import com.touristskaya.homeoseq.data.common.actions.action.Action;
import com.touristskaya.homeoseq.data.common.actions.actions_dispatcher.ActionsDispatcher;
import com.touristskaya.homeoseq.data.common.events.event_handler.EventHandler;
import com.touristskaya.homeoseq.data.common.notifier.Notifier;
import com.touristskaya.homeoseq.data.common.subscription.UnsubscribeHandler;

public class ServerActionsDispatcher implements ActionsDispatcher {
    public static final String NEW_ACTION_EVENT = "SYSTEM_ACTIONS_DISPATCHER_NEW_ACTION_EVENT";
    public static final String ACTION_RESULT_EVENT = "SYSTEM_ACTIONS_DISPATCHER_ACTION_RESULT_EVENT";

    private Notifier mNotifier;

    public ServerActionsDispatcher() {
        mNotifier = new Notifier();
    }

    @Override
    public synchronized UnsubscribeHandler subscribe(String eventType, EventHandler handler) {
        return mNotifier.subscribe(eventType, handler);
    }

    @Override
    public synchronized void dispatch(Action action) {
        mNotifier.notify(NEW_ACTION_EVENT, action);

        action.onComplete((object) -> {
            mNotifier.notify(ACTION_RESULT_EVENT, action);
        });

        mNotifier.notify(NEW_ACTION_EVENT, action);
    }
}
