package com.example.chatapp_2;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Client {
    public static void main(String[] args) {
        Socket socket;
        InputStreamReader inputstreamReader;
        OutputStreamWriter outputstreamiriter;
        BufferedReader bufferedReader;
        BufferedWriter bufferedwriter;
        try {
            socket = new Socket("localhost", 1997);
            inputstreamReader = new InputStreamReader(socket.getInputStream());
            outputstreamiriter = new OutputStreamWriter(socket.getOutputStream());

            bufferedReader = new BufferedReader (inputstreamReader);
            bufferedwriter = new BufferedWriter(outputstreamiriter);
            Scanner scanner = new Scanner(System. in);
            while (true) {
                String msgTosend = scanner.nextLine();
                bufferedwriter. write(msgTosend);
                bufferedwriter.newLine();
                bufferedwriter.flush();

                System.out.println("Server: " + bufferedReader.readLine());

                if (msgTosend.equalsIgnoreCase("exit") ) {
                    socket.close();
                    break;
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



