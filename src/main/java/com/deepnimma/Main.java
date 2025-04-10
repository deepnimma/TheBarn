package com.deepnimma;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        String host = "localhost"; // Change to the server's hostname or IP as needed.
        int port = 12345;          // Must match the server's port.

        try (Socket socket = new Socket(host, port);
             // Writer to send messages to the server.
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             // Reader to get responses from the server.
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             // Reader for user input.
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the server at " + host + ":" + port);
            
            // Thread to continuously read messages from the server.
            Thread listenerThread = new Thread(() -> {
                String serverMsg;
                try {
                    while ((serverMsg = reader.readLine()) != null) {
                        System.out.println("Server: " + serverMsg);
                    }
                } catch (Exception e) {
                    System.out.println("Disconnected from the server.");
                }
            });
            listenerThread.start();

            // Main thread handles sending messages to the server.
            String userInput;
            while ((userInput = consoleReader.readLine()) != null) {
                writer.println(userInput);
                // If the user types "bye", break the loop and close the connection.
                if ("bye".equalsIgnoreCase(userInput.trim())) {
                    break;
                }
            }
            
            System.out.println("Connection closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

