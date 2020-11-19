package com.touristskaya.homeoseq.server.server_actions_dispatcher;

import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.actions.actions_dispatcher.ActionsDispatcher;
import com.touristskaya.homeoseq.common.events.event_handler.EventHandler;
import com.touristskaya.homeoseq.common.notifier.Notifier;
import com.touristskaya.homeoseq.common.subscription.UnsubscribeHandler;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
