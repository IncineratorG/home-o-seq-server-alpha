package com.touristskaya.homeoseq.server.services;

import com.touristskaya.homeoseq.server.services.cameras_service.service_description.CamerasServiceDescription;
import com.touristskaya.homeoseq.server.services.communication_service.service_description.CommunicationServiceDescription;
import com.touristskaya.homeoseq.server.services.test_service.service_description.TestServiceDescription;

public class Services {
    public static final TestServiceDescription testService = new TestServiceDescription();
    public static final CommunicationServiceDescription communicationService = new CommunicationServiceDescription();
    public static final CamerasServiceDescription camerasService = new CamerasServiceDescription();
}
