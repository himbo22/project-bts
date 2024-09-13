package cdio.desert_eagle.project_bts.model;

public class User {
    private String name;
    private int userIcon;
    private int messageIcon;

    public User(String name, int userIcon, int messageIcon) {
        this.name = name;
        this.userIcon = userIcon;
        this.messageIcon = messageIcon;
    }

    public String getName() {
        return name;
    }

    public int getUserIcon() {
        return userIcon;
    }

    public int getMessageIcon() {
        return messageIcon;
    }
}