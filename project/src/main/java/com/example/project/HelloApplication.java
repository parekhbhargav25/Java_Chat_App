package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //3 clients (3 group members)
//        for(int i = 0; i < 3; ++i) {
//            Stage stage1 = new Stage();
//            FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("client.fxml"));
//            Scene client = new Scene(fxmlLoader1.load(), 320, 240);
//            stage1.setTitle("Client " + (i + 1));
//            stage1.setScene(client);
//            stage1.show();
//        }

        FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("client.fxml"));
        Scene client = new Scene(fxmlLoader1.load(), 320, 240);
        stage.setTitle("Client");
        stage.setScene(client);
        stage.show();

        Stage stage2 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("server.fxml"));
        Scene server = new Scene(fxmlLoader.load(), 320, 300);
        stage2.setTitle("Server");
        stage2.setScene(server);
        stage2.show();
    }

    public static void main(String[] args) {
        launch();
    }
}