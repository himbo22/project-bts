package cdio.desert_eagle.project_bts.repository.profile;

import cdio.desert_eagle.project_bts.api.ApiService;
import cdio.desert_eagle.project_bts.api.RetrofitClient;
import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepositoryImpl implements ProfileRepository {

    private final ApiService apiService;

    public ProfileRepositoryImpl() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void getAllUserPosts(Long id, int page, int size, ProfileResultListener listener) {
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

}
