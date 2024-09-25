package cdio.desert_eagle.project_bts.view.activity;

import static cdio.desert_eagle.project_bts.constant.ConstantList.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import java.util.Objects;

import cdio.desert_eagle.project_bts.adapter.MessageAdapter;
import cdio.desert_eagle.project_bts.databinding.ActivityMessageBinding;
import cdio.desert_eagle.project_bts.model.request.Message;
import cdio.desert_eagle.project_bts.model.request.UserMessage;
import cdio.desert_eagle.project_bts.viewmodel.MessageViewModel;

public class MessageActivity extends AppCompatActivity {

    ActivityMessageBinding binding;
    MessageViewModel messageViewModel;
    MessageAdapter messageAdapter;
    Intent intent;
    String receiverAvatar;
    Long receiverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // init data
        intent = getIntent();
        messageViewModel = new MessageViewModel(this.getApplication());
        messageAdapter = new MessageAdapter(
                messageViewModel.userAvatar,
                intent.getStringExtra("receiverAvatar"));
        receiverAvatar = intent.getStringExtra("receiverAvatar");
        receiverId = intent.getLongExtra("receiverId", 0L);

        binding.rvMessage.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMessage.setAdapter(messageAdapter);
        binding.tvUsername.setText(intent.getStringExtra("username"));
        Glide.with(this).load(BASE_URL + "/api/images/" + intent.getStringExtra("receiverAvatar"))
                .into(binding.imgAvatar);

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

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.requireNonNull(binding.etMessage.getText()).toString().isBlank()) {
                    return;
                }
                messageViewModel.sendMessage(
                        new UserMessage(receiverId, intent.getStringExtra("username"), receiverAvatar),
                        new Message(binding.etMessage.getText().toString())
                );
                binding.etMessage.setText("");
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        messageViewModel.getMessage(intent.getLongExtra("receiverId", 0L));

        messageViewModel.messagesLiveData.observe(this, messages -> {
            messageAdapter.setChatMessages(messages);
            binding.rvMessage.smoothScrollToPosition(messageAdapter.getItemCount());
        });

        messageViewModel.realTimeMessageLiveData.observe(this, message -> {
            binding.rvMessage.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
        });

    }
}