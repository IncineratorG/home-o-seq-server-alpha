package com.touristskaya.homeoseq.server.services.communication_service.service_description.action_types;

import com.touristskaya.homeoseq.data.common.actions.action_types.ActionTypes;

import java.util.Arrays;
import java.util.List;

public class CommunicationServiceActionTypes implements ActionTypes {
    public final String SEND_TEST_DATA = "CSA_SEND_TEST_DATA";
    public final String STOP_SERVICE = "CSA_STOP_SERVICE";

    @Override
    public List<String> getTypes() {
        return Arrays.asList(
                SEND_TEST_DATA,
                STOP_SERVICE
        );
    }
}
