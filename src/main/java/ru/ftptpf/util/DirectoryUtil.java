package ru.ftptpf.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static ru.ftptpf.util.PdfConst.*;

public final class DirectoryUtil {

    private static final Logger LOGGER = LogManager.getLogger(DirectoryUtil.class);

    private DirectoryUtil() {
    }

    public static List<File> getPdfFilesFromDirectory(Path path) {
        try (Stream<Path> list = Files.list(path)) {
            return list.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .filter(file -> file.getName().endsWith(".pdf"))
                    .toList();
        } catch (IOException e) {
            LOGGER.error("Произошла ошибка получения списка файлов из директории {}. ", path, e);
            throw new RuntimeException(e);
        }
    }

    public static void createDirectoryIfNotExist(Path path) {
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
        } catch (IOException e) {
            LOGGER.error("Произошла ошибка создания директории: {}. ", path, e);
            throw new RuntimeException(e);
        }
    }

    public static void createAllDirectories() {
        createDirectoryIfNotExist(DELETE_PATH);
        createDirectoryIfNotExist(INSERT_PATH);
        createDirectoryIfNotExist(MERGE_PATH);
        createDirectoryIfNotExist(EXTRACT_PATH);
    }
}
