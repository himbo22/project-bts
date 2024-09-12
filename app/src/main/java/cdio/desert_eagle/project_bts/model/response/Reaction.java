package cdio.desert_eagle.project_bts.model.response;

public class Reaction {
    private Long id;
    private Post post;
    private User author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Reaction(Long id, Post post, User author) {
        this.id = id;
        this.post = post;
        this.author = author;
    }

    public Reaction() {
    }
}
