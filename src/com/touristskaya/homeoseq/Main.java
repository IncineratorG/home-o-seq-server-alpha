package com.touristskaya.homeoseq;

import com.touristskaya.homeoseq.server.Server;
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

        Server.get().startServer();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        Server.get().stopServer();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

