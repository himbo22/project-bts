package cdio.desert_eagle.project_bts.model.response;

public class Follower {
    private long id;
    private User followedUser;
    private User followingUser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getFollowedUser() {
        return followedUser;
    }

    public void setFollowedUser(User followedUser) {
        this.followedUser = followedUser;
    }

    public User getFollowingUser() {
        return followingUser;
    }

    public void setFollowingUser(User followingUser) {
        this.followingUser = followingUser;
    }

    public Follower(long id, User followedUser, User followingUser) {
        this.id = id;
        this.followedUser = followedUser;
        this.followingUser = followingUser;
    }
}
