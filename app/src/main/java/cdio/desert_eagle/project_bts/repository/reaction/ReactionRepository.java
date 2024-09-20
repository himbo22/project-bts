package cdio.desert_eagle.project_bts.repository.reaction;

import cdio.desert_eagle.project_bts.model.response.Reaction;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.listener.BaseResult;

public interface ReactionRepository {
    void deleteReaction(Long user_id, Long post_id, ReactionResultListener<ResponseObject<String>> listener);

    void reactionExisted(Long user_id, Long post_id, ReactionResultListener<Boolean> listener);

    void addReaction(Long user_id, Long post_id, ReactionResultListener<ResponseObject<Reaction>> listener);

    interface ReactionResultListener<T> extends BaseResult<T> {
    }
}
