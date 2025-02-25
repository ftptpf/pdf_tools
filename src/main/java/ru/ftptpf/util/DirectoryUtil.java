package ru.ftptpf.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

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

    public static void createOutputDirectoryIfNotExist(Path outputPath) {
        try {
            if (!Files.exists(outputPath.getParent())) {
                Files.createDirectory(outputPath.getParent());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
