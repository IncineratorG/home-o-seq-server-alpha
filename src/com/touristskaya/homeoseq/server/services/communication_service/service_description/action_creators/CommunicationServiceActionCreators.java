package com.touristskaya.homeoseq.server.services.communication_service.service_description.action_creators;

import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.actions.action_creators.ActionCreators;
import com.touristskaya.homeoseq.common.promise.Promise;
import com.touristskaya.homeoseq.server.services.communication_service.service_description.action_types.CommunicationServiceActionTypes;

public class CommunicationServiceActionCreators implements ActionCreators {
    private CommunicationServiceActionTypes mActionTypes;

    public CommunicationServiceActionCreators() {
        mActionTypes = new CommunicationServiceActionTypes();
    }

    public Action sendTestDataAction(String data, Promise<Boolean> promise) {
        return new Action(mActionTypes.SEND_TEST_DATA, data, promise);
    }

    public Action stopServiceAction() { return new Action(mActionTypes.STOP_SERVICE); }
}
