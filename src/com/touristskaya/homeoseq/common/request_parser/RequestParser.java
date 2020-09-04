package com.touristskaya.homeoseq.common.request_parser;

import com.touristskaya.homeoseq.common.client_requests.ClientRequest;

public interface RequestParser {
    ClientRequest parse(String data);
}
