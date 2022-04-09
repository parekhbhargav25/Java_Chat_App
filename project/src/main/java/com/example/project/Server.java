package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;


public class Server implements Initializable {

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
//    public static void main(String[] args) throws IOException {
//
//        Socket socket;
//        InputStreamReader inputStreamReader;
//        OutputStreamWriter outputStreamwriter;
//        BufferedReader bufferedReader;
//        BufferedWriter bufferedwriter;
//        ServerSocket serverSocket;
//
//        serverSocket = new ServerSocket( 1997);
//
//        String msgfromClient = "";
//        while (true) {
//            try {
//                socket = serverSocket.accept();
//                System.out.println("Client Connected");
//                inputStreamReader = new InputStreamReader(socket.getInputStream());
//                outputStreamwriter = new OutputStreamWriter(socket.getOutputStream());
//
//                bufferedReader = new BufferedReader(inputStreamReader);
//                bufferedwriter = new BufferedWriter(outputStreamwriter);
//                Scanner scanner = new Scanner(System. in);
//
//                while (true) {
//                    msgfromClient = bufferedReader.readLine();
//                    System.out.println("Client: " + msgfromClient );
//                    String msgTosend = scanner.nextLine();
//                    bufferedwriter. write(msgTosend);
//                    //bufferedwriter.write("Message received");
//                    bufferedwriter.newLine();
//                    bufferedwriter.flush();
//                    if (msgfromClient.equalsIgnoreCase("exit") || msgTosend.equalsIgnoreCase("exit") ) {
//                        break;
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//    }

    //Chat server log
    @FXML TextArea textArea;

    //Start server
    public void initialize(URL location, ResourceBundle resources) {
        textArea.setEditable(false);

        //Multithreaded server
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Initialize server
                    ServerSocket server = new ServerSocket(6666);

                    while(true) {
                        Socket socket = server.accept();

                        textArea.appendText("Server online\n");

                        BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter writer = new PrintWriter(socket.getOutputStream());

                        //Read data from client
                        boolean connected = false;
                        String message;
                        while((message = inStream.readLine()) != null) {
                            int len = Integer.parseInt(message.substring(0,2).trim());
                            String timestamp = new SimpleDateFormat("h:mm").format(new Date());

                            //Log new clients
                            if(!connected) {
                                connected = true;
                                textArea.appendText("[" + timestamp + "]");
                                textArea.appendText(" User <" + message.substring(2, len + 2) + "> connected.\n");
                            }

                            //Add to log
                            textArea.appendText("[" + timestamp + "]");
                            textArea.appendText(" <" + message.substring(2, len + 2) + "> ");
                            textArea.appendText(message.substring(len + 2) + "\n");

                            //Send chat data back to client
                            writer.print("[" + timestamp + "]");
                            writer.print(" <" + message.substring(2, len + 2) + "> ");
                            writer.print(message.substring(len + 2) + "\n");
                            writer.flush();
                        }
                    }
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}

