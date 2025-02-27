package ru.ftptpf.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PageExtractor;
import org.apache.pdfbox.pdmodel.PDDocument;
import ru.ftptpf.util.CurrentDateTime;
import ru.ftptpf.util.DirectoryUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class PdfPageExtractor implements PdfService {

    private static final String PREFIX_FILE_NAME = "extracted-result-file-";
    private static final String INPUT_DIR_NAME = "3-extract-folder-in";
    private static final String OUTPUT_DIR_NAME = "3-extract-folder-result";

    private final int startPage;
    private final int endPage;

    public PdfPageExtractor(int startPage, int endPage) {
        this.startPage = startPage;
        this.endPage = endPage;
    }

    public void run() {

        String outputFileName = PREFIX_FILE_NAME + CurrentDateTime.get() + ".pdf";
        Path inputPath = Path.of("src", "main", "resources", INPUT_DIR_NAME);
        Path outputPath = Path.of("src", "main", "resources", OUTPUT_DIR_NAME, outputFileName);

        DirectoryUtil.createOutputDirectoryIfNotExist(outputPath);

        List<File> files = DirectoryUtil.getPdfFilesFromDirectory(inputPath);

        if (files.size() > 1) {
            throw new RuntimeException("Несколько файлов в папке. Уберите лишние файлы.");
        }
        try {
            try (PDDocument pdDocument = Loader.loadPDF(files.getFirst())) {
                System.out.println("Исходный файл содержит " + pdDocument.getNumberOfPages() + " страниц.");
                PageExtractor pageExtractor = new PageExtractor(pdDocument, startPage, endPage);
                try (PDDocument result = pageExtractor.extract()) {
                    result.save(outputPath.toFile());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Диапазон заданных страницы в виде нового файла был сохранен в: "
                + System.lineSeparator()
                + outputPath
                + System.lineSeparator()
                + "Если мы задали страницы которые выходили за пределы диапазона, "
                + "то они будут сохранены в рамках максимально возможных значений.");
    }
}
