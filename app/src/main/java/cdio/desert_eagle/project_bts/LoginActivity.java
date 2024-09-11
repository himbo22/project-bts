package cdio.desert_eagle.project_bts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText User = findViewById(R.id.editTextText);
        EditText Pass = findViewById(R.id.editTextTextPassword);
        Button login = findViewById(R.id.button);
        TextView forgot = findViewById(R.id.forgotPasswordTextView);
        TextView sign = findViewById(R.id.sign_up);

        //  Login
        login.setOnClickListener(v -> {
            String username = User.getText().toString().trim();
            String password = Pass.getText().toString().trim();
            if (isLoginValid(username, password)) {
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {

                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        //   Sign Up
        sign.setOnClickListener(v ->

        {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }



    private boolean isLoginValid(String username, String password) {
        return "user".equals(username) && "password".equals(password);
    }
}

