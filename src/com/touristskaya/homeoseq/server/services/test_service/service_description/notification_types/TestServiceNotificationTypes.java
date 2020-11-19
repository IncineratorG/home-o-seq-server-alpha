package com.touristskaya.homeoseq.server.services.test_service.service_description.notification_types;

import com.touristskaya.homeoseq.data.common.notifications.notification_types.NotificationTypes;

import java.util.Arrays;
import java.util.List;

public class TestServiceNotificationTypes implements NotificationTypes {
    public final String NOTIFICATION_ONE = "TSN_NOTIFICATION_ONE";

    @Override
    public List<String> getTypes() {
        return Arrays.asList(
                NOTIFICATION_ONE
        );
    }
}
