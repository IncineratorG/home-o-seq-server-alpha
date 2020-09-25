package com.touristskaya.old_homoseq.homeoseq.server.services.communication_service.service_actions;

import com.touristskaya.old_homoseq.homeoseq.common.actions.action.Action;
import com.touristskaya.old_homoseq.homeoseq.common.promise.Promise;
import com.touristskaya.old_homoseq.homeoseq.common.service.ServiceActions;

import java.util.Arrays;
import java.util.List;

public class CommunicationServiceActions implements ServiceActions {
    public final String SEND_TEST_DATA = "CSA_SEND_TEST_DATA";
    public final String STOP_SERVICE = "CSA_STOP_SERVICE";

    @Override
    public List<String> getTypes() {
        return Arrays.asList(
                SEND_TEST_DATA,
                STOP_SERVICE
        );
    }

    public Action sendTestDataAction(String data, Promise<Boolean> promise) {
        return new Action(SEND_TEST_DATA, data, promise);
    }

    public Action stopServiceAction() { return new Action(STOP_SERVICE); }
}
