package cdio.desert_eagle.project_bts.repository.post;

import cdio.desert_eagle.project_bts.listener.BaseResult;
import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.Post;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface PostRepository {
    void getALlPosts(int page, int size, PostResultListener<ResponseObject<PageResponse<UserPosts>>> listener);

    void createPost(
            RequestBody userId,
            RequestBody caption,
            RequestBody createdAt,
            MultipartBody.Part content,
            PostResultListener<ResponseObject<Post>> listener);

    void deletePost(Long postId, PostResultListener<ResponseObject<String>> listener);

    interface PostResultListener<T> extends BaseResult<T> {
    }
}