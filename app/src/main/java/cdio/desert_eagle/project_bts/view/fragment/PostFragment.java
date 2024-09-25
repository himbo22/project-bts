package cdio.desert_eagle.project_bts.view.fragment;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED;
import static cdio.desert_eagle.project_bts.constant.ConstantList.BASE_URL;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.Arrays;

import cdio.desert_eagle.project_bts.databinding.FragmentPostBinding;
import cdio.desert_eagle.project_bts.listener.ViewPagerNavigator;
import cdio.desert_eagle.project_bts.viewmodel.PostViewModel;

public class PostFragment extends Fragment {


    FragmentPostBinding binding;
    PostViewModel postViewModel;
    private Intent intentImagePicker;
    private Uri image;
    ViewPagerNavigator viewPagerNavigator;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getData().getData() != null) {
                        image = result.getData().getData();
                        binding.imgContent.setVisibility(View.VISIBLE);
                        binding.imgContent.setImageURI(result.getData().getData());
                    }
                }
            }
    );

    private final ActivityResultLauncher<String> requestPermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean o) {
                    if (o) {
                        activityResultLauncher.launch(intentImagePicker);
                    } else {
                        Toast.makeText(requireActivity(),
                                        "You must allow permission for upload avatar",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(inflater, container, false);

        // init data
        postViewModel = new PostViewModel(requireActivity().getApplication());
        intentImagePicker = new Intent();
        intentImagePicker.setType("image/*");
        intentImagePicker.setAction(Intent.ACTION_GET_CONTENT);

        // bind view
        binding.tvUsername.setText(postViewModel.username);
        Glide.with(requireActivity()).load(BASE_URL + "/api/images/" + postViewModel.avatar).into(binding.imgAvatar);

        binding.imgPost.setOnClickListener(v -> {
            if (binding.etCaption.getText().toString().isBlank() || binding.imgContent.getDrawable() == null) {
                Toast.makeText(requireActivity(), "Please add a caption and image to share your status.", Toast.LENGTH_SHORT).show();
            } else {
                binding.pbLoading.setVisibility(View.VISIBLE);
                postViewModel.createPost(binding.etCaption.getText().toString(), image);
            }
        });

        binding.btnAddImage.setOnClickListener(v -> onRequestPermission());

        // observer
        postViewModel.successfulShareStatusLiveData.observe(requireActivity(), message -> {
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
            binding.pbLoading.setVisibility(View.GONE);
            binding.etCaption.setText("");
            binding.imgContent.setImageDrawable(null);
            binding.imgContent.setVisibility(View.GONE);
            viewPagerNavigator.moveToFragment(0);
        });

        postViewModel.errorLiveData.observe(requireActivity(), message -> {
            binding.pbLoading.setVisibility(View.GONE);
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }

    private void onRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            requestPermission.launch(Arrays.toString(new String[]{
                    READ_MEDIA_IMAGES,
                    READ_MEDIA_VISUAL_USER_SELECTED
            }));
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
            requestPermission.launch(READ_MEDIA_IMAGES);
        } else {
            requestPermission.launch(READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ViewPagerNavigator) {
            viewPagerNavigator = (ViewPagerNavigator) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ViewPagerNavigator");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}