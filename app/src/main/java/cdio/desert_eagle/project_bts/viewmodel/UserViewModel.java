package cdio.desert_eagle.project_bts.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import cdio.desert_eagle.project_bts.model.response.UserResponse;
import cdio.desert_eagle.project_bts.repository.profile.ProfileRepository;
import cdio.desert_eagle.project_bts.repository.profile.ProfileRepositoryImpl;
import kotlin.Pair;

public class UserViewModel extends ViewModel {

    private final ProfileRepository profileRepository;
    public MutableLiveData<List<UserPosts>> allPosts;
    public MutableLiveData<UserResponse> userResponseMutableLiveData;
    public MutableLiveData<String> errorLiveData;
    public MutableLiveData<Pair<Boolean, Integer>> existedReaction;
    public MutableLiveData<List<Boolean>> likedPostsStatusLiveData;
    private Integer pages = 0;
    private final int size = 30;

    public UserViewModel() {
        this.profileRepository = new ProfileRepositoryImpl();
        existedReaction = new MutableLiveData<>();
        userResponseMutableLiveData = new MutableLiveData<>();
        allPosts = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
        likedPostsStatusLiveData = new MutableLiveData<>();
    }


    public void getAllUserPosts(Long id) {
        profileRepository.getAllUserPosts(id, pages, size, new ProfileRepository.ProfileResultListener<PageResponse<UserPosts>>() {
            @Override
            public void onSuccess(PageResponse<UserPosts> response) {
                allPosts.postValue(response.content);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void getUser(long userId) {
        profileRepository.getUserById(userId, new ProfileRepository.ProfileResultListener<ResponseObject<UserResponse>>() {
            @Override
            public void onSuccess(ResponseObject<UserResponse> response) {
                userResponseMutableLiveData.postValue(response.getData());
            }

            @Override
            public void onFailure(Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void loadMoreUserPosts(Long id) {
        pages++;
        profileRepository.getAllUserPosts(id, pages, size, new ProfileRepository.ProfileResultListener<PageResponse<UserPosts>>() {
            @Override
            public void onSuccess(PageResponse<UserPosts> response) {
                if (response.getContent().isEmpty()) {
                    pages--;
                } else {
                    allPosts.postValue(response.getContent());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
