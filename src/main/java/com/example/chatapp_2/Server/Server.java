package com.example.chatapp_2.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Server implements Runnable {
    private int portNumber;
    private final ServerSocket socket;
    private final ArrayList<Socket> clients;
    private final ArrayList<ClientThread> clientThreads;
    public ObservableList<String> serverLog;
    public ObservableList<String> clientNames;
    public Server(int portNumber) throws IOException {
        this.portNumber = portNumber;
        serverLog = FXCollections.observableArrayList();
        clientNames = FXCollections.observableArrayList();
        clients = new ArrayList<Socket>();
        clientThreads = new ArrayList<ClientThread>();
        socket = new ServerSocket(portNumber);

    }

    public void run() {

        try {
            /* Infinite loop to accept any incoming connection requests */
            while (true) {

                final Socket clientSocket = socket.accept();
                /* Add the incoming socket connection to the list of clients */
                clients.add(clientSocket);
                ClientThread clientThreadHolderClass = new ClientThread(clientSocket, this);
                Thread clientThread = new Thread(clientThreadHolderClass);
                clientThreads.add(clientThreadHolderClass);
                clientThread.setDaemon(true);
                clientThread.start();
                ServerApplication.threads.add(clientThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeToAllSockets(String input) {
        for (ClientThread clientThread : clientThreads) {
            clientThread.writeToServer(input);
        }
    }

}
