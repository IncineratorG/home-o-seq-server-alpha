package com.touristskaya.homeoseq.server.services.communication_service.common.communication_bridge.firebase_communication_bridge;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.services.communication_service.common.communication_bridge.CommunicationBridge;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FirebaseCommunicationBridge extends Thread implements CommunicationBridge {
    private static String mFirebaseUrl =
            "https://surveillance-136a9.firebaseio.com/homeoseq";
    private static String mClientRequestsField = "/client/requests";
    private static String mServerResponsesField = "/server/responses";
    private static String mServerNotificationsField = "/server/notifications";
    private Firebase mFirebaseDatabase;
    private ValueEventListener mTestEventListener;

    private List<String> mSnapshotsToRemoveKeys;
    private Consumer<String> mDataConsumer;

    public FirebaseCommunicationBridge() {
        mSnapshotsToRemoveKeys = new ArrayList<>();

        mFirebaseDatabase = new Firebase(mFirebaseUrl);
        mTestEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null) {
                    SystemEventsHandler.onInfo("DATA_SNAPSHOT_IS_NULL");
                    return;
                }

//                SystemEventsHandler.onInfo(String.valueOf(dataSnapshot.getChildrenCount()));

                if (dataSnapshot.getValue() == null) {
                    SystemEventsHandler.onInfo("REQUEST_DATA_IS_EMPTY");
                    return;
                }

                dataSnapshot.getChildren().forEach(snapshot -> {
                    String requestKey = snapshot.getKey();
                    String requestValue = snapshot.getValue().toString();

                    SystemEventsHandler.onInfo(requestValue);

                    mDataConsumer.accept(requestValue);

                    removeClientRequest(requestKey);
                });

                SystemEventsHandler.onInfo("MESSAGE_NOT_NULL");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };

        mFirebaseDatabase.child(mClientRequestsField).setValue(null);
        mFirebaseDatabase.child(mServerResponsesField).setValue(null);
    }

    @Override
    public void open() {
        start();
        mFirebaseDatabase.child(mClientRequestsField).addValueEventListener(mTestEventListener);
    }

    @Override
    public void close() {
        mFirebaseDatabase.child(mClientRequestsField).removeEventListener(mTestEventListener);
        Firebase.goOffline();
    }

    @Override
    public void sendResponse(String data) {
        SystemEventsHandler.onInfo("FirebaseCommunicationBridge->sendResponse(): " + data);

        String responseKey = mFirebaseDatabase.child(mServerResponsesField).push().getKey();

        mFirebaseDatabase.child(mServerResponsesField).child(responseKey).setValue(data);
    }

    @Override
    public void sendNotification(String data) {
        SystemEventsHandler.onInfo("FirebaseCommunicationBridge->sendNotification()");
    }

    @Override
    public void onReceived(Consumer<String> dataConsumer) {
        mDataConsumer = dataConsumer;
    }

    @Override
    public void run() {
        super.run();

//        mFirebaseDatabase.push()
    }

    private synchronized void removeClientRequest(String requestKey) {
        mFirebaseDatabase.child(mClientRequestsField).child(requestKey).setValue(null);
    }
}
