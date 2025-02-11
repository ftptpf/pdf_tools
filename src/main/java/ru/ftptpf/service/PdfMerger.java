package ru.ftptpf.service;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import ru.ftptpf.util.CurrentDateTime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class PdfMerger {

    public void merge() {

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

        List<File> files;

        try (Stream<Path> pathStream = Files.list(inputPath)) {
            files = pathStream
                    .map(Path::toFile)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PDFMergerUtility mergerUtility = new PDFMergerUtility();
        mergerUtility.setDestinationFileName(outputPath.toString());

        for (File file : files) {
            try {
                mergerUtility.addSource(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            mergerUtility.mergeDocuments(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Объединенный файл создан: " + outputPath);
    }


}
