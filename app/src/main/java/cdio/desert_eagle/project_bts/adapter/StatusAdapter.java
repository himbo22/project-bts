package cdio.desert_eagle.project_bts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cdio.desert_eagle.project_bts.R;
import cdio.desert_eagle.project_bts.model.StatusModel;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {
    public List<StatusModel> statusList;
    public Context context;
    public LayoutInflater layoutInflater;
    public StatusAdapter(Context context,List<StatusModel> statusList ){
        this.context = context;
        this.statusList = statusList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public StatusAdapter.StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.status_item, parent, false);
        return new StatusViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusAdapter.StatusViewHolder holder, int position) {
        StatusModel currentStatus = statusList.get(position);
        holder.OtherUserName.setText(currentStatus.getUsername());
        holder.Status.setText(currentStatus.getStatusText());
        holder.LikeCount.setText(String.valueOf(currentStatus.getLikeCount()));
        holder.CommentCount.setText(String.valueOf(currentStatus.getCommentCount()));
        holder.OtherUserAvatar.setImageResource(currentStatus.getAvatarResId());
        holder.StatusImage.setImageResource(currentStatus.getStatusImageResId());
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public static class StatusViewHolder extends RecyclerView.ViewHolder {
        ImageView OtherUserAvatar, StatusImage;
        ImageButton LikeButton, CommentButton;
        TextView LikeCount, CommentCount, Status, OtherUserName;
        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);
            this.OtherUserAvatar = (ImageView)itemView.findViewById(R.id.other_user_avatar);
            this.StatusImage = (ImageView)itemView.findViewById(R.id.status_image);
            this.LikeButton = (ImageButton) itemView.findViewById(R.id.status_like_btn);
            this.CommentButton = (ImageButton)itemView.findViewById(R.id.status_comment_btn);
            this.LikeCount = (TextView)itemView.findViewById(R.id.status_like_count);
            this.CommentCount = (TextView)itemView.findViewById(R.id.status_comment_count);
            this.Status = (TextView)itemView.findViewById(R.id.status);
            this.OtherUserName = (TextView)itemView.findViewById(R.id.other_username);

        }
    }
}
