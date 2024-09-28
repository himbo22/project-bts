package cdio.desert_eagle.project_bts.view.fragment;

import static cdio.desert_eagle.project_bts.constant.ConstantList.BASE_URL;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import cdio.desert_eagle.project_bts.adapter.StatusAdapter;
import cdio.desert_eagle.project_bts.databinding.FragmentHomeBinding;
import cdio.desert_eagle.project_bts.listener.OnProfileItemListener;
import cdio.desert_eagle.project_bts.listener.ViewPagerNavigator;
import cdio.desert_eagle.project_bts.view.activity.UserActivity;
import cdio.desert_eagle.project_bts.view.dialog.CommentBottomSheetFragment;
import cdio.desert_eagle.project_bts.view.dialog.ReportDialog;
import cdio.desert_eagle.project_bts.viewmodel.HomeViewModel;
import cdio.desert_eagle.project_bts.viewmodel.ProfileViewModel;

public class HomeFragment extends Fragment {

    ViewPagerNavigator viewPagerNavigator;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            o -> {
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater, container, false);
        HomeViewModel homeViewModel = new HomeViewModel();
        ProfileViewModel profileViewModel = new ProfileViewModel(requireActivity().getApplication());
        StatusAdapter statusAdapter = new StatusAdapter(requireActivity(), profileViewModel,
                new OnProfileItemListener() {
                    @Override
                    public void option(Long postId) {
                        ReportDialog reportDialog = new ReportDialog(
                                profileViewModel.userId, postId
                        );
                        reportDialog.show(getParentFragmentManager(), "REPORT");
                    }

                    @Override
                    public void comment(Long postId) {
                        CommentBottomSheetFragment commentBottomSheetFragment = new CommentBottomSheetFragment(postId, profileViewModel.userId);
                        commentBottomSheetFragment.show(getParentFragmentManager(), commentBottomSheetFragment.getTag());
                    }

                    @Override
                    public void user(Long userId) {
                        Intent intent = new Intent(requireActivity(), UserActivity.class);
                        intent.putExtra("userId", userId);
                        activityResultLauncher.launch(intent);
                    }

                });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recyclerView.setAdapter(statusAdapter);
        Glide.with(binding.userAvatar.getContext()).load(BASE_URL + "/api/images/" + profileViewModel.avatar)
                .into(binding.userAvatar);

        homeViewModel.getAllPosts();

        binding.homeScreen.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                homeViewModel.loadMore();
            }
        });

        binding.btnAddStatus.setOnClickListener(v -> {
            viewPagerNavigator.moveToFragment(2);
        });

        // observer

        homeViewModel.allPostsLiveData.observe(requireActivity(), statusAdapter::updateData);


        return binding.getRoot();
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

}
