package ch.noseryoung.uek335;

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

        final Button left_button = findViewById(R.id.button_left);
        left_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(left_button.getText().equals(getString(R.string.text_button_register))){
                    System.out.println(getString(R.string.text_button_register));
                    left_button.setText(getString(R.string.text_button_login));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                RegisterFragment registerFragment = new RegisterFragment();
                fragmentTransaction.replace(R.id.fragmentcontainer, registerFragment);
                fragmentTransaction.commit();
            } else if (left_button.getText().equals(getString(R.string.text_button_login))){
                    System.out.println(getString(R.string.text_button_login));
                    left_button.setText(getString(R.string.text_button_register));
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    LoginFragment loginFragment = new LoginFragment();
                    fragmentTransaction.replace(R.id.fragmentcontainer, loginFragment);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }
}
