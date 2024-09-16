package cdio.desert_eagle.project_bts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import cdio.desert_eagle.project_bts.viewmodel.SplashScreenViewModel;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SplashScreenViewModel splashScreenViewModel = new SplashScreenViewModel(this.getApplication());

        new Handler().postDelayed(() -> {
            Intent intent;
            if (Objects.equals(splashScreenViewModel.getLoggedInformation(), "yes")) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, 1700);
    }
}