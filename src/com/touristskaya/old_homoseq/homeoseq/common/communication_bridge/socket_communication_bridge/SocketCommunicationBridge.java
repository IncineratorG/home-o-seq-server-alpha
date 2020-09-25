package com.touristskaya.old_homoseq.homeoseq.common.communication_bridge.socket_communication_bridge;

import com.touristskaya.old_homoseq.homeoseq.common.communication_bridge.CommunicationBridge;
import com.touristskaya.old_homoseq.homeoseq.common.system_events_handler.SystemEventsHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;

public class SocketCommunicationBridge extends Thread implements CommunicationBridge {
    private ServerSocket mServerSocket;
    private Consumer<String> mDataConsumer;
    private PrintWriter mPrintWriter;

    public SocketCommunicationBridge() {

    }

    @Override
    public void open() {
        start();
    }

    @Override
    public void close() {
        try {
            SystemEventsHandler.onInfo("SocketCommunicationBridget->close()");
            mServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendResponse(String data) {
        SystemEventsHandler.onInfo("SocketCommunicationBridge: " + data);

        if (mPrintWriter != null) {
            mPrintWriter.println(data);
        } else {
            SystemEventsHandler.onError("SocketCommunicationBridge->sendResponse(): PRINT_WRITER_IS_NULL");
        }
    }

    @Override
    public void sendNotification(String data) {
        SystemEventsHandler.onInfo("SocketCommunicationBridge->sendNotification()");
    }

    @Override
    public void onReceived(Consumer<String> dataConsumer) {
        mDataConsumer = dataConsumer;
    }

    @Override
    public void run() {
        try {
            mServerSocket = new ServerSocket(9991);
            Socket socket = mServerSocket.accept();

            InputStream inputToServer = socket.getInputStream();

            OutputStream outputFromServer = socket.getOutputStream();

            Scanner scanner = new Scanner(inputToServer, "UTF-8");
            mPrintWriter = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);

            while(scanner.hasNextLine()) {
                mDataConsumer.accept(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
