package cdio.desert_eagle.project_bts.api;

import cdio.desert_eagle.project_bts.model.request.LoginRequest;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/users/login")
    Call<ResponseObject> login(@Body LoginRequest loginRequest);
}
