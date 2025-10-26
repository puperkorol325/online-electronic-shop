package com.example.practice;

public class Validator {

    public static boolean PasswordValidator(String password) {
        if (password == null || password.length() != 8) {
            return false;
        }

        String regexp = "^[0-9\\p{Punct}]+$";

        return password.matches(regexp);
    }

    public static boolean EmailValidator(String email) {
        if (email == null || email.length() < 5) {
            return false;
        }

        if (!email.contains("@")) {
            return false;
        }

        String preAtString = email.split("@")[0];
        String afterAtString = email.split("@")[1];

        if (preAtString.length() < 2 || preAtString == null) {
            return false;
        }

        if (afterAtString.length() < 3 || afterAtString == null || !afterAtString.contains(".")) {
            return false;
        }

        return true;
    }

    public static boolean PhoneNumberValidator(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 10) {
            return false;
        }

        String regexp = "^(\\+7|7|8)?\\d{10}$";

        return phoneNumber.matches(regexp);
    }

    public static boolean NameValidator(String name) {
        if (name == null || name.length() < 3) {
            return false;
        }

        return true;
    }
}
