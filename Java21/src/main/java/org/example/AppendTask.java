package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class AppendTask implements Runnable {
    private final String filePath;
    private final int count;

    public AppendTask(String filePath, int count) {
        this.filePath = filePath;
        this.count = count;
    }

    @Override
    public void run() {
        try {
            // Create a FileWriter with append mode (true)
            FileWriter fileWriter = new FileWriter(filePath, true);
            // Wrap the FileWriter with a BufferedWriter for efficient writing
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Append the line to the file
            bufferedWriter.write("I am call " + count);
            // Add a new line after the appended text
            bufferedWriter.newLine();

            // Close the resources
            bufferedWriter.close();

            fileWriter.close();

            System.out.println("Line appended successfully by Thread: " + Thread.currentThread().getName());
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }
}