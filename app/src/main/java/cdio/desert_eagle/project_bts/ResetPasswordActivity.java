package cdio.desert_eagle.project_bts;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cdio.desert_eagle.project_bts.databinding.ActivityResetPasswordBinding;
import cdio.desert_eagle.project_bts.viewmodel.ResetPasswordViewModel;

public class ResetPasswordActivity extends AppCompatActivity {

    private ActivityResetPasswordBinding binding;
    private ResetPasswordViewModel resetPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

    }
}