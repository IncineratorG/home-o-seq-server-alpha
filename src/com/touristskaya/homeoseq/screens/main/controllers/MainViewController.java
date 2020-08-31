package com.touristskaya.homeoseq.screens.main.controllers;

import com.touristskaya.homeoseq.common.actions.ActionsDispatcher;
import com.touristskaya.homeoseq.common.payload.Payload;
import com.touristskaya.homeoseq.common.promise.Promise;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.Server;
import com.touristskaya.homeoseq.server.system_actions.actions.SystemActions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController<T> implements Initializable {
    public Button leftButton;
    public Button rightButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SystemEventsHandler.onInfo("MainViewController->initialize()");

        leftButton.setOnAction((e) -> {
            SystemEventsHandler.onInfo("LEFT_BUTTON_CLICK");

            ActionsDispatcher dispatcher = Server.get().getDispatcher();

            Promise<Integer> calculatePromise = new Promise<>();
            calculatePromise.then(result -> {
                SystemEventsHandler.onInfo("RESULT: " + result);
            });

            Promise<Payload> promise = new Promise<>();
            promise.then(payload -> {
                dispatcher.dispatch(SystemActions.anotherTestServiceActions.calculateAction(payload, calculatePromise));
            });

            dispatcher.dispatch(SystemActions.testServiceActions.getDataAction(promise));
        });

        rightButton.setOnAction((e) -> {
            SystemEventsHandler.onInfo("RIGHT_BUTTON_CLICK");

            Promise<String> p = new Promise<>();


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
        });
    }

//    public Promise<T> func(Promise<T> promise) {
//        return promise;
//    }

//    public <T> void func(Promise<T> promise, Class<T> typeKey)
//            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        promise.resolve(typeKey.getConstructor().newInstance());
//    }
}
