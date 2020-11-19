package com.touristskaya.homeoseq.server.services.cameras_service.service_description;

import com.touristskaya.homeoseq.data.common.actions.action_creators.ActionCreators;
import com.touristskaya.homeoseq.data.common.actions.action_results.ActionResults;
import com.touristskaya.homeoseq.data.common.actions.action_types.ActionTypes;
import com.touristskaya.homeoseq.data.common.notifications.notification_types.NotificationTypes;
import com.touristskaya.homeoseq.data.common.services.service_description.ServiceDescription;
import com.touristskaya.homeoseq.server.services.cameras_service.service_description.action_creators.CamerasServiceActionCreators;
import com.touristskaya.homeoseq.server.services.cameras_service.service_description.action_results.CamerasServiceActionResults;
import com.touristskaya.homeoseq.server.services.cameras_service.service_description.action_types.CamerasServiceActionTypes;
import com.touristskaya.homeoseq.server.services.cameras_service.service_description.notification_types.CamerasServiceNotificationTypes;

public class CamerasServiceDescription implements ServiceDescription {
    public final CamerasServiceActionTypes actionTypes = new CamerasServiceActionTypes();
    public final CamerasServiceActionCreators actionCreators = new CamerasServiceActionCreators();
    public final CamerasServiceActionResults actionResults = new CamerasServiceActionResults();
    public final CamerasServiceNotificationTypes notificationTypes = new CamerasServiceNotificationTypes();

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
