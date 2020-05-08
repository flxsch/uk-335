package ch.noseryoung.uek335;

import androidx.appcompat.app.AppCompatActivity;
import ch.noseryoung.uek335.model.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        user = (User) getIntent().getSerializableExtra("User");
        TextView nameDisplayView = findViewById(R.id.name_display);
        nameDisplayView.setText(user.getDisplayName());

        final Button logout_button = findViewById(R.id.button_logout);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Log.d(TAG, "openMainActivity: open the main activity");
    }
}
