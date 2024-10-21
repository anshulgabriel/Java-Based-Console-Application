package Validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationClass {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9._%#*$+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,3}$");
    private static final Pattern MOBILE_REGEX = Pattern.compile("^[0-9]{10}$");
    private static final Pattern USERNAME_REGEX = Pattern.compile("^(?=[A-Z])[A-Za-z0-9!@#$%^&*_]{3,20}$");

    public static boolean isValidEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }

    public static boolean isValidMobileNumber(String mobileNumber) {
        return MOBILE_REGEX.matcher(mobileNumber).matches();
    }

    public static boolean isValidUserName(String username) {
        return USERNAME_REGEX.matcher(username).matches();
    }
}
