package ch.noseryoung.uek335;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import ch.noseryoung.uek335.model.PasswordAuthentication;
import ch.noseryoung.uek335.model.User;
import ch.noseryoung.uek335.persistence.AppDatabase;
import ch.noseryoung.uek335.persistence.UserDAO;

public class RegisterActivity extends AppCompatActivity {

    private UserDAO mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button saveUserButton = findViewById(R.id.button_register);
        saveUserButton.setOnClickListener(mSaveUserOnClickListener);

        mUserDao = AppDatabase.getAppDb(getApplicationContext()).getUserDao();
    }

    private View.OnClickListener mSaveUserOnClickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View openActivityButton) {
            PasswordAuthentication passwordAuthentication = new PasswordAuthentication();

            // Get Register contents
            EditText firstNameView = findViewById(R.id.field_text_vorname_register);
            EditText lastNameView = findViewById(R.id.field_text_nachname_register);
            EditText emailView = findViewById(R.id.field_email_register);
            EditText passwordView = findViewById(R.id.field_password_register);

            // Create new User
            User user = new User();
            user.setFirstName(firstNameView.getText().toString());
            user.setLastName(lastNameView.getText().toString());
            user.setEmail(emailView.getText().toString());
            user.setPassword(passwordAuthentication.hash(passwordView.getText().toString().toCharArray()));

            mUserDao.insertOne(user);
        }
    };
}
