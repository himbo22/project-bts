package cdio.desert_eagle.project_bts.repository.post;

import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.Post;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.listener.BaseResult;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface PostRepository {
    void getALlPosts(int page, int size, PostResultListener<ResponseObject<PageResponse<Post>>> listener);

    void createPost(
            RequestBody userId,
            RequestBody caption,
            RequestBody createdAt,
            MultipartBody.Part content,
            PostResultListener<ResponseObject<Post>> listener);

    interface PostResultListener<T> extends BaseResult<T> {
    }
}