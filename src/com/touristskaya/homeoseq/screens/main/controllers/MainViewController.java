package com.touristskaya.homeoseq.screens.main.controllers;

import com.touristskaya.homeoseq.common.actions.actions_dispatcher.ActionsDispatcher;
import com.touristskaya.homeoseq.common.promise.Promise;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.Server;
import com.touristskaya.homeoseq.server.services.Services;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class MainViewController<T> implements Initializable {
    public Button leftButton;
    public Button rightButton;
    public Button centerButton;

    private AtomicInteger val = new AtomicInteger(0);
    private int simpleVal = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SystemEventsHandler.onInfo("MainViewController->initialize()");

        leftButton.setOnAction((e) -> {
            SystemEventsHandler.onInfo("LEFT_BUTTON_CLICK");

            // =====
            ActionsDispatcher dispatcher = Server.get().getActionsDispatcher();
            dispatcher.dispatch(Services.testService.actionCreators.makeTestTaskAction(new Promise<>()));
            // =====

            // ===
//            ActionsDispatcher dispatcher = Server.get().getActionsDispatcher();
//
//            SystemEventsHandler.onInfo("CONTROLLER_THREAD: " + Thread.currentThread().getId());
//
//            Promise<String> resultPromise = new Promise<>();
//            resultPromise.then(string -> {
//                SystemEventsHandler.onInfo("PROMISE_THREAD: " + Thread.currentThread().getId());
//
//                SystemEventsHandler.onInfo("RESULT: " + string);
//            });
//
//            dispatcher.dispatch(
//                    Services.testService.actionCreators.makeTestTaskAction(resultPromise)
//            );
            // ===

            // ===
//            ActionsDispatcher dispatcher = Server.get().getActionsDispatcher();
//
//            for (int i = 0; i < 100000; ++i) {
//                Promise<String> resultPromise_1 = new Promise<>();
//                resultPromise_1.then(string -> {
//                    val.incrementAndGet();
//                    ++simpleVal;
//                });
//                Promise<String> resultPromise_2 = new Promise<>();
//                resultPromise_2.then(string -> {
//                    val.incrementAndGet();
//                    ++simpleVal;
//                });
//                Promise<String> resultPromise_3 = new Promise<>();
//                resultPromise_2.then(string -> {
//                    val.incrementAndGet();
//                    ++simpleVal;
//                });
//                Promise<String> resultPromise_4 = new Promise<>();
//                resultPromise_2.then(string -> {
//                    val.incrementAndGet();
//                    ++simpleVal;
//                });
//
//                dispatcher.dispatch(
//                        Services.testService.actionCreators.makeTestTaskAction(resultPromise_1)
//                );
//                dispatcher.dispatch(
//                        Services.camerasService.actionCreators.testAction(resultPromise_2)
//                );
//                dispatcher.dispatch(
//                        Services.testService.actionCreators.makeTestTaskAction(resultPromise_3)
//                );
//                dispatcher.dispatch(
//                        Services.camerasService.actionCreators.testAction(resultPromise_4)
//                );
//            }
            // ===

//            SystemEventsHandler.onInfo("AFTER_1: " + Thread.currentThread().getId());
//            String result = resultPromise.get();
//            SystemEventsHandler.onInfo("AFTER_2: " + result + " - " + Thread.currentThread().getId());
        });

        rightButton.setOnAction((e) -> {
            SystemEventsHandler.onInfo("RIGHT_BUTTON_CLICK: " + val.intValue() + " - " + simpleVal);
        });
//
//        rightButton.setOnAction((e) -> {
//            SystemEventsHandler.onInfo("RIGHT_BUTTON_CLICK");
//
//            // ===
//            ServerActionsDispatcher dispatcher = Server.get().getDispatcher();
//
//            Promise<SurveillanceStatus> resultPromise = new Promise<>();
//            resultPromise.then(result -> {
//                SystemEventsHandler.onInfo("");
//                SystemEventsHandler.onInfo("===========");
//                SystemEventsHandler.onInfo("Controller->GET_STATUS_RESULT: " + result.getTimestamp());
//                SystemEventsHandler.onInfo("Controller->GET_STATUS_RESULT: " + result.getIsRunning());
//                SystemEventsHandler.onInfo("Controller->GET_STATUS_RESULT: " + result.getTestStringValue());
//                SystemEventsHandler.onInfo("===========");
//                SystemEventsHandler.onInfo("");
//            });
//
//            dispatcher.dispatch(
//                    SystemActions.surveillanceServiceActions.getStatusAction(resultPromise)
//            );
//            // ===
//        });
//
//        centerButton.setOnAction((e) -> {
//            ServerActionsDispatcher dispatcher = Server.get().getDispatcher();
//
//            Promise<Boolean> resultPromise = new Promise<>();
//            resultPromise.then(result -> {
//                SystemEventsHandler.onInfo("Controller->STOP_RESULT: " + result);
//            });
//
//            dispatcher.dispatch(
//                    SystemActions.surveillanceServiceActions.stopSurveillanceAction(resultPromise)
//            );
//        });
    }
}

//            Promise<String> p = new Promise<>();


//            Promise<String> promise = new Promise<>();
//            promise.then(str -> {
//                SystemEventsHandler.onInfo("FROM_PROMISE: " + str);
//            });
//
//            Promise<String> p = func(promise);
//            p.resolve("n");


