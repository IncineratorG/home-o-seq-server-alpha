package com.touristskaya.homeoseq.server.services.test_service.service_description.actions.action_types;

import com.touristskaya.homeoseq.data.common.actions.action_types.ActionTypes;

import java.util.Arrays;
import java.util.List;

public class TestServiceActionTypes implements ActionTypes {
    public final String STOP_SERVICE = "TSA_STOP_SERVICE";
    public final String ACTION_ONE = "TSA_ACTION_ONE";
    public final String MAKE_TEST_TASK = "TSA_MAKE_TEST_TASK";

    @Override
    public List<String> getTypes() {
        return Arrays.asList(
                ACTION_ONE,
                STOP_SERVICE,
                MAKE_TEST_TASK
        );
    }
}
