package cdio.desert_eagle.project_bts.view.fragment;

import static android.app.Activity.RESULT_OK;
import static cdio.desert_eagle.project_bts.constant.ConstantList.BASE_URL;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import cdio.desert_eagle.project_bts.view.activity.EditProfileActivity;
import cdio.desert_eagle.project_bts.view.activity.LoginActivity;
import cdio.desert_eagle.project_bts.view.activity.MainActivity;
import cdio.desert_eagle.project_bts.R;
import cdio.desert_eagle.project_bts.adapter.ProfileAdapter;
import cdio.desert_eagle.project_bts.databinding.FragmentProfileBinding;
import cdio.desert_eagle.project_bts.listener.BaseResult;
import cdio.desert_eagle.project_bts.listener.OnProfileItemListener;
import cdio.desert_eagle.project_bts.view.dialog.CommentBottomSheetFragment;
import cdio.desert_eagle.project_bts.view.dialog.UpdateProfileDialog;
import cdio.desert_eagle.project_bts.viewmodel.ProfileViewModel;


public class ProfileFragment extends Fragment {

    ProfileViewModel profileViewModel;
    ProfileAdapter profileAdapter;
    FragmentProfileBinding binding;

    private final ActivityResultLauncher<Intent> editProfileResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    doEverything();
                }
            }
    );

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileViewModel = new ProfileViewModel(requireActivity().getApplication());
        MenuHost menuHost = requireActivity();

        ((MainActivity) requireActivity()).setSupportActionBar(binding.toolBar);

        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.profile_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.editProfile) {
                    binding.imgAvatar.setDrawingCacheEnabled(true);
                    Intent intent = new Intent(requireActivity(), EditProfileActivity.class);
                    Bitmap b = binding.imgAvatar.getDrawingCache();
                    intent.putExtra("avatar", b);
                    intent.putExtra("username", binding.tvUsername.getText());
                    intent.putExtra("bio", binding.tvBio.getText());
                    editProfileResultLauncher.launch(intent);
                    return true;
                } else if (menuItem.getItemId() == R.id.logOut) {
                    new UpdateProfileDialog("Are you sure to log out?",
                            "",
                            "YES", new BaseResult<Boolean>() {
                        @Override
                        public void onSuccess(Boolean response) {
                            if (response) {
                                profileViewModel.logOut();
                                startActivity(new Intent(requireActivity(), LoginActivity.class));
                                requireActivity().finishAffinity();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    }).show(getParentFragmentManager(), "log_out");

                    return true;
                }

                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        profileAdapter = new ProfileAdapter(1, profileViewModel, new OnProfileItemListener() {
            @Override
            public void option(Long postId) {
                UpdateProfileDialog updateProfileDialog = new UpdateProfileDialog(
                        "Are you sure delete this post?",
                        "",
                        "Yes",
                        new BaseResult<Boolean>() {
                            @Override
                            public void onSuccess(Boolean response) {
                                if (response) {
                                    profileViewModel.deletePost(postId);
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {

                            }
                        }
                );
                updateProfileDialog.show(getParentFragmentManager(), "delete_post");
            }

            @Override
            public void comment(Long postId) {
                CommentBottomSheetFragment commentBottomSheetFragment = new CommentBottomSheetFragment(postId, profileViewModel.userId);
                commentBottomSheetFragment.show(getParentFragmentManager(), commentBottomSheetFragment.getTag());
            }
        });
        binding.rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvPosts.setAdapter(profileAdapter);
        profileViewModel.getAllUserPosts();
        profileViewModel.getUserInformation();

        // observer
        profileViewModel.allPosts.observe(requireActivity(), userPostsLiveData -> {
            profileAdapter.resetData(userPostsLiveData);
        });

        profileViewModel.deletePostLiveData.observe(requireActivity(), message -> {
            doEverything();
        });

        profileViewModel.userResponseMutableLiveData.observe(requireActivity(), data -> {
            Glide.with(binding.imgAvatar.getContext()).
                    load(BASE_URL + "/api/images/" + data.getAvatar()).into(binding.imgAvatar);
            binding.tvUsername.setText(data.getUsername());
            binding.tvBio.setText(data.getBio() != null ? data.getBio() : "");
            binding.tvPostCount.setText(String.valueOf(data.getPost()));
            binding.tvFollowerCount.setText(String.valueOf(data.getFollowers()));
            binding.tvFollowingCount.setText(String.valueOf(data.getFollowing()));
        });

        binding.srProfile.setOnRefreshListener(() -> {
            doEverything();
            binding.srProfile.setRefreshing(false);
        });


        binding.nsProfile.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                profileViewModel.loadMoreUserPosts();
            }
        });

    }


    private void doEverything() {
        profileViewModel.getUserInformation();
        profileViewModel.getAllUserPosts();
        profileViewModel.allPosts.observe(requireActivity(), userPostsLiveData -> profileAdapter.resetData(userPostsLiveData));
    }

}