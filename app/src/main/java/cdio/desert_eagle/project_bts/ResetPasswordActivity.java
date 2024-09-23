package cdio.desert_eagle.project_bts;

import static cdio.desert_eagle.project_bts.utils.EmailUtil.patternMatches;

import android.app.ActionBar;
import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cdio.desert_eagle.project_bts.databinding.ActivityResetPasswordBinding;
import cdio.desert_eagle.project_bts.model.request.ResetPasswordRequest;
import cdio.desert_eagle.project_bts.viewmodel.ResetPasswordViewModel;

public class ResetPasswordActivity extends AppCompatActivity {

    private ResetPasswordViewModel resetPasswordViewModel;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityResetPasswordBinding binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        CountDownTimer countDownTimer = new CountDownTimer(60000L, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.tvOneMinuteToSendAgain.setText("Send again in: " + (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                binding.btnSend.setEnabled(true);
            }
        };
        binding.btnVerify.setEnabled(false);
        dialog = new Dialog(ResetPasswordActivity.this);
        dialog.setContentView(R.layout.reset_password_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        resetPasswordViewModel = new ResetPasswordViewModel();
        EditText dialogEtPassword = dialog.findViewById(R.id.etNewPassword);
        EditText dialogEtPasswordConfirm = dialog.findViewById(R.id.etConfirmPassword);
        Button dialogBtnReset = dialog.findViewById(R.id.btnReset);

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.btnSend.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString();
            if (!email.isBlank() && patternMatches(email)) {
                resetPasswordViewModel.verifyingEmail(email);
                binding.btnSend.setEnabled(false);
                binding.tvOneMinuteToSendAgain.setVisibility(View.VISIBLE);
                countDownTimer.start();
                binding.btnVerify.setEnabled(true);
            } else {
                Toast.makeText(this, "Please checking email address again", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnVerify.setOnClickListener(v -> {
            if (binding.etOtp.getText().toString().isBlank()) {
                Toast.makeText(this, "Please fill out the OTP", Toast.LENGTH_SHORT).show();
                return;
            }
            resetPasswordViewModel.verifyingOtp(
                    binding.etOtp.getText().toString(),
                    binding.etEmail.getText().toString());
        });

        dialogBtnReset.setOnClickListener(v -> {
            if (dialogEtPassword.getText().toString().isBlank() || dialogEtPasswordConfirm.getText().toString().isBlank()) {
                Toast.makeText(this, "Please fill out the Passwords", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!dialogEtPassword.getText().toString().equals(dialogEtPasswordConfirm.getText().toString())) {
                Toast.makeText(this, "Password is not match", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, binding.etEmail.getText().toString(), Toast.LENGTH_SHORT).show();
            resetPasswordViewModel.resetPassword(binding.etEmail.getText().toString(),
                    new ResetPasswordRequest(
                            dialogEtPassword.getText().toString(),
                            dialogEtPasswordConfirm.getText().toString()
                    ));
        });

        // observer

        resetPasswordViewModel.verifyingOtpLiveData.observe(this, data -> {
            dialog.show();
        });

        resetPasswordViewModel.verifyingEmailLiveData.observe(this, data -> {
            Toast.makeText(this, "Please checking your email and get an OTP to reset your password", Toast.LENGTH_SHORT).show();
        });

        resetPasswordViewModel.resetPasswordLiveData.observe(this, data -> {
            dialog.dismiss();
            Toast.makeText(this, "Everything okay now, please login again", Toast.LENGTH_SHORT).show();
            finish();
        });

        resetPasswordViewModel.errorLiveData.observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }
}