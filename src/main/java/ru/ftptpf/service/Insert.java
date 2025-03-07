package ru.ftptpf.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import ru.ftptpf.util.DirectoryUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static ru.ftptpf.util.PdfConst.INSERT_PATH;
import static ru.ftptpf.util.PdfConst.INSERT_PATH_OUTPUT;

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

        List<File> files = DirectoryUtil.getPdfFilesFromDirectory(INSERT_PATH);

        List<String> names = files.stream()
                .map(File::getName)
                .toList();

        if (!names.contains(mainFileName) && !names.contains(addedFileName)) {
            System.out.println("Файлы в папке не найдены! Проверьте наименования файлов и попробуйте ещё раз!");
        } else if (files.size() >= 2 && files.size() <= 10) {
            try (PDDocument mainDocument = Loader.loadPDF(
                    files.stream()
                            .filter(file -> file.getName().equals(mainFileName))
                            .toList()
                            .getFirst());
                 PDDocument addedDocument = Loader.loadPDF(
                         files.stream()
                                 .filter(file -> file.getName().equals(addedFileName))
                                 .toList()
                                 .getFirst());
                 PDDocument resultDocument = new PDDocument()) {

                int pagesInMainFile = mainDocument.getNumberOfPages();
                int pagesInAddedFile = addedDocument.getNumberOfPages();

                int startIndex = 0;
                if (insertAfterThisPage >= pagesInMainFile) {
                    startIndex = pagesInMainFile - 1;
                } else if (insertAfterThisPage >= 1) {
                    startIndex = insertAfterThisPage;
                }

                //TODO:решить проблему с вставкой когда вставляется в конец файла
                for (int i = 0; i < pagesInMainFile; i++) {
                    if (startIndex == i) {
                        for (int j = 0; j < pagesInAddedFile; j++) {
                            PDPage page = addedDocument.getPage(j);
                            resultDocument.addPage(page);
                            System.out.println("Мы добавили страницу " + page.toString() + " под индексом №" + j + " из файла: " + addedFileName);
                        }
                    }
                    PDPage page = mainDocument.getPage(i);
                    resultDocument.addPage(page);
                    System.out.println("Мы добавили страницу " + page.toString() + " под индексом №" + i + " из файла: " + mainFileName);
                }

                try {
                    resultDocument.save(INSERT_PATH_OUTPUT.toFile());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
