package com.touristskaya.homeoseq.server.services.test_service.actions_handler;

import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.actions.action_handler.ActionHandler;
import com.touristskaya.homeoseq.common.actions.actions_dispatcher.ActionsDispatcher;
import com.touristskaya.homeoseq.common.actions.actions_processor.ActionsProcessor;
import com.touristskaya.homeoseq.common.promise.Promise;
import com.touristskaya.homeoseq.server.services.Services;
import com.touristskaya.homeoseq.server.services.test_service.service_description.TestServiceDescription;
import com.touristskaya.old_homoseq.homeoseq.common.system_events_handler.SystemEventsHandler;

public class TestServiceActionsHandler implements ActionHandler {
    private ActionsDispatcher mActionsDispatcher;
    private TestServiceDescription mTestServiceDescription;

    public TestServiceActionsHandler(ActionsDispatcher actionsDispatcher, TestServiceDescription testServiceDescription) {
        mActionsDispatcher = actionsDispatcher;
        mTestServiceDescription = testServiceDescription;
    }

    @Override
    public void onAction(Action action) {
        if (action.getType().equals(mTestServiceDescription.actionTypes.MAKE_TEST_TASK)) {
//            SystemEventsHandler.onInfo("PROCESSOR_THREAD: " + Thread.currentThread().getId());
//            action.complete(mTestServiceDescription.actionResults.getMakeTestActionResult("RESULT_STRING"));

            Promise<String> p = new Promise<>();
            p.then(result -> {
                SystemEventsHandler.onInfo("PROMISE_THREAD: " + Thread.currentThread().getId() + " - " + result);
            });
            mActionsDispatcher.dispatch(Services.camerasService.actionCreators.testAction(p));
        }
    }
}
