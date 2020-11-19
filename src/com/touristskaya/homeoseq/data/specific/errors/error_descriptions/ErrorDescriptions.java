package com.touristskaya.homeoseq.data.specific.errors.error_descriptions;

import com.touristskaya.homeoseq.data.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.data.specific.errors.Errors;

public class ErrorDescriptions {
    public static String getDescription(String errorType) {
        switch (errorType) {
            case (Errors.UNKNOWN_ERROR): {
                return "Unknown error";
            }

            case (Errors.UNKNOWN_REQUEST): {
                return "Unknown request";
            }

            case (Errors.BAD_REQUEST_DATA): {
                return "Bad request data";
            }

            default: {
                SystemEventsHandler.onError("ErrorDescriptions->getDescription(): UNKNOWN_ERROR_TYPE: " + errorType);
                return "No description";
            }
        }
    }
}
