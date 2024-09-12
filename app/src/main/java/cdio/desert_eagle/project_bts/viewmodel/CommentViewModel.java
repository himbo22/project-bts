package cdio.desert_eagle.project_bts.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cdio.desert_eagle.project_bts.model.request.CommentRequest;
import cdio.desert_eagle.project_bts.model.response.Comment;
import cdio.desert_eagle.project_bts.model.response.CommentResponse;
import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.repository.comment.CommentRepository;
import cdio.desert_eagle.project_bts.repository.comment.CommentRepositoryImpl;

public class CommentViewModel extends ViewModel {

    private final CommentRepository commentRepository;
    public MutableLiveData<List<CommentResponse>> commentResponseLiveData;
    public MutableLiveData<Comment> commentMutableLiveData;
    public Integer pages = 0;

    public CommentViewModel() {
        this.commentRepository = new CommentRepositoryImpl();
        commentResponseLiveData = new MutableLiveData<>();
        commentMutableLiveData = new MutableLiveData<>();
    }

    public void getPostComments(Long postId, int page, int size) {
        commentRepository.getPostComments(postId, page, size, new CommentRepository.CommentResultListener<ResponseObject<PageResponse<CommentResponse>>>() {
            @Override
            public void onSuccess(ResponseObject<PageResponse<CommentResponse>> response) {
                commentResponseLiveData.postValue(response.getData().getContent());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    public void loadMoreComments(Long postId, int size) {
        pages++;
        commentRepository.getPostComments(postId, pages, size, new CommentRepository.CommentResultListener<ResponseObject<PageResponse<CommentResponse>>>() {
            @Override
            public void onSuccess(ResponseObject<PageResponse<CommentResponse>> response) {
                if (response.getData().getContent() == null) {
                    pages--;
                } else {
                    commentResponseLiveData.postValue(response.getData().getContent());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void addComment(CommentRequest commentRequest) {
        commentRepository.addComment(commentRequest, new CommentRepository.CommentResultListener<ResponseObject<Comment>>() {
            @Override
            public void onSuccess(ResponseObject<Comment> response) {
                if (response.getData() != null) {
                    commentMutableLiveData.postValue(response.getData());
                } else {
                    commentMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
