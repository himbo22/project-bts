package cdio.desert_eagle.project_bts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cdio.desert_eagle.project_bts.databinding.ActivityEditProfileBinding;
import cdio.desert_eagle.project_bts.fragment.UpdateProfileDialog;
import cdio.desert_eagle.project_bts.repository.BaseResult;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Bitmap b = intent.getParcelableExtra("avatar");
        binding.etUsername.setText(intent.getStringExtra("username"));
        binding.etBio.setText(intent.getStringExtra("bio"));
        binding.imgAvatar.setImageBitmap(b);

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateProfileDialog(
                        "Confirm Change",
                        "Updating this information will update your entire profile",
                        "Update",
                        new BaseResult<Boolean>() {
                            @Override
                            public void onSuccess(Boolean response) {
                                if (response) {
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {

                            }
                        }).show(getSupportFragmentManager(), "UPDATE_DIALOG");
            }
        });

        binding.btnDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateProfileDialog(
                        "Confirm Discard",
                        "Discard all changes",
                        "Discard",
                        new BaseResult<Boolean>() {
                            @Override
                            public void onSuccess(Boolean response) {
                                if (response) {
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {

                            }
                        }).show(getSupportFragmentManager(), "DISCARD_DIALOG");
            }
        });
    }
}