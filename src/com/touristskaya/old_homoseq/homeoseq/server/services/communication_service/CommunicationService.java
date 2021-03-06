package com.touristskaya.old_homoseq.homeoseq.server.services.communication_service;

import com.touristskaya.old_homoseq.homeoseq.common.actions.ActionsDispatcher;
import com.touristskaya.old_homoseq.homeoseq.common.actions.action.Action;
import com.touristskaya.old_homoseq.homeoseq.common.communication_bridge.firebase_communication_bridge.FirebaseCommunicationBridge;
import com.touristskaya.old_homoseq.homeoseq.common.communication_manager.CommunicationManager;
import com.touristskaya.old_homoseq.homeoseq.common.communication_messages.ServerMessage;
import com.touristskaya.old_homoseq.homeoseq.common.service.ActionsBuffer;
import com.touristskaya.old_homoseq.homeoseq.common.service.Service;
import com.touristskaya.old_homoseq.homeoseq.common.service.ServiceActions;
import com.touristskaya.old_homoseq.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.old_homoseq.homeoseq.server.services.communication_service.requests_processor.RequestsProcessor;
import com.touristskaya.old_homoseq.homeoseq.server.services.communication_service.service_actions.CommunicationServiceActions;
import com.touristskaya.old_homoseq.homeoseq.server.system_actions.dispatcher.SystemActionsDispatcher;

import java.util.concurrent.LinkedBlockingQueue;

public class CommunicationService extends Thread implements Service {
    private LinkedBlockingQueue<Action> mActionsQueue = new LinkedBlockingQueue<>();

    private ActionsDispatcher mActionsDispatcher;
    private CommunicationServiceActions mServiceActions;
    private ActionsBuffer mActionsBuffer;
    private CommunicationManager mCommunicationManager;
    private RequestsProcessor mRequestsProcessor;

