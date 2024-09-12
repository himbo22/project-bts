package cdio.desert_eagle.project_bts.model.request;

public class CommentRequest {
    private String content;
    private String createdAt;
    private Long author;
    private Long post_id;

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

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public CommentRequest(String content, String createdAt, Long author, Long post_id) {
        this.content = content;
        this.createdAt = createdAt;
        this.author = author;
        this.post_id = post_id;
    }

    public CommentRequest() {
    }
}
