package ru.ftptpf;

import ru.ftptpf.service.Delete;
import ru.ftptpf.service.Merge;
import ru.ftptpf.service.Extract;
import ru.ftptpf.service.PdfService;

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
                    System.out.println("Вставить в pdf файл страницы");
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
