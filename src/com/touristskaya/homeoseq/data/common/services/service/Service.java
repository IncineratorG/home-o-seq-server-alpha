package com.touristskaya.homeoseq.data.common.services.service;

import com.touristskaya.homeoseq.data.common.actions.action.Action;
import com.touristskaya.homeoseq.data.common.actions.action_handler.ActionsHandler;
import com.touristskaya.homeoseq.data.common.actions.actions_dispatcher.ActionsDispatcher;
import com.touristskaya.homeoseq.data.common.events.event_handler.EventHandler;
import com.touristskaya.homeoseq.data.common.notifications.notification.Notification;
import com.touristskaya.homeoseq.data.common.subscription.UnsubscribeHandler;
import com.touristskaya.homeoseq.data.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.data.specific.server.server_actions_dispatcher.ServerActionsDispatcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Service extends Thread implements ActionsDispatcher {
    private ServerActionsDispatcher mDispatcher;
    private LinkedBlockingQueue<Action> mActionsQueue;
    private ActionsHandler mActionHandler;
    private ActionsHandler mResultActionHandler;
    private Set<UUID> mDispatchedActionUuids;
    private List<String> mServiceActions;
    private Set<String> mServiceActionTypes;
    private String mTerminateServiceActionType;
    private LinkedBlockingQueue<Notification> mNotificationsQueue;

    protected abstract ServerActionsDispatcher systemDispatcher();

    protected abstract List<String> actionTypes();

    protected abstract String terminateServiceActionType();

    protected abstract ActionsHandler actionsHandler();

    protected abstract void beforeStart();

    protected abstract void cleanup();

    protected void initService() {
        mDispatchedActionUuids = new CopyOnWriteArraySet<>();
        mResultActionHandler = (action) -> {
            if (action != null && action.promise() != null) {
                action.promise().resolveWithCurrentResult();
            }
        };

        mActionHandler = actionsHandler();
        mActionsQueue = new LinkedBlockingQueue<>();
        mDispatcher = systemDispatcher();

        mTerminateServiceActionType = terminateServiceActionType();

        mServiceActions = actionTypes();
        mServiceActionTypes = new HashSet<>(mServiceActions);

        mDispatcher.subscribe(ServerActionsDispatcher.NEW_ACTION_EVENT, (actionData) -> {
            Action action = (Action) actionData;
            if (mServiceActionTypes.contains(action.type())) {
                try {
                    mActionsQueue.put(action);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    SystemEventsHandler.onError(e.getMessage());
                }
            }
        });

        mDispatcher.subscribe(ServerActionsDispatcher.ACTION_RESULT_EVENT, (actionData) -> {
            Action action = (Action) actionData;
            if (mDispatchedActionUuids.contains(action.uuid())) {
                try {
                    mActionsQueue.put(action);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    SystemEventsHandler.onError(e.getMessage());
                }
            }
        });
    }

    @Override
    public void dispatch(Action action) {
        mDispatchedActionUuids.add(action.uuid());
        mDispatcher.dispatch(action);
    }

    @Override
    public UnsubscribeHandler subscribe(String eventType, EventHandler handler) {
        return null;
    }

    public void startService() {
        start();
    }

    public void stopService() {
        try {
            mActionsQueue.put(new Action(mTerminateServiceActionType));
        } catch (InterruptedException e) {
            e.printStackTrace();
            SystemEventsHandler.onError(e.getMessage());
        }
    }

    @Override
    public void run() {
        beforeStart();

        if (mActionsQueue == null) {
            return;
        }

        while (true) {
            try {
                Action action = mActionsQueue.take();

                if (mDispatchedActionUuids.contains(action.uuid())) {
                    if (mResultActionHandler != null) {
                        mResultActionHandler.onAction(action);
                    }
                } else {
                    if (mActionHandler != null) {
                        mActionHandler.onAction(action);
                    }
                }

                if (action.type().equals(mTerminateServiceActionType)) {
                    cleanup();
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                SystemEventsHandler.onError(e.getMessage());
            }
        }
    }
}