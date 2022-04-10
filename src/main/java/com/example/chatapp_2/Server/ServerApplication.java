package com.example.chatapp_2.Server;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.geometry.Pos.CENTER;


public class ServerApplication extends Application {
    public static ArrayList<Thread> threads;
    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        threads = new ArrayList<Thread>();
        primaryStage.setTitle("JavaFX Chat Server");
        primaryStage.setScene(makeServerUI(primaryStage));
        primaryStage.show();

    }
    public Scene makeServerUI(Stage primaryStage){
        try {
            Server server = new Server(1234);
            Thread serverThread = (new Thread(server));
            serverThread.setName("Server Thread");
            serverThread.setDaemon(true);
            serverThread.start();
            threads.add(serverThread);
        }catch(IllegalArgumentException e){
        }
        catch (IOException e) {

        }
        /* Make the root pane and set properties */
        GridPane rootPane = new GridPane();
        rootPane.setAlignment(CENTER);
        rootPane.setPadding(new Insets(20));
        rootPane.setHgap(2);
        rootPane.setVgap(2);

        Text portText = new Text("Server Is Running on port# 1234");
        portText.setFont(Font.font ("Verdana", 21));
        portText.setFill(Color.GREEN);
        rootPane.add(portText, 0, 0);

        return new Scene(rootPane, 400, 200);
    }
}
