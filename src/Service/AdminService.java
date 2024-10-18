package Service;

import Entity.User;
import Exceptions.UserNotFoundException;
import Repository.UserRepository;
import Validation.ValidationClass;

import java.util.*;


public class AdminService {
    private int count = 1;
    private Scanner sc;

    //To initialize the scanner object
    public AdminService() {
        sc = new Scanner(System.in);
    }

    //Printing all the admin options
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
                        case 1:
                            createUser();
                            break;
                        case 2:
                            deleteUser();
                            break;
                        case 3:
                            printSingleUser();
                            break;
                        case 4:
                            fetchAllUsers();
                            break;
                        case 5:
                            updateUser();
                            break;
                        case 6:
                            System.out.println("\nThank you for using our application!");
                            sc.close();
                            System.exit(0);
                    }

                } else {
                    System.out.println("\nEnter a value between 1 and 6.");
                }
            } catch (InputMismatchException | NumberFormatException ex) {
                System.out.println("\nKindly enter a valid integer option. Thank you!");
            }
        }

        return 0;
    }

    //Creating a new user, will throw exception if user already exists
    private void createUser() {

        System.out.println("\nEnter User Details");
        String name = "";
        boolean validInput = false;
        while (!validInput) {
            System.out.println("\nPress 3 to go back\n");
            System.out.print("Username:- ");
            name = sc.nextLine();
            if(name.equals("3")) {return;}
            if (!ValidationClass.isValidUserName(name)) {
                System.out.println("\nInvalid User Name, First Letter Should be Capital, Name Should Not Start With Numbers or Special Characters");
                System.out.println("Name should contain atleast 1 special character or a digit.\n");
            } else {
                validInput = true;
            }
        }
        String email = "";

        boolean validEmail = false;
        while (!validEmail) {
            System.out.println("\nPress 3 to go back\n");
            System.out.print("Email:- ");
            email = sc.nextLine();
            if(email.equals("3")) {return;}
            validEmail = ValidationClass.isValidEmail(email);
            if (!validEmail) {
                System.out.println("\n| Only 1 Special Character is Allowed | Include atleast 1 special character\n");
            }

        }

        List<String> mobileNo = new ArrayList<>();
        System.out.println("\nPress 3 to go back\n");
        while (true) {
            String input = "";
            System.out.print("Enter your mobile no:- ('type exit to stop'):- ");
            input = sc.nextLine();
            if(input.equals("3")) {return;}

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            if (ValidationClass.isValidMobileNumber(input)) {
//                    Integer.parseInt(input);
                mobileNo.add(input);
            } else {
                System.out.println("\nKindly enter a valid number, mobile number should be of 10 digits only.");
            }
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

        Set<User> userSet = UserRepository.getUserSet();
        Optional<User> user = userSet.stream().filter(i -> i.getEmail().equalsIgnoreCase(email)).findFirst();
        return user;
    }

    //Fetching and printing a single user from the database, will throw exception if user not found
    private Optional<User> printSingleUser() {
        System.out.println("\nPress 3 to go back");
        System.out.print("\nEnter user email to fetch:- ");
        String email = sc.nextLine();
        if(email.equals("3")) {return Optional.empty();}
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
        return user;
    }

    //Fetching and printing all the users from the database
    private void fetchAllUsers() {
        Set<User> userSet = UserRepository.getUserSet();
        if (!userSet.isEmpty()) {
            System.out.println();
            for (User u : userSet) {

                System.out.println(u);
            }
        } else {
            System.out.println("\nUser table is empty, no user found!");
        }
    }

    //Updating user details except their email id
    private void updateUser() {
        Optional<User> singleUser = printSingleUser();
        if (singleUser.isPresent()) {
            boolean validInput = false;
            while (!validInput) {
                String name = "";
                List<String> mobileNo = null;
                System.out.println("\nPress 3 to go back");
                System.out.println("\n1. Name\n2. Mobile Number");
                System.out.print("\nEnter your option(Integer value):- ");
                try {
                    int input = (Integer.parseInt(sc.nextLine()));
                    if(input == 3) {return;}

                    if (input == 1) {
                        while (!ValidationClass.isValidUserName(name)) {
                            System.out.print("New Username:- ");
                            name = sc.nextLine();
                            if (!ValidationClass.isValidUserName(name)) {
                                System.out.println("\nInvalid User Name, First Letter Should be Capital, Name Should Not Start With Numbers or Special Characters");
                                System.out.println("Name should contain atleast 1 special character or a digit.\n");
                            }
                        }
                    } else if (input == 2) {
                        mobileNo = new ArrayList<>();
                        while (true) {
                            String mobileInput = "";
                            System.out.print("Enter your new mobile no:- ('type exit to stop'):- ");
                            mobileInput = sc.nextLine();

                            if (mobileInput.equalsIgnoreCase("exit")) {
                                break;
                            }

                            if (ValidationClass.isValidMobileNumber(mobileInput)) {
                                mobileNo.add(mobileInput);
                            } else {
                                System.out.println("Kindly enter a valid number, mobile number should be of 10 digits only.");
                            }
                        }
                    } else {
                        System.out.println("\nPlease select either 1 or 2.");
                        continue;
                    }

                    for (User user : UserRepository.getUserSet()) {
                        if (user.getId() == singleUser.get().getId()) {
                            if (!name.equals("")) {
                                user.setName(name.toLowerCase());
                            } else if (mobileNo != null) {
                                if (!mobileNo.isEmpty())
                                    user.setMobileList(mobileNo);
                            }

                            UserRepository.setUser(user);
                            System.out.println("\nUser Updated Successfully!");
                            validInput = true;
                        }
                    }

                } catch (InputMismatchException | NumberFormatException ex) {
                    System.out.println("\nKindly enter a valid integer option. Thank you!");
                }
            }

        }

    }

    //Deleting user if found in the database
    private void deleteUser() {
        System.out.println("\nPress 3 to go back");
        System.out.print("\nEnter user email to delete:- ");
        String email = sc.nextLine();
        if(email.equals("3")) {return;}
        Optional<User> user = fetchSingleUser(email);

        try {
            if (user.isPresent()) {
                boolean input = UserRepository.removeUser(user.get());
                if (input) {
                    System.out.println("\nUser Deleted Successfully!");
                }
            } else {
                throw new UserNotFoundException();
            }
        } catch (UserNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
