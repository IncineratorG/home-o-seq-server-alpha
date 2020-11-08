package com.touristskaya.homeoseq.server.services.test_service.actions_processor;

import com.touristskaya.homeoseq.common.actions.action.Action;
import com.touristskaya.homeoseq.common.actions.actions_dispatcher.ActionsDispatcher;
import com.touristskaya.homeoseq.common.actions.actions_processor.ActionsProcessor;
import com.touristskaya.homeoseq.common.notifications.notifications_dispatcher.NotificationsDispatcher;
import com.touristskaya.homeoseq.server.services.test_service.service_description.TestServiceDescription;
import com.touristskaya.old_homoseq.homeoseq.common.system_events_handler.SystemEventsHandler;

public class TestServiceActionsProcessor implements ActionsProcessor {
    private ActionsDispatcher mActionsDispatcher;
    private NotificationsDispatcher mNotificationDispatcher;
    private TestServiceDescription mTestServiceDescription;

    public TestServiceActionsProcessor(ActionsDispatcher actionsDispatcher,
                                       NotificationsDispatcher notificationsDispatcher,
                                       TestServiceDescription testServiceDescription) {
        mActionsDispatcher = actionsDispatcher;
        mNotificationDispatcher = notificationsDispatcher;
        mTestServiceDescription = testServiceDescription;
    }

    @Override
    public boolean process(Action action) {
        if (action.getType().equals(mTestServiceDescription.actionTypes.STOP_SERVICE)) {
            return true;
        } else if (action.getType().equals(mTestServiceDescription.actionTypes.MAKE_TEST_TASK)) {
//            SystemEventsHandler.onInfo("PROCESSOR_THREAD: " + Thread.currentThread().getId());
            action.complete(mTestServiceDescription.actionResults.getMakeTestActionResult("RESULT_STRING"));
        }

        return false;
    }
}
