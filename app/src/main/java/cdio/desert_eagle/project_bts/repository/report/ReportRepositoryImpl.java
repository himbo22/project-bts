package cdio.desert_eagle.project_bts.repository.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import cdio.desert_eagle.project_bts.data.remote.ApiService;
import cdio.desert_eagle.project_bts.data.remote.RetrofitClient;
import cdio.desert_eagle.project_bts.model.response.Report;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportRepositoryImpl implements ReportRepository {

    private final ApiService apiService;

    public ReportRepositoryImpl() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void reportPost(Long userId, Long postId, String reason, ReportResultRepository<ResponseObject<Report>> listener) {
        apiService.reportPost(userId, postId, reason).enqueue(new Callback<ResponseObject<Report>>() {
            @Override
            public void onResponse(Call<ResponseObject<Report>> call, Response<ResponseObject<Report>> response) {
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
            public void onFailure(Call<ResponseObject<Report>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
