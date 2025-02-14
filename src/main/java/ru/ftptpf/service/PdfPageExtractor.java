package ru.ftptpf.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PageExtractor;
import org.apache.pdfbox.pdmodel.PDDocument;
import ru.ftptpf.util.CurrentDateTime;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class PdfPageExtractor implements PdfService {

    private static final String PREFIX_FILE_NAME = "extracted-result-file-";
    private static final String INPUT_DIR_NAME = "3-extract-folder-in";
    private static final String OUTPUT_DIR_NAME = "3-extract-folder-result";

    private int startPage;
    private int endPage;

    public PdfPageExtractor(int startPage, int endPage) {
        this.startPage = startPage;
        this.endPage = endPage;
    }

    public void run() {

        String outputFileName = PREFIX_FILE_NAME + CurrentDateTime.get() + ".pdf";
        Path inputPath = Path.of("src", "main", "resources", INPUT_DIR_NAME);
        Path outputPath = Path.of("src", "main", "resources", OUTPUT_DIR_NAME, outputFileName);

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

        if (files.size() > 1) {
            throw new RuntimeException("Несколько файлов в папке. Уберите лишние файлы.");
        }

        try {
            try (PDDocument pdDocument = Loader.loadPDF(files.getFirst())) {
                PageExtractor pageExtractor = new PageExtractor(pdDocument, startPage, endPage);
                try (PDDocument result = pageExtractor.extract()) {
                    result.save(outputPath.toFile());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Из исходного файла были извлечены страницы с "
                + startPage + " по "
                + endPage + " и они в виде нового файла сохранены в: "
                + outputPath);

    }
}
