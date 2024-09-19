package cdio.desert_eagle.project_bts.adapter;

import static cdio.desert_eagle.project_bts.constant.ConstantList.BASE_URL;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cdio.desert_eagle.project_bts.databinding.ItemUserMessagedBinding;
import cdio.desert_eagle.project_bts.model.request.UserMessage;
import cdio.desert_eagle.project_bts.repository.BaseResult;

public class UserMessagedAdapter extends RecyclerView.Adapter<UserMessagedAdapter.MyViewHolder> implements Filterable {

    private final List<UserMessage> userMessages;
    private final List<UserMessage> usersMessagesFull;
    private final BaseResult<UserMessage> listener;

    public UserMessagedAdapter(BaseResult<UserMessage> listener) {
        this.userMessages = new ArrayList<>();
        this.usersMessagesFull = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserMessagedBinding binding = ItemUserMessagedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserMessage userMessage = userMessages.get(position);

        holder.binding.tvUsername.setText(userMessage.getUsername());
        Glide.with(holder.binding.imgAvatar.getContext()).load(BASE_URL + "/api/images/" + userMessage.getAvatar())
                .into(holder.binding.imgAvatar);

        holder.binding.clMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSuccess(userMessage);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userMessages.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                List<UserMessage> usersFiltered = new ArrayList<>();
                FilterResults results = new FilterResults();

                if (charString.isEmpty()) {
                    synchronized (usersMessagesFull) {
                        usersFiltered = usersMessagesFull;
                    }
                    results.values = usersFiltered;
                    results.count = usersFiltered.size();
                } else {
                    for (UserMessage userMessage : usersMessagesFull) {
                        if (userMessage.getUsername().toLowerCase().contains(charString.toLowerCase())) {
                            usersFiltered.add(userMessage);
                        }
                    }
                    results.values = usersFiltered;
                    results.count = usersFiltered.size();
                }

                return results;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values != null) {
                    userMessages.clear();
                    userMessages.addAll((List<UserMessage>) results.values);
                    notifyDataSetChanged();
                }
            }
        };
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ItemUserMessagedBinding binding;

        public MyViewHolder(@NonNull ItemUserMessagedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(List<UserMessage> newList) {
        this.userMessages.clear();
        this.usersMessagesFull.clear();
        this.userMessages.addAll(newList);
        this.usersMessagesFull.addAll(newList);
        notifyDataSetChanged();
    }
}
