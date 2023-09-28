package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.*;
public class App
{
    public static void main( String[] args ) throws InterruptedException {

        Runnable task = () -> {
            HttpClient httpClient = HttpClient.newHttpClient();
            String result = makeHttpRequest(httpClient);
            System.out.println("Thread is running and the result is: " + result);
        };
        final int numberOfThreads = 1_000_000;
        ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();
        try {
            for (int i = 0; i < numberOfThreads; i++) {
                service.submit(() -> {
                    // Specify the file path
                    String filePath = "output.txt";
                    // Create a new thread and start it
                    Thread appendThread = new Thread(new AppendTask(filePath, i));
                    appendThread.start();
                    long id = Thread.currentThread().threadId();
                    System.out.println(id);
                });
            }
            service.close();
        } catch (OutOfMemoryError e) {
            // Handle the OutOfMemoryError
            System.err.println(e);
            try {
                // Create a FileWriter with append mode (true)
                FileWriter fileWriter = new FileWriter("error.log", true);
                // Wrap the FileWriter with a BufferedWriter for efficient writing
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                // Append the line to the file
                bufferedWriter.write("Caught OutOfMemoryError: " + e.getMessage());
                // Add a new line after the appended text
                bufferedWriter.newLine();

                // Close the resources
                bufferedWriter.close();

                fileWriter.close();

            } catch (IOException err) {
                // Handle exceptions
                err.printStackTrace();
            }
        }
    }

    private static String makeHttpRequest(HttpClient httpClient) {
        // Replace this URL with the API you want to call
        String apiUrl = "http://worldtimeapi.org/api/timezone/America/New_York";

        // Create an HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();

        // Send the request asynchronously and handle the response
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return "Response: " + response.body();
            } else {
                return "HTTP request failed with status code: " + response.statusCode();
            }
        } catch (Exception e) {
            return "Error making HTTP request: " + e.getMessage();
        }
    }
}