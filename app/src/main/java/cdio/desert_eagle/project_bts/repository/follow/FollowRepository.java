package cdio.desert_eagle.project_bts.repository.follow;

import cdio.desert_eagle.project_bts.listener.BaseResult;
import cdio.desert_eagle.project_bts.model.response.Follower;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;

public interface FollowRepository {

    void followUser(long userId, long followId, FollowResult<ResponseObject<Follower>> listener);

    void unFollowUser(long userId, long followId, FollowResult<ResponseObject<String>> listener);

    void getFollowUser(long userId, long followId, FollowResult<ResponseObject<Boolean>> listener);

    interface FollowResult<T> extends BaseResult<T> {
    }
}
