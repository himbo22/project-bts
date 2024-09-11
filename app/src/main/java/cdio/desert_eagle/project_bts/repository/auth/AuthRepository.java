package cdio.desert_eagle.project_bts.repository.auth;

import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.repository.BaseResult;
import retrofit2.Response;

public interface AuthRepository {
    void login(String username, String password, AuthResultListener listener);

    interface AuthResultListener extends BaseResult<Response<ResponseObject>> {
    }

    ;
}
