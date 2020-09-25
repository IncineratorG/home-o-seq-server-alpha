package com.touristskaya.homeoseq.server.errors.error_codes;

import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.errors.Errors;

public class ErrorCodes {
    public static String getCode(String errorType) {
        switch (errorType) {
            case (Errors.UNKNOWN_ERROR): {
                return "1";
            }

            case (Errors.UNKNOWN_REQUEST): {
                return "2";
            }

            case (Errors.BAD_REQUEST_DATA): {
                return "3";
            }

            default: {
                SystemEventsHandler.onError("ErrorCodes->getCode(): UNKNOWN_ERROR_TYPE: " + errorType);
                return "0";
            }
        }
    }
}
