package ru.ftptpf.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import ru.ftptpf.util.DirectoryUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static ru.ftptpf.util.PdfConst.DELETE_PATH;
import static ru.ftptpf.util.PdfConst.DELETE_PATH_OUTPUT;

public class Delete implements PdfService {

    private final int startPage;
    private final int endPage;

    public Delete(int startPage, int endPage) {
        this.startPage = startPage;
        this.endPage = endPage;
    }

    @Override
    public void run() {

        List<File> files = DirectoryUtil.getPdfFilesFromDirectory(DELETE_PATH);

        if (files.size() == 1) {
            try {
                try (PDDocument pdDocument = Loader.loadPDF(files.getFirst())) {
                    int numberOfPages = pdDocument.getNumberOfPages();
                    System.out.println("Исходный файл содержит " + numberOfPages + " страниц.");

                    int startIndex = Math.max(0, startPage - 1);
                    int endIndex = Math.min(numberOfPages, endPage) - 1;

                    int deletePages = endIndex - startIndex + 1;
                    if (startIndex != 0 && endIndex != (numberOfPages - 1)) {
                        while (deletePages > 0) {
                            pdDocument.removePage(startIndex);
                            deletePages--;
                        }
                        pdDocument.save(DELETE_PATH_OUTPUT.toFile());
                        System.out.println("Из файла был удален указанный диапазон страниц. Новый файл был сохранен в: "
                                + System.lineSeparator()
                                + DELETE_PATH_OUTPUT
                                + System.lineSeparator()
                                + "Если были заданы страницы которые выходили за пределы диапазона, "
                                + "они будут удалены в рамках максимально / минимально возможных значений.");
                    } else {
                        System.out.println("Вы не можете удалить все страницы. Попробуйте задать другой диапазон.");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (files.size() > 1) {
            System.out.println("В папке несколько файлов. Удалите лишние файлы и попробуйте снова.");
        } else {
            System.out.println("В папке нет файлов. Разместите в папке 1 файл и попробуйте еще раз.");
        }
    }
}