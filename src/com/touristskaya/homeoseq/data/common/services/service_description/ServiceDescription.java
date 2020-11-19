package com.touristskaya.homeoseq.data.common.services.service_description;

import com.touristskaya.homeoseq.data.common.actions.action_creators.ActionCreators;
import com.touristskaya.homeoseq.data.common.actions.action_results.ActionResults;
import com.touristskaya.homeoseq.data.common.actions.action_types.ActionTypes;
import com.touristskaya.homeoseq.data.common.notifications.notification_types.NotificationTypes;

public interface ServiceDescription {
    ActionTypes getActionTypes();
    ActionCreators getActionCreators();
    ActionResults getActionResults();
    NotificationTypes getNotificationTypes();
}
