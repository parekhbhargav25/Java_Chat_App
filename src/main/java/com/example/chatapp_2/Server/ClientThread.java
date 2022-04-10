package com.example.chatapp_2.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;


/* Thread Class for each incoming client */
public class ClientThread implements Runnable {

    // The socket of the client
    private Socket clientSocket;
    // Server class from which thread was called
    private Server baseServer;
    private BufferedReader incomingMessageReader;
    private PrintWriter outgoingMessageWriter;
    // The name of the client
    private String clientName;

    public ClientThread(Socket clientSocket, Server baseServer) {
        this.setClientSocket(clientSocket);
        this.baseServer = baseServer;
        try {
            incomingMessageReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // Writer to write outgoing messages from the server to the client
            outgoingMessageWriter = new PrintWriter(clientSocket.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            this.clientName = incomingMessageReader.readLine();
            String inputToServer;
            while (true) {
                inputToServer = incomingMessageReader.readLine();
                baseServer.writeToAllSockets(inputToServer);
            }
        } catch (SocketException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToServer(String input) {
        outgoingMessageWriter.println(input);
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
