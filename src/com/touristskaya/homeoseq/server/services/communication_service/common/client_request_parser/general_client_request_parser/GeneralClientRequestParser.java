package com.touristskaya.homeoseq.server.services.communication_service.common.client_request_parser.general_client_request_parser;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.services.communication_service.common.client_request.ClientRequest;
import com.touristskaya.homeoseq.server.services.communication_service.common.client_request_parser.ClientRequestParser;

public class GeneralClientRequestParser implements ClientRequestParser {
    private Gson mGson;

    public GeneralClientRequestParser() {
        mGson = new Gson();
    }

    @Override
    public ClientRequest parse(String data) {
        ClientRequest request = null;

        try {
            request = mGson.fromJson(data, ClientRequest.class);
            return request;
        } catch (JsonSyntaxException e) {
            SystemEventsHandler.onError("GeneralClientRequestParser->parse()->ERROR: " + e.toString());
            return new ClientRequest();
        }
    }
}
