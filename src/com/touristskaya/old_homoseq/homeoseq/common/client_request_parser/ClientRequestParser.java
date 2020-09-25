package com.touristskaya.old_homoseq.homeoseq.common.client_request_parser;

import com.touristskaya.old_homoseq.homeoseq.common.client_requests.ClientRequest;

public interface ClientRequestParser {
    ClientRequest parse(String data);
}
