package ru.ftptpf;

import ru.ftptpf.service.*;
import ru.ftptpf.util.DirectoryUtil;

import java.util.Scanner;

public class AppRunner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        DirectoryUtil.createAllDirectories();

        while (!exit) {
            System.out.println();
            System.out.println("*** Меню ***");
            System.out.println("1 - Объединить до 10 pdf файлов в один");
            System.out.println("2 - Вставить в pdf файл страницы другого pdf файла");
            System.out.println("3 - Извлечь диапазон страниц и сохранить его в новый pdf файл");
            System.out.println("4 - Удалить страницы из pdf файла");
            System.out.println("5 - Выйти из программы");
            System.out.println("Введите номер меню: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    PdfService merge = new Merge();
                    merge.run();
                    break;
                case 2:
                    System.out.println("Введите сначала имя основного файла, а затем имя файла с добавляемыми страницами.");
                    String mainFileName = scanner.next();
                    String addedFileName = scanner.next();
                    System.out.println("Введите номер страницы, после которой начнется вставка.");
                    int insertAfterThisPage = scanner.nextInt();
                    PdfService insert = new Insert(mainFileName, addedFileName, insertAfterThisPage);
                    insert.run();
                    break;
                case 3:
                    System.out.println("Введите диапазон извлекаемых страниц. Сначала первую потом последнюю.");
                    int startPage = scanner.nextInt();
                    int endPage = scanner.nextInt();
                    PdfService extract = new Extract(startPage, endPage);
                    extract.run();
                    break;
                case 4:
                    System.out.println("Введите диапазон удаляемых страниц. Сначала первую потом последнюю.");
                    int firstPage = scanner.nextInt();
                    int lastPage = scanner.nextInt();
                    PdfService delete = new Delete(firstPage, lastPage);
                    delete.run();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Работа с программой завершена.");
                    break;
                default:
                    System.out.println("Неверно выбран номер меню");
            }

        }
    }
}
