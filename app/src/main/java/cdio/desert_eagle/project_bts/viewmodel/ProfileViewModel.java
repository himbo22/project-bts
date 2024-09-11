package cdio.desert_eagle.project_bts.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.Reaction;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import cdio.desert_eagle.project_bts.repository.BaseResult;
import cdio.desert_eagle.project_bts.repository.profile.ProfileRepository;
import cdio.desert_eagle.project_bts.repository.profile.ProfileRepositoryImpl;
import cdio.desert_eagle.project_bts.repository.reaction.ReactionRepository;
import cdio.desert_eagle.project_bts.repository.reaction.ReactionRepositoryImpl;

public class ProfileViewModel extends ViewModel {
    private final ProfileRepository profileRepository;
    private final ReactionRepository reactionRepository;
    public MutableLiveData<List<UserPosts>> allPosts;
    public Integer pages = 0;
    public MutableLiveData<Boolean> existedReaction;

    public void resetAll() {

    }

    public ProfileViewModel() {
        this.profileRepository = new ProfileRepositoryImpl();
        this.reactionRepository = new ReactionRepositoryImpl();
        existedReaction = new MutableLiveData<>();
        allPosts = new MutableLiveData<>();
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

    public void getAllUserPosts(Long id, int page, int size) {
        profileRepository.getAllUserPosts(id, page, size, new ProfileRepository.ProfileResultListener() {
            @Override
            public void onSuccess(PageResponse<UserPosts> response) {
                allPosts.postValue(response.content);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void loadMoreUserPosts(Long id, int size) {
        pages++;
        profileRepository.getAllUserPosts(id, pages, size, new ProfileRepository.ProfileResultListener() {
            @Override
            public void onSuccess(PageResponse<UserPosts> response) {
                if (response.getContent() == null) {
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
