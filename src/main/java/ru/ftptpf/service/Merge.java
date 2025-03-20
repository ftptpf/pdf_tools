package ru.ftptpf.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import ru.ftptpf.util.DirectoryUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static ru.ftptpf.util.PdfConst.MERGE_PATH;
import static ru.ftptpf.util.PdfConst.MERGE_PATH_OUTPUT;

public class Merge implements PdfService {

    private static final Logger LOGGER = LogManager.getLogger(Merge.class);

    public void run() {

        List<File> files = DirectoryUtil.getPdfFilesFromDirectory(MERGE_PATH);
        int filesInDirectory = files.size();

        if (filesInDirectory >= 2 && filesInDirectory <= 10) {
            PDFMergerUtility mergerUtility = new PDFMergerUtility();
            mergerUtility.setDestinationFileName(MERGE_PATH_OUTPUT.toString());

            for (File file : files) {
                try {
                    mergerUtility.addSource(file);
                } catch (FileNotFoundException e) {
                    LOGGER.error("Произошла ошибка при группировке файлов перед слиянием. Файл {} не был найден.", file, e);
                    throw new RuntimeException(e);
                }
            }
            try {
                mergerUtility.mergeDocuments(null);
                System.out.println("Объединенный файл создан: " + MERGE_PATH_OUTPUT);
            } catch (IOException e) {
                LOGGER.error("Произошла ошибка при слиянии файлов.", e);
                throw new RuntimeException(e);
            }
        } else if (filesInDirectory == 1) {
            System.out.println("В папке 1 pdf файл. Добавьте еще как минимум 1 pdf файл и попробуйте снова.");
            LOGGER.info("Операция слияния нескольких pdf фалов не была выполнена, так как в папке оказалось {} pdf файлов.", filesInDirectory);
        } else if (filesInDirectory > 10) {
            System.out.println("В папке больше 10 pdf файлов. Это слишком много. Удалите лишние и попробуйте снова.");
            LOGGER.info("Операция слияния нескольких pdf фалов не была выполнена, так как в папке оказалось {} pdf файлов. " +
                    "Их должно быть не более 10.", filesInDirectory);
        } else {
            System.out.println("В папке нет файлов. Добавьте минимум 2 pdf файла и попробуйте снова.");
            LOGGER.info("Операция слияния нескольких pdf фалов не была выполнена, так как в папке было {} pdf файлов.", filesInDirectory);
        }
    }
}