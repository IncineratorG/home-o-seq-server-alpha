package com.touristskaya.old_homoseq.homeoseq.server;

import com.touristskaya.old_homoseq.homeoseq.common.actions.action.Action;
import com.touristskaya.old_homoseq.homeoseq.common.service.ServiceActions;

import java.util.Arrays;
import java.util.List;

public class ServerActions implements ServiceActions {
    public final String STOP_SERVER = "STOP_SERVER";

    @Override
    public List<String> getTypes() {
        return Arrays.asList(STOP_SERVER);
    }

    public Action stopServerAction() {
        return new Action(STOP_SERVER, "STOP");
    }
}
