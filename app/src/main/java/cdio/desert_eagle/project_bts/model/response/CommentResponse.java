package cdio.desert_eagle.project_bts.model.response;

public class CommentResponse {
    private long id;
    private String avatar;
    private String username;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommentResponse(long id, String avatar, String username, String content) {
        this.id = id;
        this.avatar = avatar;
        this.username = username;
        this.content = content;
    }

    public CommentResponse() {
    }
}
