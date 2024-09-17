package cdio.desert_eagle.project_bts.repository.profile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import cdio.desert_eagle.project_bts.api.ApiService;
import cdio.desert_eagle.project_bts.api.RetrofitClient;
import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.User;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import cdio.desert_eagle.project_bts.model.response.UserResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepositoryImpl implements ProfileRepository {

    private final ApiService apiService;

    public ProfileRepositoryImpl() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }


    @Override
    public void getAllUserPosts(Long id, int page, int size, ProfileResultListener<PageResponse<UserPosts>> listener) {
        apiService.getAllUserPosts(id, page, size).enqueue(new Callback<ResponseObject<PageResponse<UserPosts>>>() {
            @Override
            public void onResponse(Call<ResponseObject<PageResponse<UserPosts>>> call, Response<ResponseObject<PageResponse<UserPosts>>> response) {
                assert response.body() != null;
                listener.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResponseObject<PageResponse<UserPosts>>> call, Throwable t) {
                listener.onFailure(t);

            }
        });
    }

    @Override
    public void getUserById(Long userId, ProfileResultListener<ResponseObject<UserResponse>> listener) {
        apiService.getUserById(userId).enqueue(new Callback<ResponseObject<UserResponse>>() {
            @Override
            public void onResponse(Call<ResponseObject<UserResponse>> call, Response<ResponseObject<UserResponse>> response) {
                if (response.code() != 200) {
                    Gson gson = new GsonBuilder().create();
                    ResponseObject mError;
                    try {
                        mError = gson.fromJson(response.errorBody().string(), ResponseObject.class);
                        listener.onSuccess(mError);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    listener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<UserResponse>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void updateProfile(RequestBody userId, RequestBody username, RequestBody bio, MultipartBody.Part image, ProfileResultListener<ResponseObject<User>> listener) {
        apiService.updateProfile(userId, username, bio, image).enqueue(new Callback<ResponseObject<User>>() {
            @Override
            public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                if (response.code() != 200) {
                    Gson gson = new GsonBuilder().create();
                    ResponseObject mError;
                    try {
                        mError = gson.fromJson(response.errorBody().string(), ResponseObject.class);
                        listener.onSuccess(mError);
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
