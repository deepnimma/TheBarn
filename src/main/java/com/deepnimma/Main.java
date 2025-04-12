package com.deepnimma;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws InterruptedException {
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

            // Thread to continously read messages from the server
            Thread listenerThread = new Thread(() -> {
                String serverMsg;

                try {
                    while ((serverMsg = reader.readLine()) != null) {
                        System.out.println("Server: " + serverMsg);
                    } // while

                    System.out.println("Server closed the connection.");
                } catch (Exception e) {
                    System.out.println("Disconnected from the server.");
                } // try-catch
            });

            listenerThread.setDaemon(true);
            listenerThread.start();

            String userInput;
            while ((userInput = consoleReader.readLine()) != null) {
                writer.println(userInput);

                // If "bye" is sent, wait a moment for the final response
                if ("bye".equalsIgnoreCase(userInput.trim())) {
                    Thread.sleep(500);
                    break;
                } // if
            } // while

            System.out.println("Closing client.");
        } catch (Exception e) {
            e.printStackTrace();
        } // try-catch
    } // main
} // Main

