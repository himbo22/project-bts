package cdio.desert_eagle.project_bts.model.request;

public class Message {
    Long userId;
    String sentAt;
    String message;

    private transient MESSAGE_TYPE type;

    public MESSAGE_TYPE getType() {
        return type;
    }

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public Message(Long userId, String sentAt, String message, MESSAGE_TYPE type) {
        this.userId = userId;
        this.sentAt = sentAt;
        this.message = message;
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setType(MESSAGE_TYPE type) {
        this.type = type;
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


}

