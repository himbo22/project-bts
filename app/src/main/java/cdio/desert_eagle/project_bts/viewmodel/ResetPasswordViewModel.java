package cdio.desert_eagle.project_bts.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import cdio.desert_eagle.project_bts.model.request.ResetPasswordRequest;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.User;
import cdio.desert_eagle.project_bts.model.response.VerifyingResponse;
import cdio.desert_eagle.project_bts.repository.auth.AuthRepository;
import cdio.desert_eagle.project_bts.repository.auth.AuthRepositoryImpl;

public class ResetPasswordViewModel extends ViewModel {
    private final AuthRepository authRepository;
    public MutableLiveData<User> resetPasswordLiveData;
    public MutableLiveData<String> verifyingEmailLiveData;
    public MutableLiveData<String> verifyingOtpLiveData;
    public MutableLiveData<String> errorLiveData;

    public ResetPasswordViewModel() {
        this.authRepository = new AuthRepositoryImpl();
        resetPasswordLiveData = new MutableLiveData<>();
        verifyingEmailLiveData = new MutableLiveData<>();
        verifyingOtpLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }

    public void resetPassword(String email, ResetPasswordRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return;
        }
        authRepository.resetPassword(email, request, new AuthRepository.AuthResultListener<ResponseObject<User>>() {
            @Override
            public void onSuccess(ResponseObject<User> response) {
                resetPasswordLiveData.postValue(response.getData());
            }

            @Override
            public void onFailure(Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void verifyingEmail(String email) {
        authRepository.verifyingEmail(email, new AuthRepository.AuthResultListener<VerifyingResponse>() {
            @Override
            public void onSuccess(VerifyingResponse response) {
                verifyingEmailLiveData.postValue(response.getResponse());
            }

            @Override
            public void onFailure(Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void verifyingOtp(String otp, String email) {
        authRepository.verifyingOtp(Integer.valueOf(otp), email, new AuthRepository.AuthResultListener<VerifyingResponse>() {
            @Override
            public void onSuccess(VerifyingResponse response) {
                if (!Objects.equals(response.getResponse(), "verified")) {
                    errorLiveData.postValue("Check the OTP again");
                } else {
                    verifyingOtpLiveData.postValue(response.getResponse());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }
}
