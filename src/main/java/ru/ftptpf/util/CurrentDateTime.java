package ru.ftptpf.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Текущая дата и время
 */
public final class CurrentDateTime {

    private CurrentDateTime() {
    }

    public static String get() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
    }
}
