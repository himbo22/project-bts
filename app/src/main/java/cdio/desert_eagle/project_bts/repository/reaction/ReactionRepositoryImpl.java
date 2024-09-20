package cdio.desert_eagle.project_bts.repository.reaction;

import cdio.desert_eagle.project_bts.data.remote.ApiService;
import cdio.desert_eagle.project_bts.data.remote.RetrofitClient;
import cdio.desert_eagle.project_bts.model.response.Reaction;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReactionRepositoryImpl implements ReactionRepository {

    private final ApiService apiService;

    public ReactionRepositoryImpl() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }


    @Override
    public void deleteReaction(Long user_id, Long post_id, ReactionResultListener<ResponseObject<String>> listener) {
        apiService.deleteReaction(user_id, post_id).enqueue(new Callback<ResponseObject<String>>() {
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
    public void reactionExisted(Long user_id, Long post_id, ReactionResultListener<Boolean> listener) {
        apiService.existedReaction(user_id, post_id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void addReaction(Long user_id, Long post_id, ReactionResultListener<ResponseObject<Reaction>> listener) {
        apiService.addReaction(user_id, post_id).enqueue(new Callback<ResponseObject<Reaction>>() {
            @Override
            public void onResponse(Call<ResponseObject<Reaction>> call, Response<ResponseObject<Reaction>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseObject<Reaction>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }


}
