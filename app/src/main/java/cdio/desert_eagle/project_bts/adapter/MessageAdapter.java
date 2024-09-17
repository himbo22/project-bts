package cdio.desert_eagle.project_bts.adapter;

import static cdio.desert_eagle.project_bts.constant.ConstantList.BASE_URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cdio.desert_eagle.project_bts.databinding.ReceiveMessageBinding;
import cdio.desert_eagle.project_bts.databinding.SendMessageBinding;
import cdio.desert_eagle.project_bts.model.request.UserMessage;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ChatViewHolder> {

    private static final int SENDING = 1;
    private static final int RECEIVING = 2; // RECEPTION is also right
    private final Long userId;
    List<UserMessage> chatMessages = new ArrayList<>();

    public MessageAdapter(Long userId) {
        this.userId = userId;
    }

    @Override
    public int getItemViewType(int position) {
        if (Objects.equals(chatMessages.get(position).getUserId(), userId)) {
            return SENDING;
        } else {
            return RECEIVING;
        }
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case SENDING:
                SendMessageBinding sendingBinding = SendMessageBinding
                        .inflate(LayoutInflater.from(parent.getContext()),
                                parent,
                                false);
                return new SendingViewHolder(sendingBinding);
            case RECEIVING:
                ReceiveMessageBinding receivingBinding = ReceiveMessageBinding
                        .inflate(LayoutInflater.from(parent.getContext()),
                                parent,
                                false);
                return new ReceivingViewHolder(receivingBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        UserMessage userMessage = chatMessages.get(position);
        if (holder.getItemViewType() == SENDING) {
            SendMessageBinding binding = ((SendingViewHolder) holder).sendingBinding;
//            binding.tvMessage.setText(userMessage.getMessage());
//            binding.tvSentAt.setText(userMessage.getSentAt());
            Glide.with(binding.imgAvatar.getContext())
                    .load(BASE_URL + "/api/images/" + userMessage.getAvatar())
                    .into(binding.imgAvatar);
        } else {
            ReceiveMessageBinding binding = ((ReceivingViewHolder) holder).receivingBinding;
//            binding.tvMessage.setText(userMessage.getMessage());
//            binding.tvSentAt.setText(userMessage.getSentAt());
            Glide.with(binding.imgAvatar.getContext())
                    .load(BASE_URL + "/api/images/" + userMessage.getAvatar())
                    .into(binding.imgAvatar);
        }
    }


    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public void setChatMessages(List<UserMessage> messages) {
        this.chatMessages = messages;
        notifyDataSetChanged();
    }

    public void addMessage(UserMessage userMessage) {
        this.chatMessages.add(userMessage);
        notifyDataSetChanged();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class SendingViewHolder extends ChatViewHolder {

        private final SendMessageBinding sendingBinding;

        public SendingViewHolder(SendMessageBinding binding) {
            super(binding.getRoot());
            this.sendingBinding = binding;
        }
    }

    public static class ReceivingViewHolder extends ChatViewHolder {

        private final ReceiveMessageBinding receivingBinding;

        public ReceivingViewHolder(ReceiveMessageBinding binding) {
            super(binding.getRoot());
            this.receivingBinding = binding;
        }
    }

}
