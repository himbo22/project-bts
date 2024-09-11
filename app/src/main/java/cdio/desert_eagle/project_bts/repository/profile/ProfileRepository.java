package cdio.desert_eagle.project_bts.repository.profile;

import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import cdio.desert_eagle.project_bts.repository.BaseResult;

public interface ProfileRepository {
    void getAllUserPosts(Long id, int page, int size, ProfileResultListener listener);

    interface ProfileResultListener extends BaseResult<PageResponse<UserPosts>> {
    }
}
