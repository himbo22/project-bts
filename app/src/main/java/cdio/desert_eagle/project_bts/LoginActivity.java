package cdio.desert_eagle.project_bts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cdio.desert_eagle.project_bts.viewmodel.LoginViewModel;

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
        LoginViewModel loginViewModel = new LoginViewModel(this.getApplication());

        //  Login
        login.setOnClickListener(v -> {
            String username = User.getText().toString().trim();
            String password = Pass.getText().toString().trim();
            if (username.isBlank() && password.isBlank()) {
                return;
            }
            loginViewModel.login(username, password);
        });

        //   Sign Up
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        // observe data
        loginViewModel.loginLiveData.observe(this, data -> {
            loginViewModel.saveUserInformation(data.getId());
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        loginViewModel.errorLiveData.observe(this, data -> {
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        });
    }

}

