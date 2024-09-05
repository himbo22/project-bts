package cdio.desert_eagle.project_bts.repository.auth;

import cdio.desert_eagle.project_bts.api.ApiService;
import cdio.desert_eagle.project_bts.api.RetrofitClient;
import cdio.desert_eagle.project_bts.model.request.LoginRequest;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepositoryImpl implements AuthRepository {
    private final ApiService apiService;

    public AuthRepositoryImpl() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void login(String username, String password, AuthResultListener listener) {
        LoginRequest loginRequest = new LoginRequest(username, password);
        apiService.login(loginRequest).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                listener.onSuccess(response);
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
