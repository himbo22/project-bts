package cdio.desert_eagle.project_bts.repository.profile;

import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import cdio.desert_eagle.project_bts.model.response.UserResponse;
import cdio.desert_eagle.project_bts.repository.BaseResult;

public interface ProfileRepository {
    void getAllUserPosts(Long id, int page, int size, ProfileResultListener<PageResponse<UserPosts>> listener);

    void getUserById(Long userId, ProfileResultListener<ResponseObject<UserResponse>> listener);

    interface ProfileResultListener<T> extends BaseResult<T> {
    }
}
