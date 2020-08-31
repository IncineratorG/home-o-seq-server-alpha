package com.touristskaya.homeoseq.server.system_actions.actions;

import com.touristskaya.homeoseq.server.ServerActions;
import com.touristskaya.homeoseq.server.services.another_test_service.AnotherTestServiceActions;
import com.touristskaya.homeoseq.server.services.communication_service.CommunicationServiceActions;
import com.touristskaya.homeoseq.server.services.test_service.TestServiceActions;

public class SystemActions {
    public static final ServerActions serverActions = new ServerActions();
    public static final TestServiceActions testServiceActions = new TestServiceActions();
    public static final AnotherTestServiceActions anotherTestServiceActions = new AnotherTestServiceActions();
    public static final CommunicationServiceActions communicationServiceActions = new CommunicationServiceActions();
}
