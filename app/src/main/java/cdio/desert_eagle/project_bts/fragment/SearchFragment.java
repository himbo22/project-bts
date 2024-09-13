package cdio.desert_eagle.project_bts.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cdio.desert_eagle.project_bts.R;
import cdio.desert_eagle.project_bts.adapter.UserAdapter;
import cdio.desert_eagle.project_bts.model.response.User;
import cdio.desert_eagle.project_bts.repository.BaseResult;
import cdio.desert_eagle.project_bts.viewmodel.SearchViewModel;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;
    private List<User> userList;
    private SearchViewModel searchViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout cho Fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // init data
        searchViewModel = new SearchViewModel();
        userList = new ArrayList<>();

        // Thiết lập RecyclerView
        recyclerViewUsers = view.findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getContext()));

        // Thiết lập adapter cho RecyclerView
        userAdapter = new UserAdapter(userList, new BaseResult<User>() {
            @Override
            public void onSuccess(User response) {

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
                searchViewModel.searchUsersByUsername(newText);
                return true;
            }
        });

        searchViewModel.usersSearchResult.observe(requireActivity(), listUsers -> {
            userAdapter.updateList(listUsers);
        });

        return view;
    }
}
