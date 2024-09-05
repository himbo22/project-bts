package cdio.desert_eagle.project_bts.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.repository.auth.AuthRepository;
import cdio.desert_eagle.project_bts.repository.auth.AuthRepositoryImpl;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private MutableLiveData<String> test;

    public MutableLiveData<String> getTest() {
        if (test == null) {
            test = new MutableLiveData<>();
        }
        return test;
    }

    public MainViewModel() {
        this.authRepository = new AuthRepositoryImpl();
    }

    public void login(String username, String password) {
        authRepository.login(username, password, new AuthRepository.AuthResultListener() {
            @Override
            public void onSuccess(Response<ResponseObject> response) {
                Log.d("hoangdeptrai", response.toString());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("hoangdeptrai", "onFailure: ");
            }
        });
    }

}
