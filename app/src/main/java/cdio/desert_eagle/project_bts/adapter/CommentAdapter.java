package cdio.desert_eagle.project_bts.adapter;


import static cdio.desert_eagle.project_bts.constant.ConstantList.BASE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import cdio.desert_eagle.project_bts.R;
import cdio.desert_eagle.project_bts.model.response.CommentResponse;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<CommentResponse> comments;

    public CommentAdapter(Context context, List<CommentResponse> comments) {
        this.comments = comments;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_comment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CommentResponse comment = comments.get(position);
        holder.tvUsername.setText(comment.getUsername());
        holder.tvComment.setText(comment.getContent());
        Glide.with(holder.imgAvatar.getContext()).load(BASE_URL + "/api/images/" + comment.getAvatar()).into(holder.imgAvatar);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ShapeableImageView imgAvatar;
        public TextView tvUsername, tvComment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvUsername = itemView.findViewById(R.id.tvUsername);
        }
    }

    public void updateData(List<CommentResponse> newList) {
        comments.addAll(newList);
        notifyDataSetChanged();
    }

    public void addSingleData(CommentResponse comment) {
        comments.add(getItemCount(), comment);
        notifyDataSetChanged();
    }
}
