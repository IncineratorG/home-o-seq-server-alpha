package com.touristskaya.homeoseq.common.client_request_parser;

import com.touristskaya.homeoseq.common.client_requests.ClientRequest;

public interface ClientRequestParser {
    ClientRequest parse(String data);
}
