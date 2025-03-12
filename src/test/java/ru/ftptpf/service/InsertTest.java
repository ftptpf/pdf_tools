package ru.ftptpf.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import ru.ftptpf.util.DirectoryUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static ru.ftptpf.util.PdfConst.INSERT_PATH;

class InsertTest {

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
    public void whenFilesNamesNotCorrect() {
        try (MockedStatic<DirectoryUtil> mockDirectoryUtil = mockStatic(DirectoryUtil.class)) {
            mockDirectoryUtil
                    .when(() -> DirectoryUtil.getPdfFilesFromDirectory(INSERT_PATH))
                    .thenReturn(List.of(
                            new File("1.pdf"),
                            new File("2.pdf")
                    ));

            PdfService insert = new Insert("YYY.pdf", "DDD.pdf", 100);
            insert.run();
            assertThat(systemOutContent.toString())
                    .contains("Файлы в папке не найдены! Проверьте наименования файлов и попробуйте ещё раз!");
        }
    }
}