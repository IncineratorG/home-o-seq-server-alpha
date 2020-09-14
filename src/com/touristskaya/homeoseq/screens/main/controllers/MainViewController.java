package com.touristskaya.homeoseq.screens.main.controllers;

import com.touristskaya.homeoseq.common.actions.ActionsDispatcher;
import com.touristskaya.homeoseq.common.payload.Payload;
import com.touristskaya.homeoseq.common.promise.Promise;
import com.touristskaya.homeoseq.common.service.Service;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.Server;
import com.touristskaya.homeoseq.server.services.surveillance.surveillance.surveillance_status.SurveillanceStatus;
import com.touristskaya.homeoseq.server.system_actions.actions.SystemActions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController<T> implements Initializable {
    public Button leftButton;
    public Button rightButton;
    public Button centerButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SystemEventsHandler.onInfo("MainViewController->initialize()");

        leftButton.setOnAction((e) -> {
            SystemEventsHandler.onInfo("LEFT_BUTTON_CLICK");

            // ===
            ActionsDispatcher dispatcher = Server.get().getDispatcher();

            Promise<Boolean> resultPromise = new Promise<>();
            resultPromise.then(result -> {
                SystemEventsHandler.onInfo("Controller->START_RESULT: " + result);
            });

            dispatcher.dispatch(
                    SystemActions.surveillanceServiceActions.startSurveillanceAction(resultPromise)
            );
            // ===
        });

        rightButton.setOnAction((e) -> {
            SystemEventsHandler.onInfo("RIGHT_BUTTON_CLICK");

            // ===
            ActionsDispatcher dispatcher = Server.get().getDispatcher();

            Promise<SurveillanceStatus> resultPromise = new Promise<>();
            resultPromise.then(result -> {
                SystemEventsHandler.onInfo("");
                SystemEventsHandler.onInfo("===========");
                SystemEventsHandler.onInfo("Controller->GET_STATUS_RESULT: " + result.getTimestamp());
                SystemEventsHandler.onInfo("Controller->GET_STATUS_RESULT: " + result.getIsRunning());
                SystemEventsHandler.onInfo("Controller->GET_STATUS_RESULT: " + result.getTestStringValue());
                SystemEventsHandler.onInfo("===========");
                SystemEventsHandler.onInfo("");
            });

            dispatcher.dispatch(
                    SystemActions.surveillanceServiceActions.getStatusAction(resultPromise)
            );
            // ===
        });

        centerButton.setOnAction((e) -> {
            ActionsDispatcher dispatcher = Server.get().getDispatcher();

            Promise<Boolean> resultPromise = new Promise<>();
            resultPromise.then(result -> {
                SystemEventsHandler.onInfo("Controller->STOP_RESULT: " + result);
            });

            dispatcher.dispatch(
                    SystemActions.surveillanceServiceActions.stopSurveillanceAction(resultPromise)
            );
        });
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
