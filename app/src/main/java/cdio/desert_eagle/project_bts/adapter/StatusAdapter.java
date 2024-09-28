package cdio.desert_eagle.project_bts.adapter;

import static cdio.desert_eagle.project_bts.constant.ConstantList.BASE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import cdio.desert_eagle.project_bts.R;
import cdio.desert_eagle.project_bts.listener.BaseResult;
import cdio.desert_eagle.project_bts.listener.OnProfileItemListener;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import cdio.desert_eagle.project_bts.viewmodel.ProfileViewModel;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {
    public List<UserPosts> statusList;
    public Context context;
    public LayoutInflater layoutInflater;
    private final ProfileViewModel profileViewModel;
    private final OnProfileItemListener listener;

    public StatusAdapter(Context context, ProfileViewModel profileViewModel, OnProfileItemListener listener) {
        this.context = context;
        this.profileViewModel = profileViewModel;
        this.statusList = new ArrayList<>();
        this.listener = listener;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public StatusAdapter.StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.status_item, parent, false);
        return new StatusViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusAdapter.StatusViewHolder holder, int position) {
        UserPosts currentStatus = statusList.get(position);


        profileViewModel.reactionExisted(currentStatus.getAuthor(), currentStatus.getId(), new BaseResult<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                if (response) {
                    holder.LikeButton.setImageResource(R.drawable.liked);
                } else {
                    holder.LikeButton.setImageResource(R.drawable.like);
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            OffsetDateTime odt = OffsetDateTime.parse(currentStatus.getCreatedAt());
            Duration difference = Duration.between(odt, OffsetDateTime.now(ZoneOffset.UTC));
            if (difference.toHours() < 1) {
                holder.tvElapsedTime.setText(difference.toMinutes() + "m ago");
            } else if (difference.toHours() < 24) {
                holder.tvElapsedTime.setText(difference.toHours() + "h ago");
            } else {
                holder.tvElapsedTime.setText(difference.toDays() + "d ago");
            }
        }

        holder.LikeButton.setOnClickListener(v -> {
            profileViewModel.reactionExisted(currentStatus.getAuthor(), currentStatus.getId(), new BaseResult<Boolean>() {
                @Override
                public void onSuccess(Boolean response) {
                    Long currentLiked = currentStatus.getLiked();
                    if (response) {
                        profileViewModel.deleteReaction(currentStatus.getAuthor(), currentStatus.getId());
                        holder.LikeButton.setImageResource(R.drawable.like);
                        currentLiked--;
                        currentStatus.setLiked(currentLiked);
                        holder.LikeCount.setText(currentLiked.toString());
                    } else {
                        profileViewModel.addReaction(currentStatus.getAuthor(), currentStatus.getId());
                        holder.LikeButton.setImageResource(R.drawable.liked);
                        currentLiked++;
                        currentStatus.setLiked(currentLiked);
                        holder.LikeCount.setText(currentLiked.toString());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                }
            });
        });

        holder.OtherUserName.setText(currentStatus.getAuthorName());
        holder.Status.setText(currentStatus.getCaption());
        holder.LikeCount.setText(String.valueOf(currentStatus.getLiked()));
        holder.CommentCount.setText(String.valueOf(currentStatus.getCommented()));

        Glide.with(holder.OtherUserAvatar.getContext())
                .load(BASE_URL + "/api/images/" + currentStatus.getAuthorAvatar())
                .into(holder.OtherUserAvatar);

        Glide.with(holder.StatusImage.getContext())
                .load(BASE_URL + "/api/images/" + currentStatus.getContent())
                .into(holder.StatusImage);

        holder.CommentButton.setOnClickListener(v -> {
            listener.comment(currentStatus.getId());
        });

        holder.option_btn.setOnClickListener(v -> {
            listener.option(currentStatus.getId());
        });

        holder.OtherUserAvatar.setOnClickListener(v -> {
            listener.user(currentStatus.getAuthor());
        });
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public static class StatusViewHolder extends RecyclerView.ViewHolder {
        ImageView OtherUserAvatar, StatusImage, option_btn;
        ImageButton LikeButton, CommentButton;
        TextView LikeCount, CommentCount, Status, OtherUserName, tvElapsedTime;

        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);
            this.OtherUserAvatar = (ImageView) itemView.findViewById(R.id.other_user_avatar);
            this.StatusImage = (ImageView) itemView.findViewById(R.id.status_image);
            this.LikeButton = (ImageButton) itemView.findViewById(R.id.status_like_btn);
            this.CommentButton = (ImageButton) itemView.findViewById(R.id.status_comment_btn);
            this.LikeCount = (TextView) itemView.findViewById(R.id.status_like_count);
            this.CommentCount = (TextView) itemView.findViewById(R.id.status_comment_count);
            this.Status = (TextView) itemView.findViewById(R.id.status);
            this.OtherUserName = (TextView) itemView.findViewById(R.id.other_username);
            this.tvElapsedTime = (TextView) itemView.findViewById(R.id.tvElapsedTime);
            this.option_btn = itemView.findViewById(R.id.option_btn);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<UserPosts> list) {
        this.statusList = list;
        notifyDataSetChanged();
    }
}
