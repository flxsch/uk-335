package ch.noseryoung.uek335;

import androidx.appcompat.app.AppCompatActivity;
import ch.noseryoung.uek335.model.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Log.d(TAG, "openMainActivity: open the main activity");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.menu_item_settings:

                return true;
            case R.id.menu_item_logout:
                openMainActivity();
                return true;
            default:
                return false;
        }
    }
}
