package cdio.desert_eagle.project_bts.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import cdio.desert_eagle.project_bts.repository.post.PostRepository;
import cdio.desert_eagle.project_bts.repository.post.PostRepositoryImpl;

public class HomeViewModel extends ViewModel {

    private final PostRepository postRepository;
    private int pages = 0;
    private final int size = 50;

    public MutableLiveData<List<UserPosts>> allPostsLiveData;
    public MutableLiveData<String> errorLiveData;

    public HomeViewModel() {
        postRepository = new PostRepositoryImpl();
        allPostsLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }


    public void getAllPosts() {
        postRepository.getALlPosts(pages, size, new PostRepository.PostResultListener<ResponseObject<PageResponse<UserPosts>>>() {
            @Override
            public void onSuccess(ResponseObject<PageResponse<UserPosts>> response) {
                allPostsLiveData.postValue(response.getData().getContent());
            }

            @Override
            public void onFailure(Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void loadMore() {
        pages++;
        postRepository.getALlPosts(pages, size, new PostRepository.PostResultListener<ResponseObject<PageResponse<UserPosts>>>() {
            @Override
            public void onSuccess(ResponseObject<PageResponse<UserPosts>> response) {
                if (response.getData().getContent().isEmpty()) {
                    pages--;
                } else {
                    allPostsLiveData.postValue(response.getData().getContent());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

}
