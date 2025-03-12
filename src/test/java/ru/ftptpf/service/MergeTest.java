package ru.ftptpf.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import ru.ftptpf.util.DirectoryUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static ru.ftptpf.util.PdfConst.MERGE_PATH;

class MergeTest {

    private PrintStream originalSystemOut;
    private ByteArrayOutputStream systemOutContent;

    @BeforeEach
    void redirectSystemOutStream() {
        originalSystemOut = System.out;
        systemOutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOutContent));
    }

    @AfterEach
    void restoreSystemOutStream() {
        System.setOut(originalSystemOut);
    }

    @Test
    public void whenNoFilesInFolder() {
        try (MockedStatic<DirectoryUtil> mockDirectoryUtil = mockStatic(DirectoryUtil.class)) {
            mockDirectoryUtil
                    .when(() -> DirectoryUtil.getPdfFilesFromDirectory(MERGE_PATH))
                    .thenReturn(Collections.emptyList());

            PdfService merge = new Merge();
            merge.run();
            assertThat(systemOutContent.toString())
                    .contains("В папке нет файлов. Добавьте минимум 2 pdf файла и попробуйте снова.");
        }
    }

    @Test
    public void whenOneFileInFolder() {
        try (MockedStatic<DirectoryUtil> mockDirectoryUtil = mockStatic(DirectoryUtil.class)) {
            mockDirectoryUtil
                    .when(() -> DirectoryUtil.getPdfFilesFromDirectory(MERGE_PATH))
                    .thenReturn(List.of(new File("test.pdf")));

            PdfService merge = new Merge();
            merge.run();
            assertThat(systemOutContent.toString())
                    .contains("В папке один pdf файл. Добавьте еще как минимум один и попробуйте снова.");
        }
    }

    @Test
    public void whenElevenFilesInFolder() {
        try (MockedStatic<DirectoryUtil> mockDirectoryUtil = mockStatic(DirectoryUtil.class)) {
            mockDirectoryUtil
                    .when(() -> DirectoryUtil.getPdfFilesFromDirectory(MERGE_PATH))
                    .thenReturn(
                            List.of(
                                    new File("1.pdf"),
                                    new File("2.pdf"),
                                    new File("3.pdf"),
                                    new File("4.pdf"),
                                    new File("5.pdf"),
                                    new File("6.pdf"),
                                    new File("7.pdf"),
                                    new File("8.pdf"),
                                    new File("9.pdf"),
                                    new File("10.pdf"),
                                    new File("11.pdf")
                            ));

            PdfService merge = new Merge();
            merge.run();
            assertThat(systemOutContent.toString())
                    .contains("В папке больше 10 pdf файлов. Это слишком много. Удалите лишние и попробуйте снова.");
        }
    }
}