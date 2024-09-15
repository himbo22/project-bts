package cdio.desert_eagle.project_bts.repository.auth;

import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.User;
import cdio.desert_eagle.project_bts.repository.BaseResult;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface AuthRepository {
    void login(String username, String password, AuthResultListener<ResponseObject<User>> listener);

    void register(
            RequestBody username,
            RequestBody password,
            RequestBody email,
            RequestBody bio,
            MultipartBody.Part image, AuthResultListener<ResponseObject<User>> listener);

    interface AuthResultListener<T> extends BaseResult<T> {
    }
}
