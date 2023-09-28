package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class App {
    public static void main(String[] args) {
        int numberOfThreads = 1_000_000;
        try {
            for (int i = 1; i <= numberOfThreads; i++) {
                // Create a new thread and start it
                // Specify the file path
                String filePath = "output.txt";
                // Create a new thread and start it
                Thread appendThread = new Thread(new AppendTask(filePath, i));
                appendThread.start();
            }
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

}
