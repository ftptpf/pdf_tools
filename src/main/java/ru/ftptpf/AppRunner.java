package ru.ftptpf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ftptpf.service.*;
import ru.ftptpf.util.DirectoryUtil;

import java.util.Scanner;

public class AppRunner {

    private static final Logger LOGGER = LogManager.getLogger(AppRunner.class);

    public static void main(String[] args) {
        LOGGER.trace("***TRACE***");
        LOGGER.debug("***DEBUG***");
        LOGGER.info("***INFO***");
        LOGGER.warn("***WARN***");
        LOGGER.error("***ERROR***");

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
            System.out.println("Введите числовой номер меню: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Необходимо ввести числовой номер меню. Это цифры от 1 до 5. Попробуйте еще раз.");
                scanner.nextLine();
            } else {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        PdfService merge = new Merge();
                        merge.run();
                        break;
                    case 2:
                        System.out.println("Введите имя файла (включая расширение *.pdf, например 12345.pdf) в который будут вставляться страницы другого pdf файла.");
                        String mainFileName = scanner.next();
                        System.out.println("Введите имя файла (включая расширение *.pdf, например 45.pdf) из которого будут извлекаться страницы для вставки.");
                        String addedFileName = scanner.next();
                        System.out.println("Введите номер страницы основного pdf файла, после которой начнется вставка.");
                        int insertAfterThisPage = scanner.nextInt();
                        PdfService insert = new Insert(mainFileName, addedFileName, insertAfterThisPage);
                        insert.run();
                        break;
                    case 3:
                        System.out.println("Необходимо указать диапазон извлекаемых страниц.");
                        System.out.println("Введите номер первой страницы:");
                        int startPage = scanner.nextInt();
                        System.out.println("Введите номер последней страницы:");
                        int endPage = scanner.nextInt();
                        PdfService extract = new Extract(startPage, endPage);
                        extract.run();
                        break;
                    case 4:
                        System.out.println("Необходимо указать диапазон удаляемых страниц.");
                        System.out.println("Введите номер первой страницы:");
                        int firstPage = scanner.nextInt();
                        System.out.println("Введите номер последней страницы:");
                        int lastPage = scanner.nextInt();
                        PdfService delete = new Delete(firstPage, lastPage);
                        delete.run();
                        break;
                    case 5:
                        exit = true;
                        System.out.println("Работа с программой завершена.");
                        break;
                    default:
                        System.out.println("Неверно выбран номер меню. Это должны быть цифры от 1 до 5. Попробуйте еще раз.");
                }
            }
        }
    }
}
