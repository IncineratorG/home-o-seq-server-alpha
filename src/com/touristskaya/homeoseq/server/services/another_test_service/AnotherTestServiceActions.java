package com.touristskaya.homeoseq.server.services.another_test_service;

import com.touristskaya.homeoseq.server.common.actions.action.Action;
import com.touristskaya.homeoseq.server.common.service.ServiceActions;

import java.util.Arrays;
import java.util.List;

public class AnotherTestServiceActions implements ServiceActions {
    public final String ANOTHER_FIRST = "ANOTHER_FIRST";
    public final String ANOTHER_SECOND = "ANOTHER_SECOND";
    public final String ANOTHER_THIRD = "ANOTHER_THIRD";
    public final String ANOTHER_FOURTH = "ANOTHER_FOURTH";

    @Override
    public List<String> getTypes() {
        return Arrays.asList(ANOTHER_FIRST, ANOTHER_SECOND, ANOTHER_THIRD, ANOTHER_FOURTH);
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
}
