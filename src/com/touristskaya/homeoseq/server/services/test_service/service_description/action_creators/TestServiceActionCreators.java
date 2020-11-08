package com.touristskaya.homeoseq.server.services.test_service.service_description.action_creators;

import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.actions.action_creators.ActionCreators;
import com.touristskaya.homeoseq.common.promise.Promise;
import com.touristskaya.homeoseq.server.services.test_service.service_description.action_types.TestServiceActionTypes;

public class TestServiceActionCreators implements ActionCreators {
    private TestServiceActionTypes mActionTypes;

    public TestServiceActionCreators() {
        mActionTypes = new TestServiceActionTypes();
    }

    public Action stopServiceAction() { return new Action(mActionTypes.STOP_SERVICE); }

    public Action makeTestTaskAction(Promise<String> result) {
        return new Action(mActionTypes.MAKE_TEST_TASK, result);
    }

    public Action sendFirstActionAction() {
        return new Action(mActionTypes.ACTION_ONE);
    }
}
