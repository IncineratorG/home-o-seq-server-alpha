package com.touristskaya.homeoseq.server.services.test_service;

import com.touristskaya.homeoseq.server.common.actions.action.Action;
import com.touristskaya.homeoseq.server.common.service.ServiceActions;

import java.util.Arrays;
import java.util.List;

public class TestServiceActions implements ServiceActions {
    public final String FIRST_ACTION = "FIRST_ACTION";
    public final String SECOND_ACTION = "SECOND_ACTION";
    public final String THIRD_ACTION = "THIRD_ACTION";
    public final String FOURTH_ACTION = "FOURTH_ACTION";

    public List<String> getTypes() {
        return Arrays.asList(FIRST_ACTION, SECOND_ACTION, THIRD_ACTION, FOURTH_ACTION);
    }

    public Action firstAction() {
        return new Action(FIRST_ACTION);
    }

    public Action secondAction() {
        return new Action(SECOND_ACTION);
    }

    public Action thirdAction() {
        return new Action(THIRD_ACTION);
    }

    public Action fourthAction() {
        return new Action(FOURTH_ACTION);
    }
}
