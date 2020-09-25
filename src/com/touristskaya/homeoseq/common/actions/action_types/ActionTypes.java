package com.touristskaya.homeoseq.common.actions.action_types;

import com.touristskaya.homeoseq.common.events.event_types.EventTypes;

import java.util.List;

public interface ActionTypes extends EventTypes {
    List<String> getTypes();
}
