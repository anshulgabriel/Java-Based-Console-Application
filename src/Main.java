import Service.AdminService;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_PASSWORD = "password";


    public static void main(String[] args) {

        System.out.println("Welcome to Java Console Application");
        System.out.println("1. Admin");
        System.out.println("2. User");
        System.out.println("3. Exit\n");

        int userOption = 0;

        while(!(userOption >=1 && userOption <=3))
        {
            System.out.print("Enter the option:- ");

           try {
               userOption = (Integer.parseInt(sc.nextLine()));
               if(userOption == 1)
               {
                   boolean value = adminLogin();
                   if(value) {
                       AdminService service = new AdminService();
                       service.adminOptions();
                   }

               } else if(userOption == 2) {

                   System.out.println("\nWe're working on this particular module. Please come back later.\n");
                   userOption = 0;

               } else if(userOption == 3){
                   System.out.println("\nThank you for using our application!");
                   sc.close();
                   System.exit(0);
               } else {
                   System.out.println("\nThere is no such option, please try again!");
               }
           } catch (InputMismatchException | NumberFormatException ex) {
               System.out.println("\nKindly enter a valid integer option. Thank you!");
           }
        }
        sc.close();
    }

    public static boolean adminLogin() {
        boolean input = false;

       while(!input)
       {
           System.out.println("\nEnter the name and password:- ");
           System.out.print("Enter name:- ");
           String adminName = sc.nextLine();
           System.out.print("Enter password:- ");
           String adminPassword = sc.nextLine();
           if(adminName.length() > 0 && adminPassword.length() > 0)
           {
              if(adminName.equalsIgnoreCase(ADMIN_ID) && adminPassword.equals(ADMIN_PASSWORD))
              {
                    input = true;
                    return input;
              } else {
                  System.out.println("\nWrong admin id or password, please try again!");
              }
           } else {
               System.out.println("\nInvalid username or password, please try again");
           }
       }
        return input;
    }
}