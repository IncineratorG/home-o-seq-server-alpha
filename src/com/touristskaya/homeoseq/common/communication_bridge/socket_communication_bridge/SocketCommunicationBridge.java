package com.touristskaya.homeoseq.common.communication_bridge.socket_communication_bridge;

import com.touristskaya.homeoseq.common.communication_bridge.CommunicationBridge;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;

public class SocketCommunicationBridge extends Thread implements CommunicationBridge {
    private ServerSocket mServerSocket;
    private Socket mSocket;
    private Consumer<String> mDataConsumer;
    private Thread mThread;
    private PrintWriter mPrintWriter;

    public SocketCommunicationBridge() {
        mThread = new Thread(this);
    }

    @Override
    public void open() {
        mThread.start();
    }

    @Override
    public void close() {
        try {
            mServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(String data) {
        SystemEventsHandler.onInfo("SocketCommunicationBridge: " + data);

        if (mPrintWriter != null) {
            mPrintWriter.println(data);
        } else {
            SystemEventsHandler.onError("SocketCommunicationBridge->send(): PRINT_WRITER_IS_NULL");
        }
    }

    @Override
    public void onReceived(Consumer<String> dataConsumer) {
        mDataConsumer = dataConsumer;
    }

    @Override
    public void run() {
        try {
            mServerSocket = new ServerSocket(9991);
            mSocket = mServerSocket.accept();

            InputStream inputToServer = mSocket.getInputStream();

            OutputStream outputFromServer = mSocket.getOutputStream();

            Scanner scanner = new Scanner(inputToServer, "UTF-8");
            mPrintWriter = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);

//            serverPrintOut.println("SocketCommunicationBridge");

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                mDataConsumer.accept(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
