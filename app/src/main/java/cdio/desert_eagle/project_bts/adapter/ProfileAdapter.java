package cdio.desert_eagle.project_bts.adapter;

import static cdio.desert_eagle.project_bts.constant.ConstantList.BASE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import cdio.desert_eagle.project_bts.R;
import cdio.desert_eagle.project_bts.databinding.ItemRecyclerviewProfileBinding;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import cdio.desert_eagle.project_bts.repository.BaseResult;
import cdio.desert_eagle.project_bts.viewmodel.ProfileViewModel;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {

    public List<UserPosts> userPosts;
    Context context;
    ProfileViewModel profileViewModel;
    OnProfileItemListener listener;

    public ProfileAdapter(Context context, ProfileViewModel profileViewModel, OnProfileItemListener listener) {
        this.userPosts = new ArrayList<>();
        this.context = context;
        this.profileViewModel = profileViewModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProfileAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the layout
        ItemRecyclerviewProfileBinding binding = ItemRecyclerviewProfileBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        LifecycleOwner lifecycleOwner = (LifecycleOwner) parent.getContext();
        return new MyViewHolder(binding, lifecycleOwner);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserPosts userPost = userPosts.get(position);
        profileViewModel.reactionExisted(userPost.getAuthor(), userPost.getId(), new BaseResult<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                if (response) {
                    holder.binding.imgReact.setImageResource(R.drawable.liked);
                } else {
                    holder.binding.imgReact.setImageResource(R.drawable.like);
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        Glide.with(holder.binding.imgAvatar.getContext()).load(BASE_URL + "/api/images/" + userPost.getAuthorAvatar()).into(holder.binding.imgAvatar);
        holder.binding.tvUsername.setText(userPost.getAuthorName());
        holder.binding.tvCaption.setText(userPost.getCaption());
        Glide.with(holder.binding.imgContent.getContext()).load(BASE_URL + "/api/images/" + userPost.getContent()).into(holder.binding.imgContent);
        holder.binding.tvReaction.setText(userPost.getLiked().toString());
        holder.binding.tvComment.setText(userPost.getCommented().toString());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            OffsetDateTime odt = OffsetDateTime.parse(userPost.getCreatedAt());
            Duration difference = Duration.between(odt, OffsetDateTime.now(ZoneOffset.UTC));
            if (difference.toHours() < 1) {
                holder.binding.tvElapsedTime.setText(difference.toMinutes() + "m ago");
            } else if (difference.toHours() < 24) {
                holder.binding.tvElapsedTime.setText(difference.toHours() + "h ago");
            } else {
                holder.binding.tvElapsedTime.setText(difference.toDays() + "d ago");
            }
        }

        holder.binding.imgOption.setOnClickListener(v -> {
//            listener.option();
        });


        holder.binding.imgReact.setOnClickListener(v -> {
            profileViewModel.reactionExisted(userPost.getAuthor(), userPost.getId(), new BaseResult<Boolean>() {
                @Override
                public void onSuccess(Boolean response) {
                    Long currentLiked = userPost.getLiked();
                    if (response) {
                        profileViewModel.deleteReaction(userPost.getAuthor(), userPost.getId());
                        holder.binding.imgReact.setImageResource(R.drawable.like);
                        currentLiked--;
                        userPost.setLiked(currentLiked);
                        holder.binding.tvReaction.setText(currentLiked.toString());
                    } else {
                        profileViewModel.addReaction(userPost.getAuthor(), userPost.getId());
                        holder.binding.imgReact.setImageResource(R.drawable.liked);
                        currentLiked++;
                        userPost.setLiked(currentLiked);
                        holder.binding.tvReaction.setText(currentLiked.toString());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                }
            });
//            listener.react(liked, userPost.getAuthor(), userPost.getId());
        });
        holder.binding.imgComment.setOnClickListener(v -> {
            listener.comment(userPost.getId());
        });
    }


    @Override
    public int getItemCount() {
        return userPosts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ItemRecyclerviewProfileBinding binding;
        public LifecycleOwner lifecycleOwner;

        public MyViewHolder(ItemRecyclerviewProfileBinding binding, LifecycleOwner lifecycleOwner) {
            super(binding.getRoot());
            this.binding = binding;
            this.lifecycleOwner = lifecycleOwner;
        }
    }

    public void updateData(List<UserPosts> newUserPosts) {
        userPosts.addAll(newUserPosts);
        notifyDataSetChanged();
    }

    public void resetData(List<UserPosts> newUserPosts) {
        userPosts.clear();
        userPosts.addAll(newUserPosts);
        notifyDataSetChanged();
    }

    public void updateItem(int pos, UserPosts userPosts) {
        this.userPosts.set(pos, userPosts);
        notifyDataSetChanged();
    }

}

