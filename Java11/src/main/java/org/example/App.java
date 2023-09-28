package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App {
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        final int numberOfThreads = 50000;
        try {
            for (int i = 0; i < numberOfThreads; i++) {
                Thread thread = new Thread(new BlockedThread(i));
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
        private int count = 0;
        public BlockedThread(int count){
           this.count = count;
        }
        @Override
        public void run() {
            try {
                lock.lock();
                System.out.println("Thread output for " + this.count);
                Thread.sleep(6000);
                //Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                // Handle InterruptedException if needed
            } finally {
                lock.unlock(); // This line will never be reached
            }
        }
    }
}