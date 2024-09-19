package cdio.desert_eagle.project_bts.model.response;

public class Report {
    private Long id;
    private String reason;
    private User userId;
    private Post postId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
    }

    public Report(Long id, String reason, User userId, Post postId) {
        this.id = id;
        this.reason = reason;
        this.userId = userId;
        this.postId = postId;
    }
}