    public CommunicationService(ActionsDispatcher actionsDispatcher) {
        mActionsDispatcher = actionsDispatcher;
        mServiceActions = new CommunicationServiceActions();
        mActionsBuffer = new ActionsBuffer(mActionsDispatcher,
                SystemActionsDispatcher.NEW_ACTION_EVENT,
                mServiceActions);
        mActionsBuffer.subscribe(ActionsBuffer.NEW_ACTION_AVAILABLE_EVENT, (data) -> {
            try {
                mActionsQueue.put(mActionsBuffer.takeLatest());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        mCommunicationManager = new CommunicationManager(new FirebaseCommunicationBridge());

        mRequestsProcessor = new RequestsProcessor(mCommunicationManager, mActionsDispatcher);

        mCommunicationManager.onRequestReceived(request -> {mRequestsProcessor.process(request);});
    }

    @Override
    public ServiceActions getActions() {
        return mServiceActions;
    }

    @Override
    public void startService() {
        start();
    }

    @Override
    public void stopService() {
        SystemEventsHandler.onInfo("CommunicationService->stopService()");
        mCommunicationManager.stop();

        mActionsDispatcher.dispatch(mServiceActions.stopServiceAction());
    }

    @Override
    public void run() {
        mCommunicationManager.start();

        while (true) {
            try {
                Action action = mActionsQueue.take();

                if (action.getType().equals(mServiceActions.SEND_TEST_DATA)) {
                    String data = (String) action.getPayload();

                    SystemEventsHandler.onInfo("SEND_TEST_DATA: " + data);

                    mCommunicationManager.sendResponseMessage(new ServerMessage("TEST_TYPE"));

                    action.complete(true);
                } else if (action.getType().equals(mServiceActions.STOP_SERVICE)) {
                    SystemEventsHandler.onInfo("STOP_COMMUNICATION_SERVICE");
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// ===
// =====
//    private void parseMessageData(String data) {
//        SystemEventsHandler.onInfo("PARSED_RECEIVED: " + data);
//
//        Gson gson = new Gson();
//
//        try {
//            ClientRequest request = gson.fromJson(data, ClientRequest.class);
//            SystemEventsHandler.onInfo("PARSED: " + request.getType() + " - " + request.getUuid());
//            SystemEventsHandler.onInfo("A: " + request.getPayloadItem("a"));
//            SystemEventsHandler.onInfo("B: " + request.getPayloadItem("b"));
//            SystemEventsHandler.onInfo("LIST: " + request.getPayloadItem("list"));
//
//            switch (request.getType()) {
//                case "RUN_LONG_RUNNING_TASK": {
//                    mCommunicationManager.sendResponseMessage(
//                            CommunicationMessages.confirmReceiveRequestMessage(request.getUuid())
//                    );
//
//                    mActionsDispatcher.dispatch(
//                            SystemActions.testServiceActions.runLongRunningTaskAction(new Promise<>())
//                    );
//                    break;
//                }
//            }
//
//        } catch (JsonSyntaxException e) {
//            SystemEventsHandler.onError("JsonSyntaxException: " + e.toString());
//        }
//
////        try {
////            ServerMessage message = gson.fromJson(data, ServerMessage.class);
////            SystemEventsHandler.onInfo("PARSED: " + message.getType());
////
////            switch (message.getType()) {
////                case "GET_DATA": {
////                    Promise<Payload> promise = new Promise<>();
////                    promise.then(payload -> {
////                        int a = (int) payload.get("a");
////                        int b = (int) payload.get("b");
////
////                        SystemEventsHandler.onInfo("RESULT: " + a + " - " + b);
////
////                        ServerMessage responseMessage = new ServerMessage("RESPONSE");
////                        responseMessage.setValue("a", String.valueOf(a));
////                        responseMessage.setValue("b", String.valueOf(b));
////
////                        mSocketCommunicationBridge.sendResponse(gson.toJson(responseMessage));
////
//////                        ServerMessage testMessage = new ServerMessage("TEST_MESS");
//////                        testMessage.setValue("A", "a");
//////                        testMessage.setValue("B", "b");
//////
//////                        String testMessageJson = gson.toJson(testMessage);
//////                        SystemEventsHandler.onInfo(testMessageJson);
//////
//////                        testMessage = gson.fromJson(testMessageJson, ServerMessage.class);
//////                        SystemEventsHandler.onInfo(testMessage.getValue("A"));
//////                        SystemEventsHandler.onInfo(testMessage.getValue("B"));
////                    });
////
////                    mActionsDispatcher.dispatch(SystemActions.testServiceActions.getDataAction(promise));
////                    break;
////                }
////
////                case "RUN_LONG_RUNNING_TASK": {
////                    mActionsDispatcher.dispatch(SystemActions.testServiceActions.runLongRunningTaskAction());
////                    break;
////                }
////
////                case "STOP_SERVER": {
////                    mActionsDispatcher.dispatch(SystemActions.serverActions.stopServerAction());
////                    break;
////                }
////            }
////        } catch (JsonSyntaxException e) {
////            SystemEventsHandler.onError("JsonSyntaxException");
////        }
//    }
// =====
// ===


//package com.touristskaya.old_homoseq.homeoseq.server.services.communication_service;
//
//import com.google.gson.Gson;
//import com.touristskaya.old_homoseq.homeoseq.common.TestObject;
//import com.touristskaya.old_homoseq.homeoseq.common.actions.ActionsDispatcher;
//import com.touristskaya.old_homoseq.homeoseq.common.service.ActionsBuffer;
//import com.touristskaya.old_homoseq.homeoseq.common.service.Service;
//import com.touristskaya.old_homoseq.homeoseq.common.service.ServiceActions;
//import com.touristskaya.old_homoseq.homeoseq.common.system_events_handler.SystemEventsHandler;
//import com.touristskaya.old_homoseq.homeoseq.server.system_actions.dispatcher.SystemActionsDispatcher;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Scanner;
//
//public class CommunicationService extends Thread implements Service {
//    private ActionsDispatcher mActionsDispatcher;
//    private CommunicationServiceActions mServiceActions;
//    private ActionsBuffer mActionsBuffer;
//    private ServerSocket mServerSocket;
//    private Socket mSocket;
//
//    public CommunicationService(ActionsDispatcher actionsDispatcher) {
//        mActionsDispatcher = actionsDispatcher;
//        mServiceActions = new CommunicationServiceActions();
//        mActionsBuffer = new ActionsBuffer(mActionsDispatcher,
//                SystemActionsDispatcher.NEW_ACTION_EVENT,
//                mServiceActions);
//    }
//
//    @Override
//    public ServiceActions getActions() {
//        return mServiceActions;
//    }
//
//    @Override
//    public void startService() {
//        start();
//    }
//
//    @Override
//    public void stopService() {
//        try {
//            mServerSocket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        SystemEventsHandler.onInfo("CommunicationService->stopService()");
//    }
//
//    @Override
//    public void run() {
//        try {
//            mServerSocket = new ServerSocket(9991);
//
//            mSocket = mServerSocket.accept();
//
//            InputStream inputToServer = mSocket.getInputStream();
//            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputToServer));
//
//            OutputStream outputFromServer = mSocket.getOutputStream();
//
//            Scanner scanner = new Scanner(inputToServer, "UTF-8");
//            PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);
//
//            serverPrintOut.println("Hello World! Enter Peace to exit.");
//
//            //Have the server take input from the client and echo it back
//            //This should be placed in a loop that listens for a terminator text e.g. bye
//            boolean done = false;
//
//            while(!done && scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//
//                processReceivedData(line);
//
////                System.out.println(line);
//
////                serverPrintOut.println("Echo from <Your Name Here> Server: " + line);
//
//                if(line.toLowerCase().trim().equals("peace")) {
//                    System.out.println("DONE");
//                    done = true;
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
////        try(ServerSocket serverSocket = new ServerSocket(9991)) {
////            Socket connectionSocket = serverSocket.accept();
////
////            InputStream inputToServer = connectionSocket.getInputStream();
////            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputToServer));
////
////            OutputStream outputFromServer = connectionSocket.getOutputStream();
////
////            Scanner scanner = new Scanner(inputToServer, "UTF-8");
////            PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);
////
////            serverPrintOut.println("Hello World! Enter Peace to exit.");
////
////            //Have the server take input from the client and echo it back
////            //This should be placed in a loop that listens for a terminator text e.g. bye
////            boolean done = false;
////
////            while(!done && scanner.hasNextLine()) {
////                String line = scanner.nextLine();
////
////                processReceivedData(line);
////
//////                System.out.println(line);
////
//////                serverPrintOut.println("Echo from <Your Name Here> Server: " + line);
////
////                if(line.toLowerCase().trim().equals("peace")) {
////                    System.out.println("DONE");
////                    done = true;
////                }
////            }
////        } catch (IOException e) {
////            System.out.println("ERROR");
////            e.printStackTrace();
////        }
//    }
//
//    private void processReceivedData(String data) {
////        Gson gson = new GsonBuilder()
////                .setLenient()
////                .create();
//
//        Gson gson = new Gson();
//
//        TestObject s = gson.fromJson(data, TestObject.class);
//
//        System.out.println(s.a + " - " + s.s);
//
////        System.out.println("HERE: " + data);
////
////        int a = 11;
////        String s = "string";
////        List<String> list = new ArrayList<>();
////        list.add("A");
////        list.add("B");
////        list.add("C");
////
////        TestObject t = new TestObject(a, s, list);
////
////        Gson g1 = new Gson();
////        String serialized = g1.toJson(t);
////
////        System.out.println(serialized);
////
////        Gson g2 = new Gson();
////        TestObject to = g2.fromJson(serialized, TestObject.class);
////
////        System.out.println("DESER: " + to.a);
//
////        Action a = new Action()
//    }
//}
