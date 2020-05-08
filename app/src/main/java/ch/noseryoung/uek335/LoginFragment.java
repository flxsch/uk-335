package ch.noseryoung.uek335;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import ch.noseryoung.uek335.model.validation.InputValidator;
import ch.noseryoung.uek335.model.PasswordAuthentication;
import ch.noseryoung.uek335.model.User;
import ch.noseryoung.uek335.model.validation.Validation;
import ch.noseryoung.uek335.persistence.AppDatabase;
import ch.noseryoung.uek335.persistence.UserDAO;

public class LoginFragment extends Fragment {
    private UserDAO mUserDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Button saveUserButton = getActivity().findViewById(R.id.button_submit_login);
        saveUserButton.setOnClickListener(mLoginUserOnClickListener);

        mUserDao = AppDatabase.getAppDb(getActivity().getApplicationContext()).getUserDao();
    }

    private View.OnClickListener mLoginUserOnClickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View openActivityButton) {
            InputValidator inputValidator = new InputValidator();
            Context context = getActivity().getApplicationContext();

            // Get Login Form contents
            EditText emailView = getView().findViewById(R.id.text_field_email_login);
            EditText passwordView = getView().findViewById(R.id.text_field_password_login);

            Validation validationLevel = inputValidator.validateLoginInput(emailView, passwordView);

            // Display message depending on validation level
            switch (validationLevel) {
                case MISSING_CREDS:
                    Toast.makeText(context, R.string.missing_creds, Toast.LENGTH_SHORT).show();
                    break;
                case EMAIL_TOO_LONG:
                    Toast.makeText(context, R.string.email_too_long, Toast.LENGTH_SHORT).show();
                    break;
                case INVALID_EMAIL:
                    Toast.makeText(context, R.string.invalid_email, Toast.LENGTH_SHORT).show();
                    break;
                case VALID:
                    login(emailView, passwordView);
                    break;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void login(EditText emailView, EditText passwordView) {
        Activity currActivity = getActivity();
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
        String password = passwordView.getText().toString();

        // Get User from Database
        User user = mUserDao.getOne(emailView.getText().toString());

        // Authenticate
        if (user != null && passwordAuthentication.authenticate(password.toCharArray(), user.getPassword())) {
            Intent intent = new Intent(currActivity, DashboardActivity.class);
            intent.putExtra("User", user);
            startActivity(intent);
        } else {
            Toast.makeText(currActivity.getApplicationContext(), R.string.wrong_creds, Toast.LENGTH_SHORT).show();
        }
    }
}
