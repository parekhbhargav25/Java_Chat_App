package com.example.chatapp_2;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server {

//    static TextArea messages = new TextArea();
//    private Parent createContent() {
//        messages.setPrefHeight(550);
//        TextField input =new TextField();
//        VBox root = new VBox(20, messages, input);
//        root.setPrefSize(600, 600);
//        return root;
//    }
//    @Override
//    public void start(Stage primarystage) throws Exception {
//        primarystage.setScene(new Scene(createContent()));
//        primarystage.show();
//    }
    public static void main(String[] args) throws IOException {

        Socket socket;
        InputStreamReader inputStreamReader;
        OutputStreamWriter outputStreamwriter;
        BufferedReader bufferedReader;
        BufferedWriter bufferedwriter;
        ServerSocket serverSocket;

        serverSocket = new ServerSocket( 1997);

        String msgfromClient = "";
        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println("Client Connected");
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamwriter = new OutputStreamWriter(socket.getOutputStream());

                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedwriter = new BufferedWriter(outputStreamwriter);
                Scanner scanner = new Scanner(System. in);

                while (true) {
                    msgfromClient = bufferedReader.readLine();
                    System.out.println("Client: " + msgfromClient );
                    String msgTosend = scanner.nextLine();
                    bufferedwriter. write(msgTosend);
                    //bufferedwriter.write("Message received");
                    bufferedwriter.newLine();
                    bufferedwriter.flush();
                    if (msgfromClient.equalsIgnoreCase("exit") || msgTosend.equalsIgnoreCase("exit") ) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}


