package ru.ftptpf.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static ru.ftptpf.util.PdfConst.*;

public final class DirectoryUtil {

    private DirectoryUtil() {
    }

    public static List<File> getPdfFilesFromDirectory(Path path) {
        try (Stream<Path> list = Files.list(path)) {
            return list.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .filter(file -> file.getName().endsWith(".pdf"))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createDirectoryIfNotExist(Path path) {
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
        } catch (IOException e) {
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
