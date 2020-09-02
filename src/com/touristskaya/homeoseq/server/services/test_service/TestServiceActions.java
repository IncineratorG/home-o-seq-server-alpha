package com.touristskaya.homeoseq.server.services.test_service;

import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.payload.Payload;
import com.touristskaya.homeoseq.common.promise.Promise;
import com.touristskaya.homeoseq.common.service.ServiceActions;

import java.util.Arrays;
import java.util.List;

public class TestServiceActions implements ServiceActions {
    public final String FIRST_ACTION = "TSA_FIRST_ACTION";
    public final String SECOND_ACTION = "TSA_SECOND_ACTION";
    public final String THIRD_ACTION = "TSA_THIRD_ACTION";
    public final String FOURTH_ACTION = "TSA_FOURTH_ACTION";
    public final String STOP_SERVICE = "TSA_STOP_SERVICE";
    public final String GET_DATA = "TSA_GET_DATA";
    public final String RUN_LONG_RUNNING_TASK = "TSA_RUN_LONG_RUNNING_TASK";

    public List<String> getTypes() {
        return Arrays.asList(
                FIRST_ACTION,
                SECOND_ACTION,
                THIRD_ACTION,
                FOURTH_ACTION,
                STOP_SERVICE,
                GET_DATA,
                RUN_LONG_RUNNING_TASK
        );
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

    public Action getDataAction(Promise<Payload> promise) {
        return new Action(GET_DATA, promise);
    }

    public Action runLongRunningTaskAction() {
        return new Action(RUN_LONG_RUNNING_TASK);
    }

    public Action stopServiceAction() { return new Action(STOP_SERVICE); }
}
