package ru.ftptpf.service;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import ru.ftptpf.util.DirectoryUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static ru.ftptpf.util.PdfConst.MERGE_PATH;
import static ru.ftptpf.util.PdfConst.MERGE_PATH_OUTPUT;

public class Merge implements PdfService {

    public void run() {

        List<File> files = DirectoryUtil.getPdfFilesFromDirectory(MERGE_PATH);

        if (files.size() >= 2 && files.size() <= 10) {
            PDFMergerUtility mergerUtility = new PDFMergerUtility();
            mergerUtility.setDestinationFileName(MERGE_PATH_OUTPUT.toString());

            for (File file : files) {
                try {
                    mergerUtility.addSource(file);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                mergerUtility.mergeDocuments(null);
                System.out.println("Объединенный файл создан: " + MERGE_PATH_OUTPUT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (files.size() == 1) {
            System.out.println("В папке один pdf файл. Добавьте еще как минимум один и попробуйте снова.");
        } else if (files.size() > 10) {
            System.out.println("В папке больше 10 pdf файлов. Это слишком много. Удалите лишние и попробуйте снова.");
        } else {
            System.out.println("В папке нет файлов. Добавьте минимум 2 pdf файла и попробуйте снова.");
        }
    }
}