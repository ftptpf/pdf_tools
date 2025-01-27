package ru.ftptpf;

import ru.ftptpf.service.PdfMerger;

import java.util.Scanner;

public class AppRunner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {
            System.out.println("Меню");
            System.out.println("1 - Объединить файлы");
            System.out.println("2 - Выход");
            System.out.print("Введите номер меню: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Объединение файлов");
                    break;
                case 2:
                    exit = true;
                    break;
                default:
                    System.out.println("Неверный выбор");
            }

        }

/*



        PdfMerger merger = new PdfMerger();
        merger.run(args);
*/

    }
}
