package cdio.desert_eagle.project_bts.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cdio.desert_eagle.project_bts.R;
import cdio.desert_eagle.project_bts.UserActivity;
import cdio.desert_eagle.project_bts.adapter.UserAdapter;
import cdio.desert_eagle.project_bts.model.response.User;
import cdio.desert_eagle.project_bts.repository.BaseResult;
import cdio.desert_eagle.project_bts.viewmodel.SearchViewModel;

public class SearchFragment extends Fragment {

    private UserAdapter userAdapter;
    private SearchViewModel searchViewModel;
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                }
            }
    );


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout cho Fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // init data
        searchViewModel = new SearchViewModel();

        // Thiết lập RecyclerView
        RecyclerView recyclerViewUsers = view.findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getContext()));

        // Thiết lập adapter cho RecyclerView
        userAdapter = new UserAdapter(new BaseResult<User>() {
            @Override
            public void onSuccess(User response) {
                Intent intent = new Intent(requireActivity(), UserActivity.class);
                intent.putExtra("userId", response.getId());
                activityResultLauncher.launch(intent);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        recyclerViewUsers.setAdapter(userAdapter);

        // Thiết lập SearchView
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewModel.searchUsersByUsername(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        searchViewModel.usersSearchResult.observe(requireActivity(), listUsers -> {
            userAdapter.updateList(listUsers);
        });

        return view;
    }
}
