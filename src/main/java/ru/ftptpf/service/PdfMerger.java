package ru.ftptpf.service;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class PdfMerger {

    public void run(String[] args) {


        if (args.length < 3) {
            throw new IllegalArgumentException("Неверное количество переданных аргументов. "
                    + "Должен быть указан 1 итоговый фай и минимум 2 которые будут объединены.");
        }

        File outputFile = new File(args[0]);
        List<File> files = List.of(args).subList(1, args.length).stream()
                .map(File::new)
                .toList();

        PDFMergerUtility mergerUtility = new PDFMergerUtility();
        mergerUtility.setDestinationFileName(outputFile.toString());

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
        System.out.println("Объединение файлов завершено");
    }


}
