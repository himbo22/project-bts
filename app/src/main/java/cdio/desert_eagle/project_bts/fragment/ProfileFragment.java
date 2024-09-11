package cdio.desert_eagle.project_bts.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        profileAdapter = new ProfileAdapter(userPosts, getContext(), profileViewModel, new OnProfileItemListener() {
            @Override
            public void option() {

            }

            @Override
            public void comment(Long postId) {
                CommentBottomSheetFragment commentBottomSheetFragment = new CommentBottomSheetFragment(postId, 7L);
                commentBottomSheetFragment.show(getParentFragmentManager(), commentBottomSheetFragment.getTag());
            }
        });
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPosts.setAdapter(profileAdapter);
        profileViewModel.getAllUserPosts(7L, profileViewModel.pages, 20);

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
                            profileViewModel.loadMoreUserPosts(7L, 20);
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void doEverything() {
        profileViewModel.getAllUserPosts(7L, 0, 20);
        profileViewModel.allPosts.observe(requireActivity(), userPostsLiveData -> {
            profileAdapter.updateData(userPostsLiveData);
        });
    }
}