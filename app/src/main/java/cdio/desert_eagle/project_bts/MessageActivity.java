package cdio.desert_eagle.project_bts;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import cdio.desert_eagle.project_bts.adapter.MessageAdapter;
import cdio.desert_eagle.project_bts.databinding.ActivityMessageBinding;
import cdio.desert_eagle.project_bts.viewmodel.MessageViewModel;

public class MessageActivity extends AppCompatActivity {

    ActivityMessageBinding binding;
    MessageViewModel messageViewModel;
    MessageAdapter messageAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // init data
        intent = getIntent();
        messageViewModel = new MessageViewModel(this.getApplication());
        messageAdapter = new MessageAdapter(
                intent.getStringExtra("userAvatar"),
                intent.getStringExtra("receiverAvatar"));

        binding.rvMessage.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMessage.setAdapter(messageAdapter);


//        binding.btn.setOnClickListener(v -> {
//            userListViewModel.sendMessage(
//
//            );
//        });
//
//        userListViewModel.getMessage();
//
//        userListViewModel.realTimeMessageLiveData.observe(requireActivity(), message -> {
//            binding.tv.setText(message);
//        });

//        messageViewModel.sendMessage(
//                new UserMessage(13L, "tintuc_24h", "e3cc0fe8f6584ab684139b384721e9ad.jpg"),
//                new Message(7L, "", "hoang lon")
//        );

        messageViewModel.getMessage(intent.getLongExtra("receiverId", 0L));

        messageViewModel.messagesLiveData.observe(this, messages -> {
            messageAdapter.setChatMessages(messages);
        });

    }
}