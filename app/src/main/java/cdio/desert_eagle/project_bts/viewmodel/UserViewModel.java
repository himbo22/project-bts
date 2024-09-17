package cdio.desert_eagle.project_bts.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.Reaction;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import cdio.desert_eagle.project_bts.model.response.UserResponse;
import cdio.desert_eagle.project_bts.repository.profile.ProfileRepository;
import cdio.desert_eagle.project_bts.repository.profile.ProfileRepositoryImpl;
import cdio.desert_eagle.project_bts.repository.reaction.ReactionRepository;
import cdio.desert_eagle.project_bts.repository.reaction.ReactionRepositoryImpl;
import kotlin.Pair;

public class UserViewModel extends ViewModel {

    private final ProfileRepository profileRepository;
    private final ReactionRepository reactionRepository;
    public MutableLiveData<List<UserPosts>> allPosts;
    public MutableLiveData<UserResponse> userResponseMutableLiveData;
    public MutableLiveData<String> errorLiveData;
    public MutableLiveData<Pair<Boolean, Integer>> existedReaction;
    private Integer pages = 0;
    private final Integer size = 50;

    public UserViewModel() {
        this.profileRepository = new ProfileRepositoryImpl();
        this.reactionRepository = new ReactionRepositoryImpl();
        existedReaction = new MutableLiveData<>();
        userResponseMutableLiveData = new MutableLiveData<>();
        allPosts = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
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

    public void reactionExisted(Long user_id, Long post_id, Integer position) {
        reactionRepository.reactionExisted(user_id, post_id, new ReactionRepository.ReactionResultListener<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                existedReaction.postValue(new Pair<>(response, position));
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

}
