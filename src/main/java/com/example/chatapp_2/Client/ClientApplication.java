package com.example.chatapp_2.Client;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static javafx.scene.paint.Color.LIGHTBLUE;

public class ClientApplication extends Application {
    private ArrayList<Thread> threads;
    public static void main(String[] args){
        launch();
    }

    @Override
    public void stop() throws Exception {
        // TODO Auto-generated method stub
        super.stop();
        for (Thread thread: threads){
            thread.interrupt(); //clean stop of thread
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        threads = new ArrayList<Thread>();
        primaryStage.setTitle("JavaFX Chat Client");
        primaryStage.setScene(makeInitScene(primaryStage));
        primaryStage.show();
    }

    public Scene makeInitScene(Stage primaryStage) {
        /* Make the root pane and set properties */
        GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(20));
        rootPane.setVgap(10);
        rootPane.setHgap(10);
        rootPane.setAlignment(Pos.CENTER);

        /* Make the text fields and set properties */
        TextField nameField = new TextField();

        /* Make the labels and set properties */
        Label nameLabel = new Label("Username ");
        Label errorLabel = new Label();
        /* Make the button and its handler */
        Button submitClientInfoButton = new Button("Done");
        submitClientInfoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                // TODO Auto-generated method stub
                // Instantiate the client class and start it's thread
                //Client client;
                try {
                    Client client = new Client(nameField.getText());
                    Thread clientThread = new Thread(client);
                    clientThread.setDaemon(true);
                    clientThread.start();
                    threads.add(clientThread);

                    /* Change the scene of the primaryStage */
                    primaryStage.close();
                    primaryStage.setScene(makeChatUI(client));
                    primaryStage.show();
                }
                catch(ConnectException e){
                }
                catch (NumberFormatException | IOException e) {
                }

            }
        });

        //Adding the components to the root pane
        rootPane.add(nameLabel, 0, 1);
        rootPane.add(nameField, 0, 0);
        rootPane.add(submitClientInfoButton, 0, 3, 2, 1);
        rootPane.add(errorLabel, 0, 4);
        return new Scene(rootPane, 400, 400);
    }

    public Scene makeChatUI(Client client) {
        /* Make the root pane and set properties */
        GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(20));
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setHgap(10);
        rootPane.setVgap(10);


        ListView<String> chatListView = new ListView<String>();
        chatListView.setItems(client.chatHistory);

        TextField chatTextField = new TextField();
        chatTextField.setPromptText("Type message, press enter to send");
        chatTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                client.writeToServer(chatTextField.getText());
                chatTextField.clear();
            }
        });

        // Add the components to the root pane
        rootPane.add(chatListView, 0, 0);
        rootPane.add(chatTextField, 0, 1);

        // Make and return the scene
        return new Scene(rootPane, 400, 400);

    }
}
