package ru.ftptpf.util;

import java.nio.file.Path;

public final class PdfConst {

    public static final String MERGE_DIR = "merge";
    public static final String INSERT_DIR = "insert";
    public static final String EXTRACT_DIR = "extract";
    public static final String DELETE_DIR = "delete";

    public static final String MERGE_FILE_NAME = "merge-result-" + CurrentDateTime.get() + ".pdf";
    public static final Path MERGE_PATH = Path.of("src", "main", "resources", MERGE_DIR);
    public static final Path MERGE_PATH_OUTPUT = Path.of("src", "main", "resources", MERGE_DIR, MERGE_FILE_NAME);

    public static final String INSERT_FILE_NAME = "insert-result-" + CurrentDateTime.get() + ".pdf";
    public static final Path INSERT_PATH = Path.of("src", "main", "resources", INSERT_DIR);
    public static final Path INSERT_PATH_OUTPUT = Path.of("src", "main", "resources", INSERT_DIR, INSERT_FILE_NAME);

    public static final String EXTRACT_FILE_NAME = "extract-result-" + CurrentDateTime.get() + ".pdf";
    public static final Path EXTRACT_PATH = Path.of("src", "main", "resources", EXTRACT_DIR);
    public static final Path EXTRACT_PATH_OUTPUT = Path.of("src", "main", "resources", EXTRACT_DIR, EXTRACT_FILE_NAME);

    public static final String DELETE_FILE_NAME = "delete-result-" + CurrentDateTime.get() + ".pdf";
    public static final Path DELETE_PATH = Path.of("src", "main", "resources", DELETE_DIR);
    public static final Path DELETE_PATH_OUTPUT = Path.of("src", "main", "resources", DELETE_DIR, DELETE_FILE_NAME);

    private PdfConst() {
    }
}
