package cdio.desert_eagle.project_bts.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cdio.desert_eagle.project_bts.data.SharedPref;
import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.Reaction;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import cdio.desert_eagle.project_bts.model.response.UserResponse;
import cdio.desert_eagle.project_bts.repository.BaseResult;
import cdio.desert_eagle.project_bts.repository.profile.ProfileRepository;
import cdio.desert_eagle.project_bts.repository.profile.ProfileRepositoryImpl;
import cdio.desert_eagle.project_bts.repository.reaction.ReactionRepository;
import cdio.desert_eagle.project_bts.repository.reaction.ReactionRepositoryImpl;

public class ProfileViewModel extends Application {
    private final ProfileRepository profileRepository;
    private final ReactionRepository reactionRepository;
    private final SharedPref sharedPref;
    public MutableLiveData<List<UserPosts>> allPosts;
    public MutableLiveData<Boolean> existedReaction;
    public MutableLiveData<UserResponse> userResponseMutableLiveData;
    public MutableLiveData<String> errorLiveData;
    public Integer pages = 0;
    public Long userId;
    private final int size = 30;


    public ProfileViewModel(@NonNull Application application) {
        this.profileRepository = new ProfileRepositoryImpl();
        this.reactionRepository = new ReactionRepositoryImpl();
        this.sharedPref = new SharedPref(application);
        existedReaction = new MutableLiveData<>();
        allPosts = new MutableLiveData<>();
        userResponseMutableLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
        userId = sharedPref.getLongData("userId");
    }


    public void logOut() {
        sharedPref.setStringData("loggedIn", "no");
    }

    public void getUserInformation() {
        profileRepository.getUserById(userId, new ProfileRepository.ProfileResultListener<ResponseObject<UserResponse>>() {
            @Override
            public void onSuccess(ResponseObject<UserResponse> response) {
                if (response.getData() != null) {
                    userResponseMutableLiveData.postValue(response.getData());
                } else {
                    errorLiveData.postValue(response.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void addReaction(Long user_id, Long post_id) {
        reactionRepository.addReaction(user_id, post_id, new ReactionRepository.ReactionResultListener<ResponseObject<Reaction>>() {
            @Override
            public void onSuccess(ResponseObject<Reaction> response) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void deleteReaction(Long user_id, Long post_id) {
        reactionRepository.deleteReaction(user_id, post_id, new ReactionRepository.ReactionResultListener<ResponseObject<String>>() {
            @Override
            public void onSuccess(ResponseObject<String> response) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void reactionExisted(Long user_id, Long post_id, BaseResult<Boolean> listener) {
        reactionRepository.reactionExisted(user_id, post_id, new ReactionRepository.ReactionResultListener<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                existedReaction.postValue(response);
                listener.onSuccess(response);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    public void getAllUserPosts() {
        profileRepository.getAllUserPosts(userId, pages, size, new ProfileRepository.ProfileResultListener<PageResponse<UserPosts>>() {
            @Override
            public void onSuccess(PageResponse<UserPosts> response) {
                allPosts.postValue(response.content);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void loadMoreUserPosts() {
        pages++;
        profileRepository.getAllUserPosts(userId, pages, size, new ProfileRepository.ProfileResultListener<PageResponse<UserPosts>>() {
            @Override
            public void onSuccess(PageResponse<UserPosts> response) {
                Log.d("hoangdeptrai", "onSuccess: " + response.getContent());
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
