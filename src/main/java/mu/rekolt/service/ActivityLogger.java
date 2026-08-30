package mu.rekolt.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class ActivityLogger {

    private static final String LOG_PATH = "output/run-log.txt";

    public static void append(int memberSectionCount) {
        //I constructed the formatted output string before opening the file resource
        String logMessage = LocalDateTime.now() + " | Season report compiled for "
                + memberSectionCount + " member sections.";

        //I used try-with-resources to open the file in append mode and ensure auto-closing
        try (FileWriter fw = new FileWriter(LOG_PATH, true);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println(logMessage);
        } catch (IOException e) {
            //I caught file write issues directly and displayed a clear warning message
            System.out.println("Warning: Unable to update activity log file (" + e.getMessage() + ")");
        }
    }
}