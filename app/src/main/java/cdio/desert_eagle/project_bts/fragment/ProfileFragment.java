package cdio.desert_eagle.project_bts.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import cdio.desert_eagle.project_bts.LoginActivity;
import cdio.desert_eagle.project_bts.MainActivity;
import cdio.desert_eagle.project_bts.R;
import cdio.desert_eagle.project_bts.adapter.OnProfileItemListener;
import cdio.desert_eagle.project_bts.adapter.ProfileAdapter;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import cdio.desert_eagle.project_bts.viewmodel.ProfileViewModel;


public class ProfileFragment extends Fragment {

    ProfileViewModel profileViewModel;
    List<UserPosts> userPosts = new ArrayList<>();
    RecyclerView rvPosts;
    ProfileAdapter profileAdapter;
    SwipeRefreshLayout srProfile;
    NestedScrollView nsProfile;
    Toolbar toolbar;
    private boolean loading = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        rvPosts = view.findViewById(R.id.rvPosts);
        rvPosts.setNestedScrollingEnabled(false);
        srProfile = view.findViewById(R.id.srProfile);
        nsProfile = view.findViewById(R.id.nsProfile);
        toolbar = view.findViewById(R.id.toolBar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileViewModel = new ProfileViewModel(requireActivity().getApplication());
        MenuHost menuHost = requireActivity();

        ((MainActivity) requireActivity()).setSupportActionBar(toolbar);

        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.profile_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.editProfile) {

                    return true;
                } else if (menuItem.getItemId() == R.id.logOut) {
                    profileViewModel.logOut();
                    startActivity(new Intent(requireActivity(), LoginActivity.class));
                    requireActivity().finishAffinity();
                    return true;
                }

                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        profileAdapter = new ProfileAdapter(userPosts, getContext(), profileViewModel, new OnProfileItemListener() {
            @Override
            public void option() {

            }

            @Override
            public void comment(Long postId) {
                CommentBottomSheetFragment commentBottomSheetFragment = new CommentBottomSheetFragment(postId, profileViewModel.userId);
                commentBottomSheetFragment.show(getParentFragmentManager(), commentBottomSheetFragment.getTag());
            }
        });
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPosts.setAdapter(profileAdapter);
        profileViewModel.getAllUserPosts(profileViewModel.userId, profileViewModel.pages, 20);

        profileViewModel.allPosts.observe(requireActivity(), userPostsLiveData -> {
            profileAdapter.updateData(userPostsLiveData);
            loading = true;
        });

        srProfile.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doEverything();
                srProfile.setRefreshing(false);
            }
        });


        rvPosts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    RecyclerView.LayoutManager lm = rvPosts.getLayoutManager();
                    assert lm != null;
                    visibleItemCount = lm.getChildCount();
                    totalItemCount = lm.getItemCount();
                    pastVisibleItems = ((LinearLayoutManager) lm).findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            profileViewModel.loadMoreUserPosts(profileViewModel.userId, 20);
                        }
                    }
                }
            }
        });

    }


    private void doEverything() {
        profileViewModel.getAllUserPosts(profileViewModel.userId, 0, 20);
        profileViewModel.allPosts.observe(requireActivity(), userPostsLiveData -> {
            profileAdapter.resetData(userPostsLiveData);
        });
    }


}