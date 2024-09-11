package cdio.desert_eagle.project_bts.model.response;

import androidx.annotation.NonNull;

public class Post {
    public Long id;
    public String caption;
    public String content;
    public String createdAt;
    public User author;

    @NonNull
    @Override
    public String toString() {
        return getId() + " / " + getCaption() + " / " + getContent() + " / " + getCreatedAt() + " / " + getAuthor().getId();
    }


    public Post(Long id, String caption, String content, String createdAt, User author) {
        this.id = id;
        this.caption = caption;
        this.content = content;
        this.createdAt = createdAt;
        this.author = author;
    }

    public Post() {
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
