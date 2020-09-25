package com.touristskaya.old_homoseq.homeoseq.server.services.surveillance.service_actions;

import com.touristskaya.old_homoseq.homeoseq.common.actions.action.Action;
import com.touristskaya.old_homoseq.homeoseq.common.promise.Promise;
import com.touristskaya.old_homoseq.homeoseq.common.service.ServiceActions;
import com.touristskaya.old_homoseq.homeoseq.server.services.surveillance.surveillance.surveillance_status.SurveillanceStatus;

import java.util.Arrays;
import java.util.List;

public class SurveillanceServiceActions implements ServiceActions {
    public final String START_SERVICE = "SSA_START_SERVICE";
    public final String STOP_SERVICE = "SSA_STOP_SERVICE";
    public final String GET_STATUS = "SSA_GET_STATUS";
    public final String START_SURVEILLANCE = "SSA_START_SURVEILLANCE";
    public final String STOP_SURVEILLANCE = "SSA_STOP_SURVEILLANCE";

    public SurveillanceServiceActionResults actionResults = new SurveillanceServiceActionResults();

    @Override
    public List<String> getTypes() {
        return Arrays.asList(
                START_SERVICE,
                STOP_SERVICE,
                GET_STATUS,
                START_SURVEILLANCE,
                STOP_SURVEILLANCE
        );
    }

    public Action startServiceAction() {
        return new Action(START_SERVICE);
    }

    public Action stopServiceAction() {
        return new Action(STOP_SERVICE);
    }

    public Action getStatusAction(Promise<SurveillanceStatus> result) {
        return new Action(GET_STATUS, result);
    }

    public Action startSurveillanceAction(Promise<Boolean> result) {
        return new Action(START_SURVEILLANCE, result);
    }

    public Action stopSurveillanceAction(Promise<Boolean> result) {
        return new Action(STOP_SURVEILLANCE, result);
    }
}
