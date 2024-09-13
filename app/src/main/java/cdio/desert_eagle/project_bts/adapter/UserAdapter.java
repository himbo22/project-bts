package cdio.desert_eagle.project_bts.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Filter;
import android.widget.Filterable;
import java.util.ArrayList;
import java.util.List;
import cdio.desert_eagle.project_bts.R;
import cdio.desert_eagle.project_bts.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> implements Filterable {

    private List<User> userList;
    private List<User> userListFull; // Danh sách đầy đủ để lọc

    public UserAdapter(List<User> userList) {
        this.userList = userList;
        this.userListFull = new ArrayList<>(userList); // Sao chép danh sách đầy đủ
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
        holder.userName.setText(user.getName());
        holder.userIcon.setImageResource(user.getUserIcon());
        holder.messageIcon.setImageResource(user.getMessageIcon());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    // Lớp Filter(tìm kiếm)
    @Override
    public Filter getFilter() {
        return userFilter;
    }

    private Filter userFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<User> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(userListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (User user : userListFull) {
                    if (user.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(user);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            userList.clear();
            userList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    // Lớp ViewHolder
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public ImageView userIcon;
        public ImageView messageIcon;

        public UserViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userIcon = itemView.findViewById(R.id.userIcon);
            messageIcon = itemView.findViewById(R.id.messageIcon);
        }
    }
}
