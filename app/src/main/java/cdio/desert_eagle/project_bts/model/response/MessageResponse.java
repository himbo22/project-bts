package cdio.desert_eagle.project_bts.model.response;

public class MessageResponse {
    Long key;
    String value;

    public Long getKey() {
        return key;
    }

    public MessageResponse(Long key, String value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public MessageResponse() {
    }
}
