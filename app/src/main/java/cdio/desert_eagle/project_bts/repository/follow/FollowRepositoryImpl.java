package cdio.desert_eagle.project_bts.repository.follow;

import cdio.desert_eagle.project_bts.data.remote.ApiService;
import cdio.desert_eagle.project_bts.data.remote.RetrofitClient;
import cdio.desert_eagle.project_bts.model.response.Follower;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowRepositoryImpl implements FollowRepository {

    private final ApiService apiService;

    public FollowRepositoryImpl() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void followUser(long userId, long followId, FollowResult<ResponseObject<Follower>> listener) {
        apiService.followUser(userId, followId).enqueue(new Callback<ResponseObject<Follower>>() {
            @Override
            public void onResponse(Call<ResponseObject<Follower>> call, Response<ResponseObject<Follower>> response) {
                if (response.code() == 200) {
                    listener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<Follower>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void unFollowUser(long userId, long followId, FollowResult<ResponseObject<String>> listener) {
        apiService.unFollowUser(userId, followId).enqueue(new Callback<ResponseObject<String>>() {
            @Override
            public void onResponse(Call<ResponseObject<String>> call, Response<ResponseObject<String>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseObject<String>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void getFollowUser(long userId, long followId, FollowResult<ResponseObject<Boolean>> listener) {
        apiService.getFollowUser(userId, followId).enqueue(new Callback<ResponseObject<Boolean>>() {
            @Override
            public void onResponse(Call<ResponseObject<Boolean>> call, Response<ResponseObject<Boolean>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseObject<Boolean>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
