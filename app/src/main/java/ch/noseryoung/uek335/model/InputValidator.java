package ch.noseryoung.uek335.model;

import android.widget.EditText;

public class InputValidator {


    private final static String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    private final static String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    private final int MAX_FIRSTNAME_LEN = 50;
    private final int MAX_LASTNAME_LEN = 50;
    private final int MAX_EMAIL_LEN = 100;

    public Validation validateRegisterInput(EditText firstNameView, EditText lastNameView, EditText emailView, EditText passwordView) {
        String firstName = firstNameView.getText().toString();
        String lastName = lastNameView.getText().toString();
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        // Check for valid input
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return Validation.MISSING_CREDS;
        } else if (isLengthShorterThanMax(firstName, MAX_FIRSTNAME_LEN)) {
            return Validation.FIRSTNAME_TOO_LONG;
        } else if (isLengthShorterThanMax(lastName, MAX_LASTNAME_LEN)) {
            return Validation.LASTNAME_TOO_LONG;
        } else if (isLengthShorterThanMax(email, MAX_EMAIL_LEN)) {
            return Validation.EMAIL_TOO_LONG;
        } else if (isEmailValid(email)) {
            return Validation.INVALID_EMAIL;
        } else if (isPasswordSimple(password)) {
            return Validation.SIMPLE_PASSWORD;
        }
        return Validation.VALID;
    }

    public Validation validateLoginInput(EditText emailView, EditText passwordView) {
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        // Check for valid input
        if (email.isEmpty() || password.isEmpty()) {
            return Validation.MISSING_CREDS;
        } else if (isLengthShorterThanMax(email, MAX_EMAIL_LEN)) {
            return Validation.EMAIL_TOO_LONG;
        } else if (isEmailValid(email)) {
            return Validation.INVALID_EMAIL;
        } else if (isPasswordSimple(password)) {
            return Validation.SIMPLE_PASSWORD;
        }
        return Validation.VALID;
    }

    private boolean isLengthShorterThanMax(String str, int len) {
        return str.length() <= len;
    }

    private boolean isEmailValid(String email) {
        return email.matches(EMAIL_REGEX);
    }

    private boolean isPasswordSimple(String password) {
        return !password.matches(PASSWORD_REGEX);
    }
}
