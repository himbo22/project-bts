package cdio.desert_eagle.project_bts.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import cdio.desert_eagle.project_bts.view.activity.MessageActivity;
import cdio.desert_eagle.project_bts.adapter.UserMessagedAdapter;
import cdio.desert_eagle.project_bts.databinding.FragmentMessageBinding;
import cdio.desert_eagle.project_bts.listener.BaseResult;
import cdio.desert_eagle.project_bts.model.request.UserMessage;
import cdio.desert_eagle.project_bts.viewmodel.UserListViewModel;

public class MessageFragment extends Fragment {

    FragmentMessageBinding binding;
    UserListViewModel userListViewModel;
    UserMessagedAdapter userMessagedAdapter;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            o -> {
            }
    );

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMessageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userListViewModel = new UserListViewModel(requireActivity().getApplication());
        userMessagedAdapter = new UserMessagedAdapter(new BaseResult<UserMessage>() {
            @Override
            public void onSuccess(UserMessage response) {
                Intent intent = new Intent(requireActivity(), MessageActivity.class);
                intent.putExtra("receiverAvatar", response.getAvatar());
                intent.putExtra("receiverId", response.getUserId());
                intent.putExtra("username", response.getUsername());
                activityResultLauncher.launch(intent);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        binding.rvUsers.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.rvUsers.setAdapter(userMessagedAdapter);

        userListViewModel.getUsersMessaged();

        binding.svUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                userMessagedAdapter.getFilter().filter(newText);
                return false;
            }
        });

        // observer
        userListViewModel.userMessagesListLiveData.observe(requireActivity(), list -> {
            userMessagedAdapter.update(list);
        });

    }
}