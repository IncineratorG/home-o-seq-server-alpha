package com.touristskaya.homeoseq.server.errors;

import com.touristskaya.homeoseq.common.error.Error;
import com.touristskaya.homeoseq.server.errors.error_codes.ErrorCodes;
import com.touristskaya.homeoseq.server.errors.error_descriptions.ErrorDescriptions;

public class Errors {
    public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
    public static final String UNKNOWN_REQUEST = "UNKNOWN_REQUEST";
    public static final String BAD_REQUEST_DATA = "BAD_REQUEST_DATA";

    public static Error getError(String type) {
        switch (type) {
            case UNKNOWN_REQUEST: {
                return new Error(
                        ErrorCodes.getCode(UNKNOWN_REQUEST),
                        ErrorDescriptions.getDescription(UNKNOWN_REQUEST)
                );
            }

            case BAD_REQUEST_DATA: {
                return new Error(
                        ErrorCodes.getCode(BAD_REQUEST_DATA),
                        ErrorDescriptions.getDescription(BAD_REQUEST_DATA)
                );
            }

            default: {
                return new Error(
                        ErrorCodes.getCode(UNKNOWN_ERROR),
                        ErrorDescriptions.getDescription(UNKNOWN_ERROR)
                );
            }
        }
    }
}
