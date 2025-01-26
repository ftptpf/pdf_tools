package ru.ftptpf;

import ru.ftptpf.service.PdfMerger;

public class AppRunner {

    public static void main(String[] args) {

        if (args.length < 3) {
            throw new IllegalArgumentException("Неверное количество переданных аргументов. "
                    + "Должен быть указан 1 итоговый фай и минимум 2 которые будут объединены.");
        }

        PdfMerger merger = new PdfMerger();
        merger.run(args);

    }
}
