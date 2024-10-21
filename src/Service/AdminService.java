package Service;

import Entity.User;
import Exceptions.UserNotFoundException;
import Repository.UserRepository;
import Validation.ValidationClass;

import java.util.*;

public class AdminService {
    private int count = 1;
    private Scanner sc;

    public AdminService() {
        sc = new Scanner(System.in);
    }

    public int adminOptions() {
        int adminOption = 0;
        boolean validInput = false;

        System.out.println();
        while (!validInput) {
            System.out.println("\n################################");
            System.out.println("     Select Valid Options");
            System.out.println("\n     1. Create User");
            System.out.println("     2. Delete User");
            System.out.println("     3. Fetch Single User");
            System.out.println("     4. Fetch All User");
            System.out.println("     5. Update User");
            System.out.print("     6. Exit");
            System.out.println("\n################################");
            System.out.print("\nEnter the option:- ");

            try {
                adminOption = (Integer.parseInt(sc.nextLine()));

                if (adminOption >= 1 && adminOption <= 6) {
                    switch (adminOption) {
                        case 1 -> createUser();
                        case 2 -> deleteUser();
                        case 3 -> printSingleUser();
                        case 4 -> fetchAllUsers();
                        case 5 -> updateUser();
                        case 6 -> {
                            System.out.println("\nThank you for using our application!");
                            sc.close();
                            System.exit(0);
                        }
                    }
                } else {
                    System.out.println("\nEnter a value between 1 and 6.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("\nKindly enter a valid integer option. Thank you!");
            }
        }
        return 0;
    }

    private void createUser() {

        System.out.println("\nEnter User Details");
        String name = getUserName();
        if (name.equals("3")) {
            return;
        }
        String email = getEmailAddress();
        if (email.equals("3")) {
            return;
        }
        List<String> mobileNo = getMobileNumbers();
        if (!(mobileNo.isEmpty()) && mobileNo.get(0).equals("3")) {
            return;
        }

        if (!fetchSingleUser(email).isPresent()) {
            boolean b = UserRepository.setUser(new User(count, name.toLowerCase(), email.toLowerCase(), mobileNo));
            if (b) {
                count++;
                System.out.println("\nWe have created the user successfully!");
            }
        } else {
            System.out.println("\nUser Already Exist, Please Use a Different Email Address!");
        }
    }

    private Optional<User> fetchSingleUser(String email) {

        return UserRepository.getUserSet().stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    private Optional<User> printSingleUser() {
        System.out.println("\nPress 3 to go back");
        System.out.print("\nEnter user email to fetch:- ");
        String email = sc.nextLine();
        if (email.equals("3")) {
            return Optional.empty();
        }
        Optional<User> user = fetchSingleUser(email);

        try {
            if (user.isPresent()) {
                System.out.println();
                System.out.println("Name:- " + user.get().getName() +
                        " : Email:- " + user.get().getEmail() + " : Mobile No:- " + user.get().getMobileList());
                return user;
            } else {
                throw new UserNotFoundException();
            }
        } catch (UserNotFoundException ex) {
            ex.printStackTrace();
            System.out.println();
        }
        return Optional.empty();
    }

    private void fetchAllUsers() {
        Optional<Set<User>> userSetOptional = Optional.ofNullable(UserRepository.getUserSet());
        userSetOptional.ifPresentOrElse(
                userSet -> {
                    System.out.println();
                    userSet.forEach(System.out::println);
                },
                () -> System.out.println("\nUser table is empty, no user found!")
        );
    }

    private void updateUser() {
        Optional<User> singleUser = printSingleUser();
        if (singleUser.isPresent()) {
            boolean validInput = false;
            while (!validInput) {
                System.out.println("\nPress 3 to go back");
                System.out.println("\n1. Name\n2. Mobile Number");
                System.out.print("\nEnter your option(Integer value):- ");
                try {
                    int input = (Integer.parseInt(sc.nextLine()));
                    String name = "";
                    List<String> mobileNo = null;

                    switch (input) {
                        case 1 -> {
                            name = getUserName();
                            if (name.equals("3")) {
                                return;
                            }
                        }
                        case 2 -> {
                            mobileNo = getMobileNumbers();
                            if (!(mobileNo.isEmpty()) && mobileNo.get(0).equals("3")) {
                                return;
                            }
                        }
                        case 3 -> {
                            return;
                        }
                        default -> {
                            System.out.println("\nPlease select either 1 or 2.");
                            continue;
                        }
                    }

                    Optional<User> optionalUser = UserRepository.getUserSet().stream()
                            .filter(u -> u.getId() == singleUser.get().getId())
                            .findFirst();

                    String finalName = name;
                    List<String> finalMobileNo = mobileNo;
                    optionalUser.ifPresent(user -> {
                        if (!finalName.isEmpty()) {
                            user.setName(finalName.toLowerCase());
                        } else if (finalMobileNo != null && !finalMobileNo.isEmpty()) {
                            user.setMobileList(finalMobileNo);
                        }
                    });

                    UserRepository.setUser(optionalUser.get());
                    System.out.println("\nUser Updated Successfully!");
                    validInput = true;

                } catch (InputMismatchException | NumberFormatException ex) {
                    System.out.println("\nKindly enter a valid integer option. Thank you!");
                }
            }
        }
    }

    private void deleteUser() {
        System.out.println("\nPress 3 to go back");
        System.out.print("\nEnter user email to delete:- ");
        String email = sc.nextLine();
        if (email.equals("3")) {
            return;
        }

        try {
            if (UserRepository.removeUser(fetchSingleUser(email).orElseThrow(UserNotFoundException::new))) {
                System.out.println("\nUser Deleted Successfully!");
            }
        } catch (UserNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public String getUserName() {
        String name;
        while (true) {
            System.out.println("\nPress 3 to go back\n");
            System.out.print("Username:- ");
            name = sc.nextLine();
            if (name.equals("3")) {
                return name;
            }
            if (ValidationClass.isValidUserName(name)) {
                return name;
            }
            System.out.println("\nInvalid Name, Please Try Again! First Letter Should be Capital. Max Length is 20 Characters.");
        }
    }

    public String getEmailAddress() {
        String email;
        while (true) {
            System.out.println("\nPress 3 to go back\n");
            System.out.print("Email:- ");
            email = sc.nextLine();
            if (email.equals("3")) {
                return email;
            }
            if (ValidationClass.isValidEmail(email)) {
                return email;
            }
            System.out.println("\nInvalid Email, Please Enter a Valid Email Address.");
        }
    }

    public List<String> getMobileNumbers() {
        List<String> mobileNumbersList = new ArrayList<>();
        System.out.println("\nPress 3 to go back\n");
        while (true) {
            System.out.print("Enter your mobile no:- ('type exit to stop'):- ");
            String input = sc.nextLine();
            if (input.equals("3")) {
                mobileNumbersList.add(0, "3");
                return mobileNumbersList;
            }
            if (input.equalsIgnoreCase("exit")) {
                return mobileNumbersList;
            }
            if (ValidationClass.isValidMobileNumber(input)) {
                mobileNumbersList.add(input);
            } else {
                System.out.println("\nKindly enter a valid number, mobile number should be of 10 digits only.");
            }
        }
    }
}
