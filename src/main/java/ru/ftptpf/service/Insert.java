package ru.ftptpf.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
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
        String file1 = files.getFirst().getName();
        String file2 = files.getLast().getName();

        List<String> names = files.stream()
                .map(File::getName)
                .toList();

        if (!names.contains(mainFileName) && !names.contains(addedFileName)) {
            System.out.println("Файлы в папке не найдены! Проверьте наименования файлов и попробуйте ещё раз!");
        } else if (files.size() == 2) {
            try (PDDocument mainDocument = Loader.loadPDF(
                    file1.equals(mainFileName) ? files.getFirst() : files.getLast());
                 PDDocument addedDocument = Loader.loadPDF(
                         file2.equals(addedFileName) ? files.getLast() : files.getFirst());
                 PDDocument resultDocument = new PDDocument()) {
                int numberOfPagesInMainFile = mainDocument.getNumberOfPages();
                int numberOfPagesInAddedFile = addedDocument.getNumberOfPages();

                int startIndex = 0;
                if (insertAfterThisPage >= numberOfPagesInMainFile) {
                    startIndex = numberOfPagesInMainFile - 1;
                } else if (insertAfterThisPage >= 1) {
                    startIndex = insertAfterThisPage - 1;
                }

                for (int i = 0; i < numberOfPagesInMainFile - 1; i++) {
                    if (startIndex == i) {
                        for (int j = 0; j < numberOfPagesInAddedFile; j++) {
                            resultDocument.addPage(addedDocument.getPage(j));
                        }
                        resultDocument.addPage(mainDocument.getPage(i));
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
