package com.touristskaya.homeoseq.common.request_parser.socket_request_parser;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.touristskaya.homeoseq.common.client_requests.ClientRequest;
import com.touristskaya.homeoseq.common.request_parser.RequestParser;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;

public class SocketRequestParser implements RequestParser {
    private Gson mGson;

    public SocketRequestParser() {
        mGson = new Gson();
    }

    @Override
    public ClientRequest parse(String data) {
        ClientRequest request = null;

        try {
            request = mGson.fromJson(data, ClientRequest.class);
            return request;
        } catch (JsonSyntaxException e) {
            SystemEventsHandler.onError("SocketRequestParser->parse()->ERROR: " + e.toString());
            return new ClientRequest();
        }
    }
}
