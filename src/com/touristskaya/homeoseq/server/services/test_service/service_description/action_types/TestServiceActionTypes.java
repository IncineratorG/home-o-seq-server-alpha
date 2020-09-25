package com.touristskaya.homeoseq.server.services.test_service.service_description.action_types;

import com.touristskaya.homeoseq.common.actions.action_types.ActionTypes;

import java.util.Arrays;
import java.util.List;

public class TestServiceActionTypes implements ActionTypes {
    public final String ACTION_ONE = "TSA_ACTION_ONE";

    @Override
    public List<String> getTypes() {
        return Arrays.asList(
                ACTION_ONE
        );
    }
}
