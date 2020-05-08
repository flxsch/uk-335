package ch.noseryoung.uek335.model.validation;

import android.service.autofill.UserData;
import android.widget.EditText;

import ch.noseryoung.uek335.model.User;
import ch.noseryoung.uek335.persistence.AppDatabase;
import ch.noseryoung.uek335.persistence.UserDAO;

public class InputValidator {

    private final static String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    private final static String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*ç/()\\\\=?`|¦°§¬¢´~äöü\\]\\[\"{}!@#$%^&+=])(?=\\S+$).{8,}$";
    private final int MAX_FIRSTNAME_LEN = 50;
    private final int MAX_LASTNAME_LEN = 50;
    private final int MAX_EMAIL_LEN = 100;

    public Validation validateRegisterInput(UserDAO mUserDAO, EditText firstNameView, EditText lastNameView, EditText emailView, EditText passwordView) {
        String firstName = firstNameView.getText().toString();
        String lastName = lastNameView.getText().toString();
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        // Check for valid input
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return Validation.MISSING_CREDS;
        } else if (isLengthLongerThanMax(firstName, MAX_FIRSTNAME_LEN)) {
            return Validation.FIRSTNAME_TOO_LONG;
        } else if (isLengthLongerThanMax(lastName, MAX_LASTNAME_LEN)) {
            return Validation.LASTNAME_TOO_LONG;
        } else if (isLengthLongerThanMax(email, MAX_EMAIL_LEN)) {
            return Validation.EMAIL_TOO_LONG;
        } else if (isEmailInvalid(email)) {
            return Validation.INVALID_EMAIL;
        } else if (isPasswordSimple(password)) {
            return Validation.SIMPLE_PASSWORD;
        } else if (userExists(mUserDAO, email)){
            return Validation.USER_EXISTS;
        }
        return Validation.VALID;
    }

    public Validation validateLoginInput(EditText emailView, EditText passwordView) {
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        // Check for valid input
        if (email.isEmpty() || password.isEmpty()) {
            return Validation.MISSING_CREDS;
        } else if (isLengthLongerThanMax(email, MAX_EMAIL_LEN)) {
            return Validation.EMAIL_TOO_LONG;
        } else if (isEmailInvalid(email)) {
            return Validation.INVALID_EMAIL;
        }
        return Validation.VALID;
    }

    private boolean isLengthLongerThanMax(String str, int len) {
        return str.length() > len;
    }

    private boolean isEmailInvalid(String email) {
        return !email.matches(EMAIL_REGEX);
    }

    private boolean isPasswordSimple(String password) {
        return !password.matches(PASSWORD_REGEX);
    }

    private boolean userExists(UserDAO mUserDao, String email){
        // Check if User with this email already exists
        return mUserDao.getOne(email) != null;
    }
}
