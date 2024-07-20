package ssafy.closetoyou.global.validator.email;

import ssafy.closetoyou.global.exception.email.InvalidEmailFormException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public static void isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if(!matcher.matches()) {
            throw InvalidEmailFormException.EXCEPTION;
        }
    }

}