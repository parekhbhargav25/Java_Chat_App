package com.example.chatapp_2.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Client implements Runnable {
    /* The Socket of the Client */
    private Socket clientSocket;
    private BufferedReader serverToClientReader;
    private PrintWriter clientToServerWriter;
    private String name;
    public ObservableList<String> chatHistory;

    public Client( String newName) throws IOException {

        /* Try to establish a connection to the server */
        clientSocket = new Socket("localhost", 1234);
        // Instantiate writers and readers to the socket
        serverToClientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        clientToServerWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        chatHistory = FXCollections.observableArrayList();
        // Send name data to the server
        name = newName;
        clientToServerWriter.println(newName);


    }

    public void writeToServer(String input) {
        String timestamp = new SimpleDateFormat("h:mm").format(new Date());
        clientToServerWriter.println("["+ timestamp+ "] " + name + " : " + input);
    }

    public void run() {
        //loop to update the chat history
        while (true) {
            try {
                String inputFromServer = serverToClientReader.readLine();
                Platform.runLater(new Runnable() {
                    public void run() {
                        chatHistory.add(inputFromServer);
                    }
                });

            } catch (SocketException e){

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
