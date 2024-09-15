package cdio.desert_eagle.project_bts.model.response;

import androidx.annotation.NonNull;

public class User {
    public Long id;
    public String username;
    public String password;
    public String email;
    public String avatar;
    public String bio;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public User(Long id, String username, String password, String email, String avatar, String bio) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.bio = bio;
    }

    @NonNull
    @Override
    public String toString() {
        return getId() + " / " + getAvatar() + " / " + getEmail() + " / " + getPassword() + " / " + getUsername();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
