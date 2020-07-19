package com.touristskaya.homeoseq.server.common.service;

import com.touristskaya.homeoseq.server.common.actions.action.Action;
import com.touristskaya.homeoseq.server.common.notifier.events.EventsSource;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ActionsBuffer {
    private EventsSource mEventsSource;
    private ServiceActions mServiceActions;
    private Set<String> mServiceActionTypes;
    private List<Action> mAvailableActions;

    public ActionsBuffer(EventsSource eventsSource, String actionCreateEvent, ServiceActions serviceActions) {
        mAvailableActions = new CopyOnWriteArrayList<>();

        mServiceActions = serviceActions;
        mServiceActionTypes = new HashSet<>(mServiceActions.getTypes());

        mEventsSource = eventsSource;
        mEventsSource.subscribe(actionCreateEvent, (actionData) -> {
            Action action = (Action) actionData;
            if (mServiceActionTypes.contains(action.getType())) {
                saveAction(action);
            }
        });
    }

    public boolean hasAvailableActions() {
        return mAvailableActions.size() > 0;
    }

    public synchronized Action take() {
        if (mAvailableActions.size() > 0) {
            Action action = new Action(mAvailableActions.get(0));
            removeAction(action);

            return action;
        } else {
            return new Action("EMPTY");
        }
    }

    public synchronized Action takeLatest() {
        ListIterator<Action> li = mAvailableActions.listIterator(mAvailableActions.size());

        if (li.hasPrevious()) {
            Action action = new Action(li.previous());
            removeAction(action);

            return action;
        } else {
            return new Action("EMPTY");
        }
    }

    public synchronized Action takeLatestOfType(String actionType) {
        ListIterator<Action> li = mAvailableActions.listIterator(mAvailableActions.size());

        while (li.hasPrevious()) {
            if (li.previous().getType().equals(actionType)) {
                Action action = new Action(li.previous());
                removeAction(action);

                return action;
            }
        }

        return new Action("EMPTY", null);
    }


    public synchronized List<Action> takeAll() {
        List<Action> returnedActionsList = new CopyOnWriteArrayList<>(mAvailableActions);
        mAvailableActions.clear();

        return returnedActionsList;
    }

    public synchronized List<Action> takeAllPreserveReceiveOrder() {
        List<Action> returnedActionsList = new CopyOnWriteArrayList<>(mAvailableActions);
        Collections.reverse(returnedActionsList);

        mAvailableActions.clear();

        return returnedActionsList;
    }

    public synchronized List<Action> takeAllOfType(String actionType) {
        List<Action> actionsOfSpecifiedType = new ArrayList<>();
        for (Action a : mAvailableActions) {
            if (a.getType().equals(actionType)) {
                actionsOfSpecifiedType.add(a);
            }
        }

        for (Action a : actionsOfSpecifiedType) {
            removeAction(a);
        }

        return actionsOfSpecifiedType;
    }

    public synchronized List<Action> takeAllOfTypePreserveReceiveOrder(String actionType) {
        List<Action> actionsOfSpecifiedType = takeAllOfType(actionType);
        Collections.reverse(actionsOfSpecifiedType);

        return actionsOfSpecifiedType;
    }

    private void saveAction(Action action) {
        mAvailableActions.add(action);
    }

    private void removeAction(Action action) {
        mAvailableActions = mAvailableActions
                .stream()
                .filter(a -> !a.getUuid().equals(action.getUuid()))
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }
}
