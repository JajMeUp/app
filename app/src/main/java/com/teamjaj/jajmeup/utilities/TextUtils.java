package com.teamjaj.jajmeup.utilities;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

    public static boolean isValidEmail(String email) {
        String emailPattern = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
