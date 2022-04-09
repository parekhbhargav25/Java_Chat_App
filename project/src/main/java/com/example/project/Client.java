package com.example.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Client {
//    public static void main(String[] args) {
//        Socket socket;
//        InputStreamReader inputstreamReader;
//        OutputStreamWriter outputstreamiriter;
//        BufferedReader bufferedReader;
//        BufferedWriter bufferedwriter;
//        try {
//            socket = new Socket("localhost", 1997);
//            inputstreamReader = new InputStreamReader(socket.getInputStream());
//            outputstreamiriter = new OutputStreamWriter(socket.getOutputStream());
//
//            bufferedReader = new BufferedReader (inputstreamReader);
//            bufferedwriter = new BufferedWriter(outputstreamiriter);
//            Scanner scanner = new Scanner(System. in);
//            while (true) {
//                String msgTosend = scanner.nextLine();
//                bufferedwriter. write(msgTosend);
//                bufferedwriter.newLine();
//                bufferedwriter.flush();
//
//                System.out.println("Server: " + bufferedReader.readLine());
//
//                if (msgTosend.equalsIgnoreCase("exit") ) {
//                    socket.close();
//                    break;
//                }
//            }
//
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    @FXML TextField username;

    @FXML
    protected void chat(ActionEvent event) {
        GridPane gridPane = new GridPane();
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        TextField textField = new TextField();
        textField.setPromptText("Enter message");
        gridPane.add(textArea, 0, 0);
        gridPane.add(textField, 0, 1);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setVgap(20);
        gridPane.setMaxWidth(320);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("localhost", 6666);

                    PrintWriter writer = new PrintWriter(socket.getOutputStream());

                    textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent ke) {
                            if (ke.getCode().equals(KeyCode.ENTER)) {
//                                textArea.appendText(textField.getText() + "\n");
                                if(username.getText().length() < 10) {
                                    writer.print(username.getText().length() + " ");
                                }
                                else {
                                    writer.print(username.getText().length());
                                }
                                writer.print(username.getText());
                                writer.println(textField.getText());
                                writer.flush();

                                textField.clear();
                            }
                        }
                    });

                    BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message;
                    while((message = inStream.readLine()) != null) {
                        textArea.appendText(message + "\n");
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(new Pane(gridPane), 320, 300);
        stage.setTitle("Client");
        stage.setScene(scene);
        stage.show();
    }

}


