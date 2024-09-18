package cdio.desert_eagle.project_bts.model.request;

public class UserMessage {
    Long userId;
    String username;
    String avatar;
    String lastMessage;

    public String getLastMessage() {
        return lastMessage;
    }

    public UserMessage() {
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public UserMessage(Long userId, String username, String avatar, String lastMessage) {
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
        this.lastMessage = lastMessage;
    }

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


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserMessage(Long userId, String username, String avatar) {
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
    }
}
