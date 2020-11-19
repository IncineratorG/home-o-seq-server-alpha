package com.touristskaya.homeoseq.data.common.notifications.notification_types;

import com.touristskaya.homeoseq.data.common.events.event_types.EventTypes;

import java.util.List;

public interface NotificationTypes extends EventTypes {
    List<String> getTypes();
}
