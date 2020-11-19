package com.touristskaya.homeoseq.server.services.communication_service.service_description;

import com.touristskaya.homeoseq.data.common.actions.action_creators.ActionCreators;
import com.touristskaya.homeoseq.data.common.actions.action_results.ActionResults;
import com.touristskaya.homeoseq.data.common.actions.action_types.ActionTypes;
import com.touristskaya.homeoseq.data.common.notifications.notification_types.NotificationTypes;
import com.touristskaya.homeoseq.data.common.services.service_description.ServiceDescription;
import com.touristskaya.homeoseq.server.services.communication_service.service_description.actions.action_creators.CommunicationServiceActionCreators;
import com.touristskaya.homeoseq.server.services.communication_service.service_description.actions.action_results.CommunicationServiceActionResults;
import com.touristskaya.homeoseq.server.services.communication_service.service_description.actions.action_types.CommunicationServiceActionTypes;
import com.touristskaya.homeoseq.server.services.communication_service.service_description.notifications.notification_types.CommunicationServiceNotificationTypes;

public class CommunicationServiceDescription implements ServiceDescription {
    public final CommunicationServiceActionTypes actionTypes = new CommunicationServiceActionTypes();
    public final CommunicationServiceActionCreators actionCreators = new CommunicationServiceActionCreators();
    public final CommunicationServiceActionResults actionResults = new CommunicationServiceActionResults();
    public final CommunicationServiceNotificationTypes notificationTypes = new CommunicationServiceNotificationTypes();

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
