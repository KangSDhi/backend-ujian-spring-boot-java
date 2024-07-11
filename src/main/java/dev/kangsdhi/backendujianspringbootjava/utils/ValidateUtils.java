package dev.kangsdhi.backendujianspringbootjava.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {

    public boolean validateEmailFormat(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
