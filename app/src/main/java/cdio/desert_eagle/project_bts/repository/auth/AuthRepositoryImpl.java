package cdio.desert_eagle.project_bts.repository.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Objects;

import cdio.desert_eagle.project_bts.data.remote.ApiService;
import cdio.desert_eagle.project_bts.data.remote.RetrofitClient;
import cdio.desert_eagle.project_bts.model.request.LoginRequest;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.User;
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


}
