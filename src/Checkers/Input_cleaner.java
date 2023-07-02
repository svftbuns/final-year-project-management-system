package Checkers;

import static java.lang.Math.min;

public class Input_cleaner {
    // clean up inputs to prevent program from crashing in the event of InputMismatch

    public static int int_only(String input) {
        String sanitised_input = input.replaceAll("[^0-9]", "0");
        return Integer.valueOf(sanitised_input);
    }

    public static int projectID_only(String input) {
        String max_length_input = input.substring(0, min(input.length(), 3));
        String sanitised_input = max_length_input.replaceAll("[^0-9]", "99");
        return Integer.valueOf(sanitised_input);
    }
    public static String str_only(String input) {
        String sanitised_input = input.replaceAll("[^A-Za-z_]","");
        return sanitised_input;
    }

    public static String alphanumeric(String input) {
        String sanitised_input = input.replaceAll("[^A-Za-z0-9']","");
        return sanitised_input;
    }

    static String yn(String input) {
        return String.valueOf(input.charAt(0));
    }
}
