package cdio.desert_eagle.project_bts.model.response;

import androidx.annotation.NonNull;

public class UserPosts {
    private Long id;
    private String caption;
    private String content;
    private String createdAt;
    private Long author;
    private String authorAvatar;
    private String authorName;
    private Long liked;
    private Long commented;

    private transient boolean likedByUser;

    public boolean isLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    @NonNull
    @Override
    public String toString() {
        return getId() + " / " +
                getCaption() + " / " +
                getContent() + " / " +
                getCreatedAt() + " / " +
                getAuthor() + " / " +
                getAuthorAvatar() + " / " +
                getAuthorName() + " / " +
                getLiked() + " / " +
                getCommented();
    }


    public UserPosts(Long id, String caption, String content, String createdAt, Long author, String authorAvatar, String authorName, Long liked, Long commented) {
        this.id = id;
        this.caption = caption;
        this.content = content;
        this.createdAt = createdAt;
        this.author = author;
        this.authorAvatar = authorAvatar;
        this.authorName = authorName;
        this.liked = liked;
        this.commented = commented;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public Long getLiked() {
        return liked;
    }

    public void setLiked(Long liked) {
        this.liked = liked;
    }

    public Long getCommented() {
        return commented;
    }

    public void setCommented(Long commented) {
        this.commented = commented;
    }
}
