package ru.ftptpf.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PageExtractor;
import org.apache.pdfbox.pdmodel.PDDocument;
import ru.ftptpf.util.DirectoryUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static ru.ftptpf.util.PdfConst.EXTRACT_PATH;
import static ru.ftptpf.util.PdfConst.EXTRACT_PATH_OUTPUT;

public class Extract implements PdfService {

    private static final Logger LOGGER = LogManager.getLogger(Extract.class);

    private final int startPage;
    private final int endPage;

    public Extract(int startPage, int endPage) {
        this.startPage = startPage;
        this.endPage = endPage;
    }

    public void run() {

        List<File> files = DirectoryUtil.getPdfFilesFromDirectory(EXTRACT_PATH);
        int filesInDirectory = files.size();

        if (filesInDirectory == 1) {
            try {
                try (PDDocument pdDocument = Loader.loadPDF(files.getFirst())) {
                    System.out.println("Исходный файл содержит " + pdDocument.getNumberOfPages() + " страниц.");
                    PageExtractor pageExtractor = new PageExtractor(pdDocument, startPage, endPage);
                    try (PDDocument result = pageExtractor.extract()) {
                        result.save(EXTRACT_PATH_OUTPUT.toFile());
                        System.out.println("Диапазон заданных страницы в виде нового файла был сохранен в: "
                                + System.lineSeparator()
                                + EXTRACT_PATH
                                + System.lineSeparator()
                                + "Если были заданы страницы которые выходили за пределы диапазона, "
                                + "они будут сохранены в рамках максимально / минимально возможных значений.");
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Произошла ошибка в операции извлечения страниц из файла.", e);
                throw new RuntimeException(e);
            }
        } else if (filesInDirectory > 1) {
            System.out.println("В папке несколько файлов. Удалите лишние файлы и попробуйте снова.");
            LOGGER.info("Операция извлечения страниц из файла не была выполнена, так как в папке оказалось не 1,а {} pdf файлов.", filesInDirectory);
        } else {
            System.out.println("В папке нет файлов. Разместите в папке файл и попробуйте еще раз.");
            LOGGER.info("Операция извлечения страниц из файла не была выполнена, так как в папке было {} pdf файлов.", filesInDirectory);
        }
    }
}