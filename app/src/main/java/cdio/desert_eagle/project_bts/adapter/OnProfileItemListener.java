package cdio.desert_eagle.project_bts.adapter;

public interface OnProfileItemListener {
    void option();

    void like(long userId, long postId, int position);

    void liked(long userId, long postId, int position);

    void comment(Long postId);
}
