package cdio.desert_eagle.project_bts.repository.post;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import cdio.desert_eagle.project_bts.data.remote.ApiService;
import cdio.desert_eagle.project_bts.data.remote.RetrofitClient;
import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.Post;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepositoryImpl implements PostRepository {

    private final ApiService apiService;

    public PostRepositoryImpl() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }


    @Override
    public void getALlPosts(int page, int size, PostResultListener<ResponseObject<PageResponse<Post>>> listener) {
        apiService.getAllPosts(page, size).enqueue(new Callback<ResponseObject<PageResponse<Post>>>() {
            @Override
            public void onResponse(Call<ResponseObject<PageResponse<Post>>> call, Response<ResponseObject<PageResponse<Post>>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseObject<PageResponse<Post>>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void createPost(RequestBody userId, RequestBody caption, RequestBody createdAt, MultipartBody.Part content, PostResultListener<ResponseObject<Post>> listener) {
        apiService.createPost(userId, caption, createdAt, content).enqueue(new Callback<ResponseObject<Post>>() {
            @Override
            public void onResponse(Call<ResponseObject<Post>> call, Response<ResponseObject<Post>> response) {
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
            public void onFailure(Call<ResponseObject<Post>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
