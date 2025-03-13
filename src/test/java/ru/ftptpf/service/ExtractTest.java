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
import static ru.ftptpf.util.PdfConst.EXTRACT_PATH;


class ExtractTest {

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
    public void whenMoreThenOneFilesInFolder() {
        try (MockedStatic<DirectoryUtil> mockDirectoryUtil = mockStatic(DirectoryUtil.class)) {
            mockDirectoryUtil
                    .when(() -> DirectoryUtil.getPdfFilesFromDirectory(EXTRACT_PATH))
                    .thenReturn(List.of(
                            new File("1.pdf"),
                            new File("2.pdf")
                    ));

            PdfService extract = new Extract(1, 2);
            extract.run();
            assertThat(systemOutContent.toString())
                    .contains("В папке несколько файлов. Удалите лишние файлы и попробуйте снова.");
        }
    }

    @Test
    public void whenNoFilesInFolder() {
        try (MockedStatic<DirectoryUtil> mockDirectoryUtil = mockStatic(DirectoryUtil.class)) {
            mockDirectoryUtil
                    .when(() -> DirectoryUtil.getPdfFilesFromDirectory(EXTRACT_PATH))
                    .thenReturn(Collections.emptyList());

            PdfService extract = new Extract(1, 2);
            extract.run();
            assertThat(systemOutContent.toString())
                    .contains("В папке нет файлов. Разместите в папке файл и попробуйте еще раз.");
        }
    }
}