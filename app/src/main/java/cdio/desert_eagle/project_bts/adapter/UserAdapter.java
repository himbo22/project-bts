package cdio.desert_eagle.project_bts.adapter;

import static cdio.desert_eagle.project_bts.constant.ConstantList.BASE_URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import cdio.desert_eagle.project_bts.R;
import cdio.desert_eagle.project_bts.model.response.User;
import cdio.desert_eagle.project_bts.repository.BaseResult;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private final BaseResult<User> listener;

    public UserAdapter(List<User> userList, BaseResult<User> listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userName.setText(user.getUsername());
        Glide.with(holder.userIcon.getContext()).load(BASE_URL + "/api/images/" + user.getAvatar())
                .into(holder.userIcon);
        holder.llUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSuccess(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    // Lớp ViewHolder
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public ImageView userIcon;
        public ImageView messageIcon;
        public LinearLayout llUser;

        public UserViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userIcon = itemView.findViewById(R.id.userIcon);
            messageIcon = itemView.findViewById(R.id.messageIcon);
            llUser = itemView.findViewById(R.id.llUser);
        }
    }

    public void updateList(List<User> newList) {
        this.userList = newList;
        notifyDataSetChanged();
    }
}
