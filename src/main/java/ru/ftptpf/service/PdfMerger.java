package ru.ftptpf.service;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import ru.ftptpf.util.CurrentDateTime;
import ru.ftptpf.util.DirectoryUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class PdfMerger implements PdfService {

    public void run() {

        String outputFileName = "merge-result-" + CurrentDateTime.get() + ".pdf";
        Path inputPath = Path.of("src", "main", "resources", "1-merge-folder-in");
        Path outputPath = Path.of("src", "main", "resources", "1-merge-folder-result", outputFileName);

        DirectoryUtil.createOutputDirectoryIfNotExist(outputPath);

        List<File> files = DirectoryUtil.getPdfFilesFromDirectory(inputPath);

        if (files.size() < 2) {
            throw new RuntimeException("В папке должно быть минимум два файла");
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
