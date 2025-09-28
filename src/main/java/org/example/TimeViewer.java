package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeViewer {
    public static String view() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss:ms");
        return now.format(formatter);
    }
}
