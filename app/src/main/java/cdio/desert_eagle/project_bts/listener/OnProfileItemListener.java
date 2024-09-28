package cdio.desert_eagle.project_bts.listener;

public interface OnProfileItemListener {
    void option(Long postId);

    void comment(Long postId);

    void user(Long userId);
}
