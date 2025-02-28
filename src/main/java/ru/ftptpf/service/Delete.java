package ru.ftptpf.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import ru.ftptpf.util.CurrentDateTime;
import ru.ftptpf.util.DirectoryUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Delete implements PdfService {


    private static final String PREFIX_FILE_NAME = "delete-page-result-file-";
    private static final String INPUT_DIR_NAME = "4-delete-folder-in";
    private static final String OUTPUT_DIR_NAME = "4-delete-folder-result";

    private final int startPage;
    private final int endPage;

    public Delete(int startPage, int endPage) {
        this.startPage = startPage;
        this.endPage = endPage;
    }

    @Override
    public void run() {

        String outputFileName = PREFIX_FILE_NAME + CurrentDateTime.get() + ".pdf";
        Path inputPath = Path.of("src", "main", "resources", INPUT_DIR_NAME);
        Path outputPath = Path.of("src", "main", "resources", OUTPUT_DIR_NAME, outputFileName);

        DirectoryUtil.createOutputDirectoryIfNotExist(outputPath);

        List<File> files = DirectoryUtil.getPdfFilesFromDirectory(inputPath);

        if (files.size() > 1) {
            throw new RuntimeException("Несколько файлов в папке. Уберите лишние файлы.");
        }

        //TODO сделать проверку чтобы не указывали удаление всех страниц
        try {
            try (PDDocument pdDocument = Loader.loadPDF(files.getFirst())) {
                int numberOfPages = pdDocument.getNumberOfPages();
                System.out.println("Исходный файл содержит " + numberOfPages + " страниц.");
                int startIndex = Math.max(0, startPage - 1);
                int endIndex = Math.min(numberOfPages, endPage) - 1;
                int deletePages = endIndex - startIndex + 1;
                while (deletePages > 0) {
                    pdDocument.removePage(startIndex);
                    deletePages--;
                }
                pdDocument.save(outputPath.toFile());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Из файла был удален указанный диапазон страниц. Новый файл был сохранен в: "
                + System.lineSeparator()
                + outputPath
                + System.lineSeparator()
                + "Если были заданы страницы которые выходили за пределы диапазона, "
                + "они будут удалены в рамках максимально / минимально возможных значений.");
    }
}