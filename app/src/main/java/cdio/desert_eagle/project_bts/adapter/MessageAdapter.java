package cdio.desert_eagle.project_bts.adapter;

import static cdio.desert_eagle.project_bts.constant.ConstantList.BASE_URL;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cdio.desert_eagle.project_bts.databinding.ReceiveMessageBinding;
import cdio.desert_eagle.project_bts.databinding.SendMessageBinding;
import cdio.desert_eagle.project_bts.model.request.MESSAGE_TYPE;
import cdio.desert_eagle.project_bts.model.request.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ChatViewHolder> {

    private static final int SENDING = 1;
    private static final int RECEIVING = 2; // RECEPTION is also right
    List<Message> chatMessages = new ArrayList<>();
    private final String userAvatar;
    private final String receiverAvatar;

    public MessageAdapter(String userAvatar, String receiverAvatar) {
        this.userAvatar = userAvatar;
        this.receiverAvatar = receiverAvatar;
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessages.get(0).getType() == MESSAGE_TYPE.SENDING) {
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
        Message message = chatMessages.get(position);
        if (holder.getItemViewType() == SENDING) {
            SendMessageBinding binding = ((SendingViewHolder) holder).sendingBinding;
            binding.tvMessage.setText(message.getMessage());
            binding.tvSentAt.setText(message.getSentAt());
            Glide.with(binding.imgAvatar.getContext())
                    .load(BASE_URL + "/api/images/" + userAvatar)
                    .into(binding.imgAvatar);
        } else {
            ReceiveMessageBinding binding = ((ReceivingViewHolder) holder).receivingBinding;
            binding.tvMessage.setText(message.getMessage());
            binding.tvSentAt.setText(message.getSentAt());
            Glide.with(binding.imgAvatar.getContext())
                    .load(BASE_URL + "/api/images/" + receiverAvatar)
                    .into(binding.imgAvatar);
        }
    }


    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setChatMessages(List<Message> messages) {
        this.chatMessages = messages;
        notifyDataSetChanged();
    }

    public void addMessage(Message messages) {
        this.chatMessages.add(messages);
        notifyItemInserted(chatMessages.size());
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
