package ru.ftptpf.service;

public class PdfPageExtractor {

    private String fileName;
    private int startPage;
    private int endPage;

    public void extract(String fileName, int startPage, int endPage) {
        this.fileName = fileName;
        this.startPage = startPage;
        this.endPage = endPage;
    }

    public void extract() {

    }
}
