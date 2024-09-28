package cdio.desert_eagle.project_bts.view.activity;

import static cdio.desert_eagle.project_bts.constant.ConstantList.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import cdio.desert_eagle.project_bts.adapter.ProfileAdapter;
import cdio.desert_eagle.project_bts.databinding.ActivityUserBinding;
import cdio.desert_eagle.project_bts.listener.OnProfileItemListener;
import cdio.desert_eagle.project_bts.view.dialog.CommentBottomSheetFragment;
import cdio.desert_eagle.project_bts.view.dialog.ReportDialog;
import cdio.desert_eagle.project_bts.viewmodel.ProfileViewModel;
import cdio.desert_eagle.project_bts.viewmodel.UserViewModel;

public class UserActivity extends AppCompatActivity {

    ActivityUserBinding binding;
    private UserViewModel userViewModel;
    private ProfileViewModel profileViewModel;
    private ProfileAdapter profileAdapter;
    private long userId;
    private boolean isFollowed = false;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            o -> {
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // init data
        userViewModel = new UserViewModel(this.getApplication());
        profileViewModel = new ProfileViewModel(this.getApplication());
        Intent intent = getIntent();
        Intent intentToMessage = new Intent(this, MessageActivity.class);
        userId = intent.getLongExtra("userId", 0L);
        profileAdapter = new ProfileAdapter(2, profileViewModel, new OnProfileItemListener() {
            @Override
            public void option(Long postId) {
                ReportDialog reportDialog = new ReportDialog(profileViewModel.userId, postId);
                reportDialog.show(getSupportFragmentManager(), reportDialog.getTag());
            }

            @Override
            public void comment(Long postId) {
                CommentBottomSheetFragment commentBottomSheetFragment = new CommentBottomSheetFragment(postId, profileViewModel.userId);
                commentBottomSheetFragment.show(getSupportFragmentManager(), commentBottomSheetFragment.getTag());
            }

            @Override
            public void user(Long userId) {

            }
        });

        // init view
        binding.btnMessage.setClickable(false);
        binding.btnFollow.setClickable(false);
        binding.rvPosts.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPosts.setAdapter(profileAdapter);
        binding.rvPosts.setNestedScrollingEnabled(false);


        // call api
        userViewModel.getAllUserPosts(userId);
        userViewModel.getUser(userId);
        userViewModel.getFollowUser(userId);

        binding.btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLauncher.launch(intentToMessage);
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollowed) {
                    userViewModel.unFollowUser(userId);
                    isFollowed = false;
                } else {
                    userViewModel.followUser(userId);
                    isFollowed = true;
                }
            }
        });

        binding.main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doEverything();
                binding.main.setRefreshing(false);
            }
        });


        binding.nsProfile.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    userViewModel.loadMoreUserPosts(userId);
                }
            }
        });


        // observer
        userViewModel.allPosts.observe(this, data -> {
            profileAdapter.updateData(data);
        });

        userViewModel.userResponseMutableLiveData.observe(this, data -> {
            Glide.with(binding.imgAvatar.getContext()).load(BASE_URL + "/api/images/" + data.getAvatar())
                    .into(binding.imgAvatar);
            binding.tvUsername.setText(data.getUsername());
            binding.tvBio.setText(data.getBio());
            binding.tvPostCount.setText(String.valueOf(data.getPost()));
            binding.tvFollowerCount.setText(String.valueOf(data.getFollowers()));
            binding.tvFollowingCount.setText(String.valueOf(data.getFollowing()));
            intentToMessage.putExtra("receiverAvatar", data.getAvatar());
            intentToMessage.putExtra("receiverId", data.getId());
            intentToMessage.putExtra("username", data.getUsername());
            binding.btnMessage.setEnabled(true);
        });

        userViewModel.errorLiveData.observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        userViewModel.getFollowUserLiveData.observe(this, isFollowed -> {
            binding.btnFollow.setEnabled(true);
            Log.d("hoangdeptrai", "onCreate: " + isFollowed);
            this.isFollowed = isFollowed;
            if (isFollowed) {
                binding.btnFollow.setText("Following");
            } else {
                binding.btnFollow.setText("Follow");
            }
        });

        userViewModel.followUserLiveData.observe(this, data -> {
            binding.btnFollow.setText("Following");
        });

        userViewModel.unFollowUserLoveData.observe(this, message -> {
            binding.btnFollow.setText("Follow");
        });

    }

    private void doEverything() {
        userViewModel.getUser(userId);
        userViewModel.getAllUserPosts(userId);
        userViewModel.allPosts.observe(this, data -> {
            profileAdapter.resetData(data);
        });
    }

}