//            Promise<String> promise = new Promise<>();
//            promise.then(str -> {
//                SystemEventsHandler.onInfo("FROM_PROMISE: " + str);
//            });
//
//            try {
//                func(promise, String.class);
//            } catch (NoSuchMethodException e1) {
//                e1.printStackTrace();
//            } catch (IllegalAccessException e1) {
//                e1.printStackTrace();
//            } catch (InvocationTargetException e1) {
//                e1.printStackTrace();
//            } catch (InstantiationException e1) {
//                e1.printStackTrace();
//            }


//package com.touristskaya.homeoseq.screens.main.controllers;
//
//import com.touristskaya.homeoseq.common.actions.actions_dispatcher.ActionsDispatcher;
//import com.touristskaya.homeoseq.common.camera.Camera;
//import com.touristskaya.homeoseq.common.promise.Promise;
//import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
//import com.touristskaya.homeoseq.server.Server;
//import com.touristskaya.homeoseq.server.services.Services;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//
//import java.net.URL;
//import java.util.List;
//import java.util.ResourceBundle;
//
//public class MainViewController<T> implements Initializable {
//    public Button leftButton;
//    public Button rightButton;
//    public Button centerButton;
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        SystemEventsHandler.onInfo("MainViewController->initialize()");
//
//        leftButton.setOnAction((e) -> {
//            SystemEventsHandler.onInfo("LEFT_BUTTON_CLICK");
//
//            // ===
//            ActionsDispatcher dispatcher = Server.get().getActionsDispatcher();
//
//            Promise<List<Camera>> resultPromise = new Promise<>();
//            resultPromise.then(cameras -> {
//                if (cameras.size() <= 0) {
//                    SystemEventsHandler.onInfo("MainViewController->BAD_CAMERAS_SIZE");
//                    return;
//                }
//
////                List<String> serializedCamerasList = new ArrayList<>();
////                cameras.forEach(camera -> {
////                    serializedCamerasList.add(camera.serializeShortForm());
////                });
////
////                Gson gson = new Gson();
////                String serializedCameras = gson.toJson(serializedCamerasList);
////
////                SystemEventsHandler.onInfo(serializedCameras);
//
//
//
////                String cameraId = result.get(0).getId();
////
////                Promise<BufferedImage> cameraImagePromise = new Promise<>();
////                cameraImagePromise.then(cameraImage -> {
////                    SystemEventsHandler.onInfo("CAMERA_IMAGE: " + (cameraImage == null));
////                });
////
////                dispatcher.dispatch(
////                        Services.camerasService.actionCreators.getCameraImageAction(cameraId, cameraImagePromise)
////                );
//            });
//
//            dispatcher.dispatch(
//                    Services.camerasService.actionCreators.getAllCamerasAction(resultPromise)
//            );
//            // ===
//        });
////
////        rightButton.setOnAction((e) -> {
////            SystemEventsHandler.onInfo("RIGHT_BUTTON_CLICK");
////
////            // ===
////            ServerActionsDispatcher dispatcher = Server.get().getDispatcher();
////
////            Promise<SurveillanceStatus> resultPromise = new Promise<>();
////            resultPromise.then(result -> {
////                SystemEventsHandler.onInfo("");
////                SystemEventsHandler.onInfo("===========");
////                SystemEventsHandler.onInfo("Controller->GET_STATUS_RESULT: " + result.getTimestamp());
////                SystemEventsHandler.onInfo("Controller->GET_STATUS_RESULT: " + result.getIsRunning());
////                SystemEventsHandler.onInfo("Controller->GET_STATUS_RESULT: " + result.getTestStringValue());
////                SystemEventsHandler.onInfo("===========");
////                SystemEventsHandler.onInfo("");
////            });
////
////            dispatcher.dispatch(
////                    SystemActions.surveillanceServiceActions.getStatusAction(resultPromise)
////            );
////            // ===
////        });
////
////        centerButton.setOnAction((e) -> {
////            ServerActionsDispatcher dispatcher = Server.get().getDispatcher();
////
////            Promise<Boolean> resultPromise = new Promise<>();
////            resultPromise.then(result -> {
////                SystemEventsHandler.onInfo("Controller->STOP_RESULT: " + result);
////            });
////
////            dispatcher.dispatch(
////                    SystemActions.surveillanceServiceActions.stopSurveillanceAction(resultPromise)
////            );
////        });
//    }
//}
//
////            Promise<String> p = new Promise<>();
//
//
////            Promise<String> promise = new Promise<>();
////            promise.then(str -> {
////                SystemEventsHandler.onInfo("FROM_PROMISE: " + str);
////            });
////
////            Promise<String> p = func(promise);
////            p.resolve("n");
//
//
////            Promise<String> promise = new Promise<>();
////            promise.then(str -> {
////                SystemEventsHandler.onInfo("FROM_PROMISE: " + str);
////            });
////
////            try {
////                func(promise, String.class);
////            } catch (NoSuchMethodException e1) {
////                e1.printStackTrace();
////            } catch (IllegalAccessException e1) {
////                e1.printStackTrace();
////            } catch (InvocationTargetException e1) {
////                e1.printStackTrace();
////            } catch (InstantiationException e1) {
////                e1.printStackTrace();
////            }
