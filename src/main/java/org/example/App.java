package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Starting calls!" );
        int numberOfThreads = 10;

        // Create an HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // Create an array of CompletableFuture to store the results of each thread
        CompletableFuture[] futures = new CompletableFuture[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            // Create a new thread for making an HTTP request
            int threadNumber = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                String result = makeHttpRequest(httpClient);
                System.out.println("Thread " + threadNumber + ": " + result);
            });

            futures[i] = future;
        }

        // Wait for all threads to complete
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);
        allOf.join();

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
