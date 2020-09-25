package com.touristskaya.homeoseq.server.services.test_service.service_description.action_creators;

import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.actions.action_creators.ActionCreators;
import com.touristskaya.homeoseq.server.services.test_service.service_description.action_types.TestServiceActionTypes;

public class TestServiceActionCreators implements ActionCreators {
    private TestServiceActionTypes mActionTypes;

    public TestServiceActionCreators() {
        mActionTypes = new TestServiceActionTypes();
    }

    public Action sendFirstActionAction() {
        return new Action(mActionTypes.ACTION_ONE);
    }
}
