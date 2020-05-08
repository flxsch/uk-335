package ch.noseryoung.uek335;

import android.content.Context;
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


public class RegisterFragment extends Fragment {

    private UserDAO mUserDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button saveUserButton = getActivity().findViewById(R.id.button_submit_register);
        saveUserButton.setOnClickListener(mSaveUserOnClickListener);

        mUserDao = AppDatabase.getAppDb(getActivity().getApplicationContext()).getUserDao();
    }

    private View.OnClickListener mSaveUserOnClickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View openActivityButton) {
            Context context = getActivity().getApplicationContext();
            InputValidator inputValidator = new InputValidator();

            // Get Register Form contents
            EditText firstNameView = getView().findViewById(R.id.text_field_firstname_register);
            EditText lastNameView = getView().findViewById(R.id.text_field_lastname_register);
            EditText emailView = getView().findViewById(R.id.text_field_email_register);
            EditText passwordView = getView().findViewById(R.id.text_field_password_register);

            Validation validationLevel = inputValidator.validateRegisterInput(mUserDao, firstNameView, lastNameView, emailView, passwordView);

            // Display message depending on validation level
            switch (validationLevel) {
                case MISSING_CREDS:
                    Toast.makeText(context, R.string.missing_creds, Toast.LENGTH_SHORT).show();
                    break;
                case FIRSTNAME_TOO_LONG:
                    Toast.makeText(context, R.string.firstname_too_long, Toast.LENGTH_SHORT).show();
                    break;
                case LASTNAME_TOO_LONG:
                    Toast.makeText(context, R.string.lastname_too_long, Toast.LENGTH_SHORT).show();
                    break;
                case EMAIL_TOO_LONG:
                    Toast.makeText(context, R.string.email_too_long, Toast.LENGTH_SHORT).show();
                    break;
                case INVALID_EMAIL:
                    Toast.makeText(context, R.string.invalid_email, Toast.LENGTH_SHORT).show();
                    break;
                case USER_EXISTS:
                    Toast.makeText(context, R.string.user_exists, Toast.LENGTH_SHORT).show();
                    break;
                case SIMPLE_PASSWORD:
                    Toast.makeText(context, R.string.password_too_simple, Toast.LENGTH_LONG).show();
                    break;
                case VALID:
                    registerUser(firstNameView, lastNameView, emailView, passwordView);
                    Toast.makeText(context, R.string.user_registered, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void registerUser(EditText firstNameView, EditText lastNameView, EditText emailView, EditText passwordView) {
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication(10);

        // Create new User
        User user = new User();
        user.setFirstName(firstNameView.getText().toString());
        user.setLastName(lastNameView.getText().toString());
        user.setEmail(emailView.getText().toString());
        user.setPassword(passwordAuthentication.hash(passwordView.getText().toString().toCharArray()));

        mUserDao.insertOne(user);
    }
}
