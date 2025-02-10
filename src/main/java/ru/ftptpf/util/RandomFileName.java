package ru.ftptpf.util;

import java.util.Random;

/**
 * Создает случайное пятизначное число
 */
public final class RandomFileName {

    private static final Random RANDOM = new Random();

    private RandomFileName() {
    }

    public static String get(int length) {
        return String.valueOf(RANDOM.nextInt(length) % 99999 + 10000);
    }
}
