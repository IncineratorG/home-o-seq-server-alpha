package com.touristskaya.homeoseq.common.system_events_handler;

public class SystemEventsHandler {
    public static void onInfo(String text) {
        System.out.println(text);
    }

    public static void onError(String error) {
        System.out.println(error);
    }
}
