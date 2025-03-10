package ru.ftptpf.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
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
            File mainFile = files.stream()
                    .filter(file -> file.getName().equals(mainFileName))
                    .toList()
                    .getFirst();
            File addedFile = files.stream()
                    .filter(file -> file.getName().equals(addedFileName))
                    .toList()
                    .getFirst();

            try (PDDocument mainDocument = Loader.loadPDF(mainFile);
                 PDDocument addedDocument = Loader.loadPDF(addedFile);
                 PDDocument resultDocument = new PDDocument()) {

                int pagesInMainFile = mainDocument.getNumberOfPages();
                int pagesInAddedFile = addedDocument.getNumberOfPages();

                PDFMergerUtility mergerUtility = new PDFMergerUtility();
                mergerUtility.setDestinationFileName(INSERT_PATH_OUTPUT.toString());

                if (insertAfterThisPage <= 0) {
                    mergerUtility.addSource(addedFile);
                    mergerUtility.addSource(mainFile);
                    mergerUtility.mergeDocuments(null);
                } else if (insertAfterThisPage >= pagesInMainFile) {
                    mergerUtility.addSource(mainFile);
                    mergerUtility.addSource(addedFile);
                    mergerUtility.mergeDocuments(null);
                } else {
                    for (int i = 0; i < pagesInMainFile; i++) {
                        if (insertAfterThisPage == i) {
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
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
