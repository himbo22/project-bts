package cdio.desert_eagle.project_bts.repository.profile;

import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.User;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import cdio.desert_eagle.project_bts.model.response.UserResponse;
import cdio.desert_eagle.project_bts.listener.BaseResult;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface ProfileRepository {
    void getAllUserPosts(Long id, int page, int size, ProfileResultListener<PageResponse<UserPosts>> listener);

    void getUserById(
            Long userId,
            ProfileResultListener<ResponseObject<UserResponse>> listener
    );

    void updateProfile(
            RequestBody userId,
            RequestBody username,
            RequestBody bio,
            MultipartBody.Part image,
            ProfileResultListener<ResponseObject<User>> listener
    );

    interface ProfileResultListener<T> extends BaseResult<T> {
    }
}
