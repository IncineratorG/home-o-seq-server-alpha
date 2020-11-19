package com.touristskaya.homeoseq.common.services.service;

import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.actions.action_handler.ActionHandler;
import com.touristskaya.homeoseq.common.actions.actions_dispatcher.ActionsDispatcher;
import com.touristskaya.homeoseq.common.events.event_handler.EventHandler;
import com.touristskaya.homeoseq.common.subscription.UnsubscribeHandler;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.server_actions_dispatcher.ServerActionsDispatcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class NewService extends Thread implements ActionsDispatcher {
    private ServerActionsDispatcher mDispatcher;
    private List<String> mServiceActions;
    private Set<String> mServiceActionTypes;
    private LinkedBlockingQueue<Action> mActionsQueue;
    private String mTerminateServiceActionType;
    private ActionHandler mActionHandler;
    private ActionHandler mResultActionHandler;
    private Set<UUID> mDispatchedActionUuids;

    protected abstract ServerActionsDispatcher systemDispatcher();

    protected abstract List<String> actionTypes();

    protected abstract String terminateServiceActionType();

    protected abstract ActionHandler actionHandler();

    protected abstract void beforeStart();

    protected abstract void cleanup();

    protected void initService() {
        mDispatchedActionUuids = new CopyOnWriteArraySet<>();
        mResultActionHandler = (action) -> {
            if (action != null && action.promise() != null) {
                action.promise().resolveWithCurrentResult();
            }
        };

        mActionHandler = actionHandler();
        mActionsQueue = new LinkedBlockingQueue<>();
        mDispatcher = systemDispatcher();

        mTerminateServiceActionType = terminateServiceActionType();

        mServiceActions = actionTypes();
        mServiceActionTypes = new HashSet<>(mServiceActions);

        mDispatcher.subscribe(ServerActionsDispatcher.NEW_ACTION_EVENT, (actionData) -> {
            Action action = (Action) actionData;
            if (mServiceActionTypes.contains(action.getType())) {
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
            if (mDispatchedActionUuids.contains(action.getUuid())) {
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
        mDispatchedActionUuids.add(action.getUuid());
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

                if (mDispatchedActionUuids.contains(action.getUuid())) {
                    if (mResultActionHandler != null) {
                        mResultActionHandler.onAction(action);
                    }
                } else {
                    if (mActionHandler != null) {
                        mActionHandler.onAction(action);
                    }
                }

                if (action.getType().equals(mTerminateServiceActionType)) {
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