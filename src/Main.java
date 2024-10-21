import Service.AdminService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_PASSWORD = "password";

    public static void main(String[] args) {

        System.out.println("Welcome to Java Console Application\n1. Admin\n2. Exit\n");
        int userOption = 0;

        while (!(userOption >= 1 && userOption <= 2)) {
            System.out.print("Enter the option:- ");

            try {
                userOption = (Integer.parseInt(sc.nextLine()));
                switch (userOption) {
                    case 1 -> {
                        boolean value = adminLogin();
                        if (value) {
                            AdminService service = new AdminService();
                            service.adminOptions();
                        }
                    }
                    case 2 -> {
                        System.out.println("\nThank you for using our application!");
                        sc.close();
                        System.exit(0);
                    }
                    default -> System.out.println("\nThere is no such option, please try again!");
                }
            } catch (InputMismatchException | NumberFormatException ex) {
                System.out.println("\nKindly enter a valid integer option. Thank you!");
            }
        }
        sc.close();
    }

    public static boolean adminLogin() {
        boolean input = false;

        while (!input) {
            System.out.println("\nEnter the name and password:- ");
            System.out.print("Enter name:- ");
            String adminName = sc.nextLine();
            System.out.print("Enter password:- ");
            String adminPassword = sc.nextLine();

            if (adminName.equalsIgnoreCase(ADMIN_ID) && adminPassword.equals(ADMIN_PASSWORD)) {
                return input = true;
            } else {
                System.out.println("\nWrong admin id or password, please try again!");
            }
        }
        return input;
    }
}