package com.touristskaya.old_homoseq.homeoseq.common.client_request_parser.socket_client_request_parser;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.touristskaya.old_homoseq.homeoseq.common.client_requests.ClientRequest;
import com.touristskaya.old_homoseq.homeoseq.common.client_request_parser.ClientRequestParser;
import com.touristskaya.old_homoseq.homeoseq.common.system_events_handler.SystemEventsHandler;

public class SocketClientRequestParser implements ClientRequestParser {
    private Gson mGson;

    public SocketClientRequestParser() {
        mGson = new Gson();
    }

    @Override
    public ClientRequest parse(String data) {
        ClientRequest request = null;

        try {
            request = mGson.fromJson(data, ClientRequest.class);
            return request;
        } catch (JsonSyntaxException e) {
            SystemEventsHandler.onError("SocketClientRequestParser->parse()->ERROR: " + e.toString());
            return new ClientRequest();
        }
    }
}
