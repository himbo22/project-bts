package cdio.desert_eagle.project_bts;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Objects;

import cdio.desert_eagle.project_bts.databinding.ActivitySignupBinding;
import cdio.desert_eagle.project_bts.viewmodel.RegisterViewModel;

public class SignupActivity extends AppCompatActivity {

    private Uri avatarUri;
    private RegisterViewModel registerViewModel;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (Objects.requireNonNull(o.getData()).getData() != null) {
                        avatarUri = o.getData().getData();
                        binding.imageView2.setImageURI(Objects.requireNonNull(o.getData()).getData());
                    }
                }
            }
    );

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    activityResultLauncher.launch(intent);
                } else {
                    Toast.makeText(this, "You must allow permission for upload avatar", Toast.LENGTH_SHORT).show();
                }
            });

    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        registerViewModel = new RegisterViewModel(this.getApplication());
        EditText emailSign = findViewById(R.id.email);
        EditText name = findViewById(R.id.username);
        EditText pass = findViewById(R.id.password);
        Button Sign = findViewById(R.id.button3);
        TextView lo = findViewById(R.id.login);
        ImageView imgCamera = findViewById(R.id.imgCamera);

        imgCamera.setOnClickListener(v -> onclickRequestPermission());

        //  Sign up
        Sign.setOnClickListener(v -> {
            String email = emailSign.getText().toString().trim();
            String username = name.getText().toString().trim();
            String password = pass.getText().toString().trim();
            String bio = binding.textView.getText().toString().trim();
            if (email.isBlank() || username.isBlank() || password.isBlank() || bio.isBlank() || avatarUri == null) {
                return;
            }
            binding.pbLoading.setVisibility(View.VISIBLE);
            registerViewModel.register(username, password, email, bio, avatarUri);
        });
        lo.setOnClickListener(v -> {
            finish();
        });

        // observer
        registerViewModel.registerLiveData.observe(this, data -> {
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
            binding.pbLoading.setVisibility(View.GONE);
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        });
        registerViewModel.errorLiveData.observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    private void onclickRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            requestPermissionLauncher.launch(Arrays.toString(new String[]{
                    READ_MEDIA_IMAGES,
                    READ_MEDIA_VISUAL_USER_SELECTED
            }));
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(READ_MEDIA_IMAGES);
        } else {
            requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE);
        }
    }
}