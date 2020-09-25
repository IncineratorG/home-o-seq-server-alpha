package com.touristskaya.homeoseq.common.notifications.notification_types;

import com.touristskaya.homeoseq.common.events.event_types.EventTypes;

import java.util.List;

public interface NotificationTypes extends EventTypes {
    List<String> getTypes();
}
