package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App {
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        final int numberOfThreads = 1_000_000;
        try {
            for (int i = 0; i < numberOfThreads; i++) {
                Thread thread = new Thread(new BlockedThread());
                thread.start();
            }
        } catch (OutOfMemoryError e) {
            System.err.println("OutOfMemoryError caught!");
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
                // Thread will block indefinitely since the lock is never released
                Thread.sleep(60000);
                //Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                // Handle InterruptedException if needed
            } finally {
                lock.unlock(); // This line will never be reached
            }
        }
    }
}