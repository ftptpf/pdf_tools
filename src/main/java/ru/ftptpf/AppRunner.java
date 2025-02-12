package ru.ftptpf;

import ru.ftptpf.service.PdfMerger;

import java.util.Scanner;

public class AppRunner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {
            System.out.println();
            System.out.println("*** Меню ***");
            System.out.println("1 - Объединить pdf файлы в один");
            System.out.println("2 - Вставить в pdf файл страницы");
            System.out.println("3 - Извлечь страницы и сохранить в новый pdf файл");
            System.out.println("4 - Удалить страницы из pdf файла");
            System.out.println("5 - Выйти из программы");
            System.out.println("Введите номер меню: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    PdfMerger pdfMerger = new PdfMerger();
                    pdfMerger.merge();
                    break;
                case 2:
                    System.out.println("Вставить в pdf файл страницы");
                    break;
                case 3:
                    System.out.println("Извлечь страницы из pdf файла");
                    break;
                case 4:
                    System.out.println("Удалить страницы из pdf файла");
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Неверно выбран номер меню");
            }

        }
    }
}
