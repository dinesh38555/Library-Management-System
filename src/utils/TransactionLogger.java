package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import models.Book;

public class TransactionLogger {

    private static final String LOG_FILE_PATH = "data/transaction_log.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Logs a borrow or return transaction
    public static void logTransaction(String userId, String action, Book book) {
        String timestamp = DATE_FORMAT.format(new Date());
        String logEntry = String.format(
            "%s | User: %s | Action: %s | Book ID: %s | Title: \"%s\"%n",
            timestamp, userId, action, book.getId(), book.getTitle()
        );

        try (FileWriter writer = new FileWriter(LOG_FILE_PATH, true)) {
            writer.write(logEntry);
        } catch (IOException e) {
            System.err.println("Error logging transaction: " + e.getMessage());
        }
    }
}
