package cdio.desert_eagle.project_bts.model.request;

public class UserMessage {
    Long userId;
    String username;
    String avatar;
    String sentAt;
    String message;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserMessage(Long userId, String username, String avatar, String sentAt, String message) {
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
        this.sentAt = sentAt;
        this.message = message;
    }
}
