package cdio.desert_eagle.project_bts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import cdio.desert_eagle.project_bts.model.request.LoginRequest;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        EditText emailSign = findViewById(R.id.email);
        EditText name = findViewById(R.id.username);
        EditText pass = findViewById(R.id.password);
        Button Sign = findViewById(R.id.button3);
        TextView lo   = findViewById(R.id.login);

        //  Sign up
        Sign.setOnClickListener(v -> {
            String email =emailSign .getText().toString().trim();
            String username = name.getText().toString().trim();
            String password = pass.getText().toString().trim();
            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {


                Intent intent = new Intent(SignupActivity.this, EditProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        lo.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, EditProfileActivity.class);
            startActivity(intent);
            finish();
        });
    }
}