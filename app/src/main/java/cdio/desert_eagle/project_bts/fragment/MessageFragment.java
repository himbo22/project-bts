package cdio.desert_eagle.project_bts.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cdio.desert_eagle.project_bts.databinding.FragmentMessageBinding;
import cdio.desert_eagle.project_bts.model.request.UserMessage;
import cdio.desert_eagle.project_bts.viewmodel.UserListViewModel;

public class MessageFragment extends Fragment {

    FragmentMessageBinding binding;
    UserListViewModel userListViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMessageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userListViewModel = new UserListViewModel();

        binding.btn.setOnClickListener(v -> {
            userListViewModel.sendMessage(
                    new UserMessage(2L, "cac", "zxc", "zxc", "hoang lon"),
                    7L
            );
        });

        userListViewModel.getMessage();

        userListViewModel.realTimeMessageLiveData.observe(requireActivity(), message -> {
            binding.tv.setText(message);
        });

    }
}