package org.kpi.mmsa.menu;

import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    public void  showConsole() {
        char choice = '-';

        while (choice != '0'){
            clearScreen();
            System.out.println("\n\tMENU for Gaussian Elimination");
            System.out.println("\n\t------------------------------");
            System.out.println("\n\n\t 1. Solve Linear equations. Input matrices from console.");
            System.out.println("\n\t 2. Solve Linear equations. Input matrices from matrix.csv file.");
            System.out.println("\n\t 3. Solve Linear equations. Input matrices from your file.");
            System.out.println("\n\t 0. EXIT");
            System.out.println("\n\n\t Enter Your Choice: ");
            choice = scanner.next().charAt(0);

            switch (choice) {
                case '1':
                    System.out.println("\n\n\tYour choice is to input matrices from console\n");
//                    solver(initMatrixFromConsole(matrix));
                    break;
                case '2':
                    System.out.println("\n\n\tYour choice is to input matrices from matrix.csv file\n");
//                    solver(initMatrixFromFile(matrix, "matrix.csv"));
                    break;
                case '3':
                    System.out.println("\n\n\tYour choice is to input matrices from your file\n");
                    System.out.println("\n\n\tInput the file name, please: ");
//                    solver(initMatrixFromFile(matrix, input_string()));
                    break;
                case '0':
                    System.out.println("\n\n\tYour choice is to exit\n");
                    break;
                default:
                    System.out.println("\n\n\tERROR...Invalid selection...Please try again\n");
            }

        }



    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
