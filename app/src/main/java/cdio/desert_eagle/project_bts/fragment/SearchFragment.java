package cdio.desert_eagle.project_bts.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import java.util.ArrayList;
import java.util.List;
import cdio.desert_eagle.project_bts.R;
import cdio.desert_eagle.project_bts.adapter.UserAdapter;
import cdio.desert_eagle.project_bts.model.User;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;
    private List<User> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout cho Fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Khởi tạo nút quay lại và xử lý sự kiện nhấn
        ImageView backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> getActivity().onBackPressed());

        // Thiết lập RecyclerView
        recyclerViewUsers = view.findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getContext()));

        // Tạo danh sách người dùng
        userList = new ArrayList<>();
        userList.add(new User("User 1", R.drawable.user, R.drawable.messenger));
        userList.add(new User("User 2", R.drawable.user, R.drawable.messenger));
        // Thêm nhiều người dùng hơn nếu cần

        // Thiết lập adapter cho RecyclerView
        userAdapter = new UserAdapter(userList);
        recyclerViewUsers.setAdapter(userAdapter);

        // Thiết lập SearchView
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Lọc danh sách người dùng dựa trên từ khóa tìm kiếm
                userAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return view;
    }
}
