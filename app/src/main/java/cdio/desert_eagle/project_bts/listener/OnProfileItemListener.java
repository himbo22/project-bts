package cdio.desert_eagle.project_bts.listener;

public interface OnProfileItemListener {
    void option(Long postId);

    void like(long userId, long postId, int position);

    void liked(long userId, long postId, int position);

    void comment(Long postId);
}
