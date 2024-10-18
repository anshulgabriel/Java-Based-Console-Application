package Exceptions;

//Created a custom exception class
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found, try again!");
    }
}
