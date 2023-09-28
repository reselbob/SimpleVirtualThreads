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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App
{
    private static final Lock lock = new ReentrantLock();
    public static void main( String[] args ) throws InterruptedException {


        final int numberOfThreads = 1_000_000;
        ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();
        try {
            for (int i = 0; i < numberOfThreads; i++) {
                service.submit(() -> {
                    // Create a new thread and start it
                    Thread thread = new Thread(new BlockedThread());
                    thread.start();
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

                System.exit(1);

            } catch (IOException err) {
                // Handle exceptions
                err.printStackTrace();
            }
        }


    }

    static class BlockedThread implements Runnable {
        @Override
        public void run() {
            try {
                lock.lock();
                System.out.println("Thread output");
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                // Handle InterruptedException if needed
            } finally {
                lock.unlock(); // This line will never be reached
            }
        }
    }
}