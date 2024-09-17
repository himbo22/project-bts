package cdio.desert_eagle.project_bts.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.File;

import cdio.desert_eagle.project_bts.data.SharedPref;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.User;
import cdio.desert_eagle.project_bts.repository.profile.ProfileRepository;
import cdio.desert_eagle.project_bts.repository.profile.ProfileRepositoryImpl;
import cdio.desert_eagle.project_bts.utils.RealPathUtil;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpdateProfileViewModel extends AndroidViewModel {
    private final ProfileRepository profileRepository;
    private final Application application;
    private final SharedPref sharedPref;
    public MutableLiveData<User> userMutableLiveData;
    public MutableLiveData<String> errorLiveData;

    public UpdateProfileViewModel(@NonNull Application application) {
        super(application);
        this.profileRepository = new ProfileRepositoryImpl();
        this.application = application;
        this.sharedPref = new SharedPref(application);
        userMutableLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }

    public void updateProfile(String username, String bio, Uri avatar) {
        MultipartBody.Part imageBody = null;
        if (avatar != null) {
            String imageRealPath = RealPathUtil.getRealPath(application, avatar);
            File imageFile = new File(imageRealPath);
            RequestBody requestFile = RequestBody.create(imageFile, MultipartBody.FORM);
            imageBody = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);
        }

        RequestBody requestUsername = RequestBody.create(username, MultipartBody.FORM);
        RequestBody requestBio = RequestBody.create(bio, MultipartBody.FORM);
        RequestBody requestUserId = RequestBody.create(String.valueOf(sharedPref.getLongData("userId")), MultipartBody.FORM);

        profileRepository.updateProfile(requestUserId, requestUsername, requestBio, imageBody, new ProfileRepository.ProfileResultListener<ResponseObject<User>>() {
            @Override
            public void onSuccess(ResponseObject<User> response) {
                if (!response.getStatus().equals("200")) {
                    errorLiveData.postValue(response.getMessage());
                } else {
                    userMutableLiveData.postValue(response.getData());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }
}
