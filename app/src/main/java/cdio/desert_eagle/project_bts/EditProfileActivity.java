package cdio.desert_eagle.project_bts;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editprofile);
        EditText namee = findViewById(R.id.name);
        EditText dobb = findViewById(R.id.dob);
        EditText bioo = findViewById(R.id.bio);
        Button save = findViewById(R.id.save_changes_button);

        save.setOnClickListener(v -> {
            String name = namee.getText().toString().trim();
            String dob = dobb.getText().toString().trim();
            String bio = bioo.getText().toString().trim();
            Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
        });

    }
}