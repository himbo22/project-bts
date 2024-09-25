package cdio.desert_eagle.project_bts.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.File;

import cdio.desert_eagle.project_bts.data.local.SharedPref;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.User;
import cdio.desert_eagle.project_bts.repository.auth.AuthRepository;
import cdio.desert_eagle.project_bts.repository.auth.AuthRepositoryImpl;
import cdio.desert_eagle.project_bts.utils.RealPathUtil;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterViewModel extends AndroidViewModel {

    private final AuthRepository authRepository;
    private final SharedPref sharedPref;
    private final Application application;
    public MutableLiveData<User> registerLiveData;
    public MutableLiveData<String> errorLiveData;


    public RegisterViewModel(@NonNull Application application) {
        super(application);
        this.authRepository = new AuthRepositoryImpl();
        this.sharedPref = new SharedPref(application);
        this.application = application;
        registerLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }

    public void register(String username, String password, String email, String bio, Uri image) {
        String imageRealPath = RealPathUtil.getRealPath(application, image);
        File imageFile;
        if (imageRealPath != null) {
            imageFile = new File(imageRealPath);
        } else {
            errorLiveData.postValue("invalid image path");
            return;
        }

        RequestBody requestFile = RequestBody.create(imageFile, MultipartBody.FORM);
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);
        RequestBody usernameBody = RequestBody.create(username, MultipartBody.FORM);
        RequestBody passwordBody = RequestBody.create(password, MultipartBody.FORM);
        RequestBody emailBody = RequestBody.create(email, MultipartBody.FORM);
        RequestBody bioBody = RequestBody.create(bio, MultipartBody.FORM);

        authRepository.register(usernameBody, passwordBody, emailBody, bioBody, imageBody, new AuthRepository.AuthResultListener<ResponseObject<User>>() {
            @Override
            public void onSuccess(ResponseObject<User> response) {
                if (response.getData() != null) {
                    sharedPref.setLongData("userId", response.getData().getId());
                    sharedPref.setStringData("avatar", response.getData().getAvatar());
                    sharedPref.setStringData("username", response.getData().getUsername());
                    sharedPref.setStringData("loggedIn", "yes");
                    registerLiveData.postValue(response.getData());
                } else {
                    errorLiveData.postValue(response.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                errorLiveData.postValue("Something goes wrong");
            }
        });
    }

}
