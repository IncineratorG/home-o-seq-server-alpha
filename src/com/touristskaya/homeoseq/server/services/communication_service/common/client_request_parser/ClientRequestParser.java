package com.touristskaya.homeoseq.server.services.communication_service.common.client_request_parser;

import com.touristskaya.homeoseq.server.services.communication_service.common.client_request.ClientRequest;

public interface ClientRequestParser {
    ClientRequest parse(String data);
}
