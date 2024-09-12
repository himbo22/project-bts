package cdio.desert_eagle.project_bts.repository.comment;

import cdio.desert_eagle.project_bts.api.ApiService;
import cdio.desert_eagle.project_bts.api.RetrofitClient;
import cdio.desert_eagle.project_bts.model.request.CommentRequest;
import cdio.desert_eagle.project_bts.model.response.Comment;
import cdio.desert_eagle.project_bts.model.response.CommentResponse;
import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentRepositoryImpl implements CommentRepository {

    private final ApiService apiService;

    public CommentRepositoryImpl() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }


    @Override
    public void getPostComments(Long id, int page, int size, CommentResultListener<ResponseObject<PageResponse<CommentResponse>>> listener) {
        apiService.getPostComments(id, page, size).enqueue(new Callback<ResponseObject<PageResponse<CommentResponse>>>() {
            @Override
            public void onResponse(Call<ResponseObject<PageResponse<CommentResponse>>> call, Response<ResponseObject<PageResponse<CommentResponse>>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseObject<PageResponse<CommentResponse>>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void addComment(CommentRequest commentRequest, CommentResultListener<ResponseObject<Comment>> listener) {
        apiService.addComment(commentRequest).enqueue(new Callback<ResponseObject<Comment>>() {
            @Override
            public void onResponse(Call<ResponseObject<Comment>> call, Response<ResponseObject<Comment>> response) {
                assert response.body() != null;
                System.out.println(response.body().getData().getContent());
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseObject<Comment>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
