package cdio.desert_eagle.project_bts;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Objects;

import cdio.desert_eagle.project_bts.databinding.ActivityEditProfileBinding;
import cdio.desert_eagle.project_bts.fragment.UpdateProfileDialog;
import cdio.desert_eagle.project_bts.listener.BaseResult;
import cdio.desert_eagle.project_bts.viewmodel.UpdateProfileViewModel;

public class EditProfileActivity extends AppCompatActivity {

    private Uri newAvatar = null;
    private UpdateProfileViewModel updateProfileViewModel;
    private ActivityEditProfileBinding binding;

    private final ActivityResultLauncher<String> permissionResultLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean isGranted) {
                    if (isGranted) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        imagePickerResultLauncher.launch(intent);
                    } else {
                        Toast.makeText(EditProfileActivity.this,
                                        "You must allow permission for upload avatar",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
    );

    private final ActivityResultLauncher<Intent> imagePickerResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    newAvatar = Objects.requireNonNull(result.getData()).getData();
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    Toast.makeText(EditProfileActivity.this, "Image selection canceled", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        updateProfileViewModel = new UpdateProfileViewModel(this.getApplication());

        Intent intent = getIntent();
        Bitmap b = intent.getParcelableExtra("avatar");
        binding.etUsername.setText(intent.getStringExtra("username"));
        binding.etBio.setText(intent.getStringExtra("bio"));
        binding.imgAvatar.setImageBitmap(b);


        binding.imgAvatar.setOnClickListener(v -> onRequestPermission());

        binding.btnConfirm.setOnClickListener(v -> openUpdateDialog());

        binding.btnDiscard.setOnClickListener(v -> openDiscardDialog());
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                openDiscardDialog();
            }
        });
    }

    private void openUpdateDialog() {
        new UpdateProfileDialog(
                "Confirm Change",
                "Updating this information will update your entire profile",
                "Update",
                new BaseResult<Boolean>() {
                    @Override
                    public void onSuccess(Boolean response) {
                        if (response) {
                            updateProfileViewModel.updateProfile(
                                    Objects.requireNonNull(binding.etUsername.getText()).toString(),
                                    Objects.requireNonNull(binding.etBio.getText()).toString(),
                                    newAvatar
                            );
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                }).show(getSupportFragmentManager(), "UPDATE_DIALOG");
    }

    private void openDiscardDialog() {
        new UpdateProfileDialog(
                "Confirm Discard",
                "Discard all changes",
                "Discard",
                new BaseResult<Boolean>() {
                    @Override
                    public void onSuccess(Boolean response) {
                        if (response) {
                            setResult(Activity.RESULT_CANCELED);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                }).show(getSupportFragmentManager(), "DISCARD_DIALOG");
    }

    private void onRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            permissionResultLauncher.launch(Arrays.toString(new String[]{
                    READ_MEDIA_IMAGES,
                    READ_MEDIA_VISUAL_USER_SELECTED
            }));
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
            permissionResultLauncher.launch(READ_MEDIA_IMAGES);
        } else {
            permissionResultLauncher.launch(READ_EXTERNAL_STORAGE);
        }
    }
}