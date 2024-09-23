package cdio.desert_eagle.project_bts.repository.auth;

import cdio.desert_eagle.project_bts.listener.BaseResult;
import cdio.desert_eagle.project_bts.model.request.ResetPasswordRequest;
import cdio.desert_eagle.project_bts.model.response.ForgotPassword;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.User;
import cdio.desert_eagle.project_bts.model.response.VerifyingResponse;
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

    void resetPassword(String email, ResetPasswordRequest request, AuthResultListener<ResponseObject<User>> listener);

    void verifyingEmail(String email, AuthResultListener<VerifyingResponse> listener);

    void verifyingOtp(Integer otp, String email, AuthResultListener<VerifyingResponse> listener);

    interface AuthResultListener<T> extends BaseResult<T> {
    }
}
