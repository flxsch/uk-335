package ch.noseryoung.uek335;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button change_button = findViewById(R.id.button_change);
        final Button login_submit_button = findViewById(R.id.button_submit_login);
        final Button register_submit_button = findViewById(R.id.button_submit_register);
        login_submit_button.setVisibility(View.VISIBLE);
        register_submit_button.setVisibility(View.GONE);
        register_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterSubmit(login_submit_button, register_submit_button);
            }
        });
        login_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDashboardActivity();
            }
        });
        change_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchFragments(change_button, login_submit_button, register_submit_button);
            }
        });
    }

    public void openDashboardActivity() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        Log.d(TAG, "openDashboardActivity: open the dashboard activity");
    }

    public void onRegisterSubmit(Button login_submit_button, Button register_submit_button) {
        openLoginFragment(login_submit_button, register_submit_button);
    }

    public void switchFragments(Button change_button, Button login_submit_button, Button register_submit_button) {
        String register_text = getString(R.string.text_button_register);
        String login_text = getString(R.string.text_button_login);
        if (change_button.getText().equals(register_text)) {
            openRegisterFragment(login_submit_button, register_submit_button);
            change_button.setText(login_text);

        } else if (change_button.getText().equals(login_text)) {
            openLoginFragment(login_submit_button, register_submit_button);
            change_button.setText(register_text);

        }
    }

    public void openRegisterFragment(Button login_submit_button, Button register_submit_button) {
        login_submit_button.setVisibility(View.GONE);
        register_submit_button.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RegisterFragment registerFragment = new RegisterFragment();
        fragmentTransaction.replace(R.id.fragmentcontainer, registerFragment);
        fragmentTransaction.commit();
    }

    public void openLoginFragment(Button login_submit_button, Button register_submit_button) {
        login_submit_button.setVisibility(View.VISIBLE);
        register_submit_button.setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction.replace(R.id.fragmentcontainer, loginFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }
}
