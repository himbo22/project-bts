package cdio.desert_eagle.project_bts.repository.search;

import java.util.Collections;
import java.util.List;

import cdio.desert_eagle.project_bts.data.remote.ApiService;
import cdio.desert_eagle.project_bts.data.remote.RetrofitClient;
import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepositoryImpl implements SearchRepository {

    private final ApiService apiService;

    public SearchRepositoryImpl() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void searchByUsername(String username, int page, int size, SearchResultListener<List<User>> listener) {
        apiService.searchUsersByUsername(username, page, size).enqueue(new Callback<ResponseObject<PageResponse<User>>>() {
            @Override
            public void onResponse(Call<ResponseObject<PageResponse<User>>> call, Response<ResponseObject<PageResponse<User>>> response) {
                listener.onSuccess(response.body() != null ? response.body().getData().getContent() : Collections.emptyList());
            }

            @Override
            public void onFailure(Call<ResponseObject<PageResponse<User>>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
