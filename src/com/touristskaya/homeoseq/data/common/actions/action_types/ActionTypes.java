package com.touristskaya.homeoseq.data.common.actions.action_types;

import com.touristskaya.homeoseq.data.common.events.event_types.EventTypes;

import java.util.List;

public interface ActionTypes extends EventTypes {
    List<String> getTypes();
}
