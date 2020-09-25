package com.touristskaya.homeoseq.common.actions.actions_processor;

import com.touristskaya.homeoseq.common.actions.action.Action;

public interface ActionsProcessor {
    boolean process(Action action);
}
