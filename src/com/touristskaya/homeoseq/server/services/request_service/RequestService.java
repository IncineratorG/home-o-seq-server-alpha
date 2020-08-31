package com.touristskaya.homeoseq.server.services.request_service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.touristskaya.homeoseq.server.common.actions.ActionsDispatcher;
import com.touristskaya.homeoseq.server.common.actions.action.Action;
import com.touristskaya.homeoseq.server.common.service.ActionsBuffer;
import com.touristskaya.homeoseq.server.common.service.Service;
import com.touristskaya.homeoseq.server.common.service.ServiceActions;
import com.touristskaya.homeoseq.server.system_actions.dispatcher.SystemActionsDispatcher;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RequestService extends Thread implements Service {
    private ActionsDispatcher mActionsDispatcher;
    private RequestServiceActions mServiceActions;
    private ActionsBuffer mActionsBuffer;
    private ServerSocket mServerSocket;

    public RequestService(ActionsDispatcher actionsDispatcher) {
        mActionsDispatcher = actionsDispatcher;
        mServiceActions = new RequestServiceActions();
        mActionsBuffer = new ActionsBuffer(mActionsDispatcher,
                SystemActionsDispatcher.NEW_ACTION_EVENT,
                mServiceActions);
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
    public void run() {
        try(ServerSocket serverSocket = new ServerSocket(9991)) {
            Socket connectionSocket = serverSocket.accept();

            InputStream inputToServer = connectionSocket.getInputStream();
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputToServer));

            OutputStream outputFromServer = connectionSocket.getOutputStream();

            Scanner scanner = new Scanner(inputToServer, "UTF-8");
            PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);

            serverPrintOut.println("Hello World! Enter Peace to exit.");

            //Have the server take input from the client and echo it back
            //This should be placed in a loop that listens for a terminator text e.g. bye
            boolean done = false;

            while(!done && scanner.hasNextLine()) {
                String line = scanner.nextLine();

                processReceivedData(line);

//                System.out.println(line);

//                serverPrintOut.println("Echo from <Your Name Here> Server: " + line);

                if(line.toLowerCase().trim().equals("peace")) {
                    System.out.println("DONE");
                    done = true;
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

    private void processReceivedData(String data) {
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();

        Gson gson = new Gson();

        TestObject s = gson.fromJson(data, TestObject.class);

        System.out.println(s.a + " - " + s.s);

//        System.out.println("HERE: " + data);
//
//        int a = 11;
//        String s = "string";
//        List<String> list = new ArrayList<>();
//        list.add("A");
//        list.add("B");
//        list.add("C");
//
//        TestObject t = new TestObject(a, s, list);
//
//        Gson g1 = new Gson();
//        String serialized = g1.toJson(t);
//
//        System.out.println(serialized);
//
//        Gson g2 = new Gson();
//        TestObject to = g2.fromJson(serialized, TestObject.class);
//
//        System.out.println("DESER: " + to.a);

//        Action a = new Action()
    }
}
