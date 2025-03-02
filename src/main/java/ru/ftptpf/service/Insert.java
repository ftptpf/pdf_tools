package ru.ftptpf.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.fdf.FDFDocument;
import ru.ftptpf.util.CurrentDateTime;
import ru.ftptpf.util.DirectoryUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Insert implements PdfService {

    private static final String PREFIX_FILE_NAME = "extracted-result-file-";
    private static final String DIR_NAME = "2-insert-folder";

    private final String mainFileName;
    private final String addedFileName;
    private final int insertAfterThisPage;

    public Insert(String mainFileName, String addedFileName, int insertAfterThisPage) {
        this.mainFileName = mainFileName;
        this.addedFileName = addedFileName;
        this.insertAfterThisPage = insertAfterThisPage;
    }

    @Override
    public void run() {
        String outputFileName = PREFIX_FILE_NAME + CurrentDateTime.get() + ".pdf";
        Path path = Path.of("src", "main", "resources", DIR_NAME);
        File mainFile = path.resolve(mainFileName).toFile();
        File addedFile = path.resolve(addedFileName).toFile();

        List<File> files = DirectoryUtil.getPdfFilesFromDirectory(path);

        if (files.size() != 2) {
            throw new RuntimeException("В папке должно быть 2 файла.");
        }

        try (PDDocument mainDocument = Loader.loadPDF(mainFile);
             FDFDocument addedDocument = Loader.loadFDF(addedFile)) {
            int numberOfPages = mainDocument.getNumberOfPages();
            System.out.println(numberOfPages);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
