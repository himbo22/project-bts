package cdio.desert_eagle.project_bts.model.response;

public class Comment {
    private long id;
    private String content;
    private String createdAt;
    private Post post;
    private User author;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Comment(long id, String content, String createdAt, Post post, User author) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.post = post;
        this.author = author;
    }

    public Comment() {
    }
}
