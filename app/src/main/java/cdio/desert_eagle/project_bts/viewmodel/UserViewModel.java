package cdio.desert_eagle.project_bts.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cdio.desert_eagle.project_bts.data.local.SharedPref;
import cdio.desert_eagle.project_bts.model.response.Follower;
import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import cdio.desert_eagle.project_bts.model.response.UserResponse;
import cdio.desert_eagle.project_bts.repository.follow.FollowRepository;
import cdio.desert_eagle.project_bts.repository.follow.FollowRepositoryImpl;
import cdio.desert_eagle.project_bts.repository.profile.ProfileRepository;
import cdio.desert_eagle.project_bts.repository.profile.ProfileRepositoryImpl;
import kotlin.Pair;

public class UserViewModel extends AndroidViewModel {

    private final ProfileRepository profileRepository;
    private final FollowRepository followRepository;
    public MutableLiveData<List<UserPosts>> allPosts;
    public MutableLiveData<UserResponse> userResponseMutableLiveData;
    public MutableLiveData<String> errorLiveData;
    public MutableLiveData<Pair<Boolean, Integer>> existedReaction;
    public MutableLiveData<Boolean> getFollowUserLiveData;
    public MutableLiveData<String> unFollowUserLoveData;
    public MutableLiveData<Follower> followUserLiveData;
    private Integer pages = 0;
    private final int size = 30;
    private final Long userId;

    public UserViewModel(Application application) {
        super(application);
        this.profileRepository = new ProfileRepositoryImpl();
        this.followRepository = new FollowRepositoryImpl();
        SharedPref sharedPref = new SharedPref(application);
        existedReaction = new MutableLiveData<>();
        userResponseMutableLiveData = new MutableLiveData<>();
        allPosts = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
        userId = sharedPref.getLongData("userId");
        getFollowUserLiveData = new MutableLiveData<>();
        unFollowUserLoveData = new MutableLiveData<>();
        followUserLiveData = new MutableLiveData<>();
    }

    public void followUser(long followId) {
        followRepository.followUser(followId, userId, new FollowRepository.FollowResult<ResponseObject<Follower>>() {
            @Override
            public void onSuccess(ResponseObject<Follower> response) {
                followUserLiveData.postValue(response.getData());
            }

            @Override
            public void onFailure(Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void unFollowUser(long followId) {
        followRepository.unFollowUser(followId, userId, new FollowRepository.FollowResult<ResponseObject<String>>() {
            @Override
            public void onSuccess(ResponseObject<String> response) {
                unFollowUserLoveData.postValue(response.getData());
            }

            @Override
            public void onFailure(Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void getFollowUser(long followId) {
        followRepository.getFollowUser(followId, userId, new FollowRepository.FollowResult<ResponseObject<Boolean>>() {
            @Override
            public void onSuccess(ResponseObject<Boolean> response) {
                getFollowUserLiveData.postValue(response.getData());
            }

            @Override
            public void onFailure(Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
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
