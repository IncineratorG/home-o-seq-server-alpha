package com.touristskaya.homeoseq.server.services.test_service.service_description;

import com.touristskaya.homeoseq.data.common.actions.action_creators.ActionCreators;
import com.touristskaya.homeoseq.data.common.actions.action_results.ActionResults;
import com.touristskaya.homeoseq.data.common.actions.action_types.ActionTypes;
import com.touristskaya.homeoseq.data.common.notifications.notification_types.NotificationTypes;
import com.touristskaya.homeoseq.data.common.services.service_description.ServiceDescription;
import com.touristskaya.homeoseq.server.services.test_service.service_description.action_creators.TestServiceActionCreators;
import com.touristskaya.homeoseq.server.services.test_service.service_description.action_results.TestServiceActionResults;
import com.touristskaya.homeoseq.server.services.test_service.service_description.action_types.TestServiceActionTypes;
import com.touristskaya.homeoseq.server.services.test_service.service_description.notification_types.TestServiceNotificationTypes;

public class TestServiceDescription implements ServiceDescription {
    public final TestServiceActionTypes actionTypes = new TestServiceActionTypes();
    public final TestServiceActionCreators actionCreators = new TestServiceActionCreators();
    public final TestServiceActionResults actionResults = new TestServiceActionResults();
    public final TestServiceNotificationTypes notificationTypes = new TestServiceNotificationTypes();

    @Override
    public ActionTypes getActionTypes() {
        return actionTypes;
    }

    @Override
    public ActionCreators getActionCreators() {
        return actionCreators;
    }

    @Override
    public ActionResults getActionResults() {
        return actionResults;
    }

    @Override
    public NotificationTypes getNotificationTypes() {
        return notificationTypes;
    }
}
