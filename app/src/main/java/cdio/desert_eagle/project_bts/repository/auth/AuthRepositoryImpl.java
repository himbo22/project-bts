package cdio.desert_eagle.project_bts.repository.auth;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Objects;

import cdio.desert_eagle.project_bts.data.remote.ApiService;
import cdio.desert_eagle.project_bts.data.remote.RetrofitClient;
import cdio.desert_eagle.project_bts.model.request.LoginRequest;
import cdio.desert_eagle.project_bts.model.request.ResetPasswordRequest;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.User;
import cdio.desert_eagle.project_bts.model.response.VerifyingResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepositoryImpl implements AuthRepository {
    private final ApiService apiService;

    public AuthRepositoryImpl() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void login(String username, String password, AuthResultListener<ResponseObject<User>> listener) {
        apiService.login(new LoginRequest(username, password)).enqueue(new Callback<ResponseObject<User>>() {
            @Override
            public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                if (response.code() != 200) {
                    Gson gson = new GsonBuilder().create();
                    ResponseObject mError;
                    try {
                        mError = gson.fromJson(Objects.requireNonNull(response.errorBody()).string(), ResponseObject.class);
                        listener.onSuccess(mError);
                    } catch (NullPointerException | IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    listener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<User>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void register(RequestBody username, RequestBody password, RequestBody email, RequestBody bio, MultipartBody.Part image, AuthResultListener<ResponseObject<User>> listener) {
        apiService.register(username, password, email, bio, image).enqueue(new Callback<ResponseObject<User>>() {
            @Override
            public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                if (response.code() != 200) {
                    Gson gson = new GsonBuilder().create();
                    ResponseObject roError;
                    try {
                        roError = gson.fromJson(response.errorBody().string(), ResponseObject.class);
                        listener.onSuccess(roError);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    listener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<User>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void resetPassword(String email, ResetPasswordRequest request, AuthResultListener<ResponseObject<User>> listener) {
        apiService.resetPassword(email, request).enqueue(new Callback<ResponseObject<User>>() {
            @Override
            public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseObject<User>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void verifyingEmail(String email, AuthResultListener<VerifyingResponse> listener) {
        apiService.verifyingEmail(email).enqueue(new Callback<VerifyingResponse>() {
            @Override
            public void onResponse(Call<VerifyingResponse> call, Response<VerifyingResponse> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<VerifyingResponse> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void verifyingOtp(Integer otp, String email, AuthResultListener<VerifyingResponse> listener) {
        apiService.verifyingOtp(otp, email).enqueue(new Callback<VerifyingResponse>() {
            @Override
            public void onResponse(Call<VerifyingResponse> call, Response<VerifyingResponse> response) {
                Log.d("hoangdeptrai", "onSuccess: " + response.body());
                if (response.code() != 200) {
                    try {
                        ResponseObject mError = new GsonBuilder().create().fromJson(response.errorBody().string()
                                , ResponseObject.class);
                        listener.onSuccess(new VerifyingResponse(mError.getStatus()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    listener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<VerifyingResponse> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }


}
