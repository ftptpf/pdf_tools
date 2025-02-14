package ru.ftptpf.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryCheck {

    private String inputFolder;
    private String outputFolder;

    public DirectoryCheck(String inputFolder, String outputFolder) {
        this.inputFolder = inputFolder;
        this.outputFolder = outputFolder;
    }

/*    public void checkOutputFolder() {
        String outputFileName = "merge-result-" + CurrentDateTime.get() + ".pdf";
        Path inputPath = Path.of("src", "main", "resources", "1-merge-folder-in");
        Path outputPath = Path.of("src", "main", "resources", "1-merge-folder-result", outputFileName);

        try {
            if (!Files.exists(outputPath.getParent())) {
                Files.createDirectory(outputPath.getParent());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/


}
