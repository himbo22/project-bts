package cdio.desert_eagle.project_bts.utils;

import java.util.regex.Pattern;

public class EmailUtil {
    public static boolean patternMatches(String emailAddress) {
        return Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
                .matcher(emailAddress)
                .matches();
    }
}
