package com.touristskaya.homeoseq.server.services.communication_service.common.client_requests_payload_extractor;

import com.touristskaya.homeoseq.server.services.communication_service.common.client_request.ClientRequest;

public class ClientRequestsPayloadExtractor {
    public ClientRequestsPayloadExtractor() {

    }

    public String getCameraImageRequest(ClientRequest request) {
        return (String) request.getPayloadItem("cameraId");
    }
}
