package cdio.desert_eagle.project_bts.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

import cdio.desert_eagle.project_bts.data.SharedPref;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.User;
import cdio.desert_eagle.project_bts.repository.auth.AuthRepository;
import cdio.desert_eagle.project_bts.repository.auth.AuthRepositoryImpl;

public class LoginViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final SharedPref sharedPref;
    public MutableLiveData<User> loginLiveData;
    public MutableLiveData<String> errorLiveData;


    public LoginViewModel(Application application) {
        super(application);
        this.authRepository = new AuthRepositoryImpl();
        this.sharedPref = new SharedPref(application);
        loginLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }

    public void saveUserInformation(Long userId) {
        sharedPref.setStringData("loggedIn", "yes");
        sharedPref.setLongData("userId", userId);
    }


    public void login(String username, String password) {
        authRepository.login(username, password, new AuthRepository.AuthResultListener<ResponseObject<User>>() {
            @Override
            public void onSuccess(ResponseObject<User> response) {
                if (Objects.equals(response.getStatus(), "1006")) {
                    errorLiveData.postValue(response.getMessage());
                } else if (Objects.equals(response.getStatus(), "1005")) {
                    errorLiveData.postValue(response.getMessage());
                } else {
                    loginLiveData.postValue(response.getData());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
