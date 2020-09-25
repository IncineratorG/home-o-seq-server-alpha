package com.touristskaya.old_homoseq.homeoseq.server.services.another_test_service.service_actions;

import com.touristskaya.old_homoseq.homeoseq.common.actions.action.Action;
import com.touristskaya.old_homoseq.homeoseq.common.payload.Payload;
import com.touristskaya.old_homoseq.homeoseq.common.promise.Promise;
import com.touristskaya.old_homoseq.homeoseq.common.service.ServiceActions;

import java.util.Arrays;
import java.util.List;

public class AnotherTestServiceActions implements ServiceActions {
    public final String ANOTHER_FIRST = "ATSA_ANOTHER_FIRST";
    public final String ANOTHER_SECOND = "ATSA_ANOTHER_SECOND";
    public final String ANOTHER_THIRD = "ATSA_ANOTHER_THIRD";
    public final String ANOTHER_FOURTH = "ATSA_ANOTHER_FOURTH";
    public final String STOP_SERVICE = "ATSA_STOP_SERVICE";
    public final String CALCULATE = "ATSA_CALCULATE";

    @Override
    public List<String> getTypes() {
        return Arrays.asList(
                ANOTHER_FIRST,
                ANOTHER_SECOND,
                ANOTHER_THIRD,
                ANOTHER_FOURTH,
                STOP_SERVICE,
                CALCULATE
        );
    }

    public Action anotherFirstAction() {
        return new Action(ANOTHER_FIRST);
    }

    public Action anotherSecondAction() {
        return new Action(ANOTHER_SECOND);
    }

    public Action anotherThirdAction() {
        return new Action(ANOTHER_THIRD);
    }

    public Action anotherFourthAction() {
        return new Action(ANOTHER_FOURTH);
    }

    public Action calculateAction(Payload payload, Promise<Integer> completionPromise) {
        return new Action(CALCULATE, payload, completionPromise);
    }

    public Action stopServiceAction() {
        return new Action(STOP_SERVICE);
    }
}
