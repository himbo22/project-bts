package cdio.desert_eagle.project_bts.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import cdio.desert_eagle.project_bts.data.SharedPref;
import cdio.desert_eagle.project_bts.model.request.Message;
import cdio.desert_eagle.project_bts.model.request.UserMessage;
import cdio.desert_eagle.project_bts.repository.message.MessageRepository;
import cdio.desert_eagle.project_bts.repository.message.MessageRepositoryImpl;

public class UserListViewModel extends AndroidViewModel {

    private final MessageRepository messageRepository;
    public MutableLiveData<String> realTimeMessageLiveData;
    public MutableLiveData<List<UserMessage>> userMessagesListLiveData;
    private final Long userId;
    public final String avatar;

    public UserListViewModel(@NonNull Application application) {
        super(application);
        this.messageRepository = new MessageRepositoryImpl();
        SharedPref sharedPref = new SharedPref(application);
        realTimeMessageLiveData = new MutableLiveData<>();
        userMessagesListLiveData = new MutableLiveData<>();
        userId = sharedPref.getLongData("userId");
        avatar = sharedPref.getStringData("avatar");
    }

    public void getUsersMessaged() {
        messageRepository.getUsersMessaged(userId, new MessageRepository.MessageResultListener<List<UserMessage>>() {
            @Override
            public void onSuccess(List<UserMessage> response) {
                userMessagesListLiveData.postValue(response);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}
