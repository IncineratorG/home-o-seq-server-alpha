package com.touristskaya.homeoseq;

import com.touristskaya.homeoseq.common.actions.ActionsDispatcher;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.Server;
import com.touristskaya.homeoseq.server.system_actions.actions.SystemActions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("screens/main/main.fxml"));
        primaryStage.setTitle("P&S");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Server.get().start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        Server.get().getDispatcher().dispatch(SystemActions.serverActions.stopServerAction());
    }


    public static void main(String[] args) {
        launch(args);
    }
}

