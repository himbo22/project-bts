package cdio.desert_eagle.project_bts.repository.auth;

import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import retrofit2.Response;

public interface AuthRepository {
    void login(String username, String password, AuthResultListener listener);

    interface AuthResultListener {
        void onSuccess(Response<ResponseObject> response);

        void onFailure(Throwable t);
    }
}
