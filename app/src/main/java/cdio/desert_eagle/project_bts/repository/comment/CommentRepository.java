package cdio.desert_eagle.project_bts.repository.comment;

import cdio.desert_eagle.project_bts.model.request.CommentRequest;
import cdio.desert_eagle.project_bts.model.response.Comment;
import cdio.desert_eagle.project_bts.model.response.CommentResponse;
import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.listener.BaseResult;

public interface CommentRepository {
    void getPostComments(Long id, int page, int size, CommentResultListener<ResponseObject<PageResponse<CommentResponse>>> listener);

    void addComment(CommentRequest commentRequest, CommentResultListener<ResponseObject<Comment>> listener);

    interface CommentResultListener<T> extends BaseResult<T> {
    }
}
