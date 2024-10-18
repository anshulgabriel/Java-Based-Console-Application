package Validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Validation class created for validation
public class ValidationClass {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%#*$+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$";

    private static final String MOBILE_REGEX = "^[0-9]{10}$";

    private static final String USERNAME_REGEX = "^(?=[A-Z])[A-Za-z0-9!@#$%^&*_]{3,20}$";

    // Method to validate email
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to validate mobile number
    public static boolean isValidMobileNumber(String mobileNumber) {
        Pattern pattern = Pattern.compile(MOBILE_REGEX);
        Matcher matcher = pattern.matcher(mobileNumber);
        return matcher.matches();
    }

    // Method to validate user name
    public static boolean isValidUserName(String username) {
        Pattern pattern = Pattern.compile(USERNAME_REGEX);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static void main(String[] args) {
        String no = "2344242422";
        boolean validMobileNumber = isValidMobileNumber(no);
        System.out.println(validMobileNumber);
    }

}
