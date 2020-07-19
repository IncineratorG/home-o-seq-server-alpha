package com.touristskaya.homeoseq.server;

import com.touristskaya.homeoseq.server.common.actions.ActionsDispatcher;
import com.touristskaya.homeoseq.server.common.actions.action.Action;
import com.touristskaya.homeoseq.server.common.notifier.Notifier;
import com.touristskaya.homeoseq.server.services.another_test_service.AnotherTestService;
import com.touristskaya.homeoseq.server.services.request_service.RequestService;
import com.touristskaya.homeoseq.server.services.test_service.TestService;
import com.touristskaya.homeoseq.server.system_actions.actions.SystemActions;
import com.touristskaya.homeoseq.server.system_actions.dispatcher.SystemActionsDispatcher;

import java.util.concurrent.LinkedBlockingQueue;

public class Server extends Thread {
    private LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();

    private Notifier mNotifier;
    private ActionsDispatcher mActionsDispatcher;

    private RequestService mRequestService;
    private TestService mTestService;
    private AnotherTestService mAnotherTestService;

    public Server() {
        mNotifier = new Notifier();

        mActionsDispatcher = new SystemActionsDispatcher();
        mActionsDispatcher.subscribe(SystemActionsDispatcher.NEW_ACTION_EVENT, (data -> {
            Action action = (Action) data;
            if (!action.getType().equals(SystemActions.serverActions.STOP_SERVER)) {
                return;
            }

            String payload = (String) action.getPayload();
            try {
                q.put(payload);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        mRequestService = new RequestService(mActionsDispatcher);
        mTestService = new TestService(mActionsDispatcher);
        mAnotherTestService = new AnotherTestService(mActionsDispatcher);
    }

    @Override
    public void run() {
        System.out.println("SERVER_IS_RUNNING");

        mRequestService.startService();
        mTestService.startService();
        mAnotherTestService.startService();

        while (true) {
            try {
                String s = q.take();
                System.out.println("TAKEN: " + s);

                if (s.equals("STOP")) {
                    System.out.println("STOPPING_SERVER");
                    break;
                }
            } catch (InterruptedException e) {
                System.out.println("INTERRUPTED");
                e.printStackTrace();
            }
        }
    }
}

//        List<String> list = new ArrayList<>();
////        list.add("ONE");
//        list.add("TWO");
////        list.add("THREE");
//
//        Map<String, List<String>> map = new ConcurrentHashMap<>();
//        map.put("FIRST", list);
//
//        List<String> resultList = map.get("FIRST")
//                .stream()
//                .filter(str -> !str.equals("TWO"))
//                .collect(Collectors.toList());
//
//        System.out.println(resultList.size());
//
//        map.put("FIRST", resultList);
//
//        List<String> l = map.get("FIRST");
//        for (String s : l) {
//            System.out.println(s);
//        }

// ===
//        Map<String, List<String>> map = new ConcurrentHashMap<>();
//
//        List<String> list_1 = new ArrayList<>();
//        list_1.add("FIRST_ONE");
//        if (map.containsKey("FIRST")) {
//            list_1.addAll(map.get("FIRST"));
//        }
//
//        map.put("FIRST", list_1);
//
//        List<String> list_2 = new ArrayList<>();
//        list_2.add("FIRST_TWO");
//        if (map.containsKey("FIRST")) {
//            list_2.addAll(map.get("FIRST"));
//        }
//
//        map.put("FIRST", list_2);
//
//        List<String> list_3 = new ArrayList<>();
//        list_3.add("SECOND_ONE");
//        if (map.containsKey("SECOND")) {
//            list_3.addAll(map.get("SECOND"));
//        }
//
//        map.put("SECOND", list_3);
//
//        List<String> list = map.get("FIRST");
//        for (String s : list) {
//            System.out.println(s);
//        }

// =====
// =====

//        Map<String, List<Action>> map = new ConcurrentHashMap<>();
//
//        List<Action> list_1 = new ArrayList<>();
//        if (map.containsKey("TEST_ACTION")) {
//            list_1.addAll(map.get("TEST_ACTION"));
//        }
//        list_1.add(new Action("TEST_ACTION", null));
//
//        map.put("TEST_ACTION", list_1);
//
//        List<Action> list_2 = new ArrayList<>();
//        if (map.containsKey("TEST_ACTION")) {
//            list_2.addAll(map.get("TEST_ACTION"));
//        }
//        list_2.add(new Action("TEST_ACTION", null));
//
//        map.put("TEST_ACTION", list_2);
//
//        System.out.println("MAP_SIZE: " + map.size());
//        System.out.println("LIST_SIZE: " + map.get("TEST_ACTION").size());

//        Map<String, List<Action>> map = new ConcurrentHashMap<>();
//
//        List<Action> list_1 = new CopyOnWriteArrayList<>(map.get("TEST_ACTION") == null ? map.get("TEST_ACTION") : new ArrayList<>());
//        list_1.add(new Action("TEST_ACTION", null));
//
//        map.put("TEST_ACTION", list_1);
//
//        List<Action> list_2 = new CopyOnWriteArrayList<>(map.get("TEST_ACTION"));
//        list_2.add(new Action("TEST_ACTION", null));
//
//        map.put("TEST_ACTION", list_2);
//
//        System.out.println("MAP_SIZE: " + map.size());
//        System.out.println("LIST_SIZE: " + map.get("TEST_ACTION").size());
// ===
