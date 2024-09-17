package cdio.desert_eagle.project_bts;

import static cdio.desert_eagle.project_bts.constant.ConstantList.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import cdio.desert_eagle.project_bts.adapter.OnProfileItemListener;
import cdio.desert_eagle.project_bts.adapter.ProfileAdapter;
import cdio.desert_eagle.project_bts.databinding.ActivityUserBinding;
import cdio.desert_eagle.project_bts.viewmodel.UserViewModel;

public class UserActivity extends AppCompatActivity {

    ActivityUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // init data
        UserViewModel userViewModel = new UserViewModel();
        Intent intent = getIntent();
        long userId = intent.getLongExtra("userId", 0L);

        ProfileAdapter profileAdapter = new ProfileAdapter(this, new OnProfileItemListener() {
            @Override
            public void option() {

            }

            @Override
            public void like(long userId, long postId, int position) {
            }

            @Override
            public void liked(long userId, long postId, int position) {
                userViewModel.reactionExisted(userId, postId, position);
            }

            @Override
            public void comment(Long postId) {

            }
        });


        binding.rvPosts.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPosts.setAdapter(profileAdapter);
        userViewModel.getAllUserPosts(userId);
        userViewModel.getUser(userId);


        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userViewModel.allPosts.observe(this, data -> {
            profileAdapter.updateData(data);
        });

        userViewModel.userResponseMutableLiveData.observe(this, data -> {
            Log.d("hoangdeptrai", "onCreate: " + data.toString());
            Glide.with(binding.imgAvatar.getContext()).load(BASE_URL + "/api/images/" + data.getAvatar())
                    .into(binding.imgAvatar);
            binding.tvUsername.setText(data.getUsername());
            binding.tvBio.setText(data.getBio());
            binding.tvPostCount.setText(String.valueOf(data.getPost()));
            binding.tvFollowerCount.setText(String.valueOf(data.getFollowers()));
            binding.tvFollowingCount.setText(String.valueOf(data.getFollowing()));
        });

        userViewModel.errorLiveData.observe(this, message -> {
            Log.d("hoangdeptrai", "onCreate: " + message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        userViewModel.existedReaction.observe(this, liked -> {
            profileAdapter.updateItem(liked.getSecond(), liked.getFirst());
        });
    }

}