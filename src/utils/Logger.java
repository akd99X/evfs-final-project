package utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static final String LOG_FILE = "access.log";

    public static void log(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write("[" + timestamp + "] " + message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("⚠️ Failed to write log: " + e.getMessage());
        }
    }

    public static void readLog() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                found = true;
            }
            if (!found) {
                System.out.println("No activity yet.");
            }
        } catch (IOException e) {
            System.err.println("⚠️ Failed to read log: " + e.getMessage());
        }
    }
}
