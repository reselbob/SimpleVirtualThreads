package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.*;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws InterruptedException {

        Runnable task = () -> {
            HttpClient httpClient = HttpClient.newHttpClient();
            String result = makeHttpRequest(httpClient);
            System.out.println("Thread is running and the result is: " + result);
        };
        final int taskCount = 10000;
        ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < taskCount; i++) {
            service.submit(() -> {
                Thread thread = new Thread(task);
                thread.start();
                long id = Thread.currentThread().threadId();
                System.out.println(id);
            });
        }
        service.close();
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