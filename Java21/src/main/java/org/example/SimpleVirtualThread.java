package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.*;

public class SimpleVirtualThread
{
    public static void main( String[] args ) throws InterruptedException {

        Runnable task = () -> {
            HttpClient httpClient = HttpClient.newHttpClient();
            String result = makeHttpRequest(httpClient);
            System.out.println("Thread is running and the result is: " + result);
        };

        final int numberOfThreads = 10000000;
        for (int i = 0; i < numberOfThreads; i++) {
            // Create a new thread and start it
            Thread virtualThread = new Thread.ofVirtual().unstarted(task);
            virtualThread.start();
            virtualThread.join();
            String str = String.format("Thread number %s is running.", i);
            System.out.println(str);
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