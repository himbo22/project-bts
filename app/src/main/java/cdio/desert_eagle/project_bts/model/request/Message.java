package cdio.desert_eagle.project_bts.model.request;

public class Message {
    String sentAt;
    String message;

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

    public Message(String sentAt, String message) {
        this.sentAt = sentAt;
        this.message = message;
    }
}
