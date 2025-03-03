package ru.ftptpf.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.fdf.FDFDocument;
import ru.ftptpf.util.DirectoryUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static ru.ftptpf.util.PdfConst.INSERT_PATH;

public class Insert implements PdfService {

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
        File mainFile = INSERT_PATH.resolve(mainFileName).toFile();
        File addedFile = INSERT_PATH.resolve(addedFileName).toFile();

        List<File> files = DirectoryUtil.getPdfFilesFromDirectory(INSERT_PATH);

        if (files.size() == 2) {
            try (PDDocument mainDocument = Loader.loadPDF(mainFile);
                 FDFDocument addedDocument = Loader.loadFDF(addedFile)) {
                int numberOfPages = mainDocument.getNumberOfPages();
                System.out.println(numberOfPages);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
