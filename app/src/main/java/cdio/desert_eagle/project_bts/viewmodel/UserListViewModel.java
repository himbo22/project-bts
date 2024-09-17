package cdio.desert_eagle.project_bts.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import cdio.desert_eagle.project_bts.data.SharedPref;
import cdio.desert_eagle.project_bts.model.request.Message;
import cdio.desert_eagle.project_bts.model.request.UserMessage;
import cdio.desert_eagle.project_bts.repository.message.MessageRepository;
import cdio.desert_eagle.project_bts.repository.message.MessageRepositoryImpl;

public class UserListViewModel extends AndroidViewModel {

    private final MessageRepository messageRepository;
    private final SharedPref sharedPref;
    public MutableLiveData<String> realTimeMessageLiveData;
    private Long userId;

    public UserListViewModel(@NonNull Application application) {
        super(application);
        this.messageRepository = new MessageRepositoryImpl();
        this.sharedPref = new SharedPref(application);
        realTimeMessageLiveData = new MutableLiveData<>();
        userId = sharedPref.getLongData("userId");
    }

    public void sendMessage(UserMessage userMessage, Message message) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d'T'HH:mm:ss+HH:mm");
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        String timeStamp = simpleDateFormat.format(now);
        message.setSentAt(timeStamp);
        messageRepository.sendMessage(userMessage, message, userId, new MessageRepository.MessageResultListener<Void>() {
            @Override
            public void onSuccess(Void response) {
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("hoangdeptrai", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getMessage() {
        messageRepository.getMessage(new MessageRepository.MessageResultListener<String>() {
            @Override
            public void onSuccess(String response) {
                realTimeMessageLiveData.postValue(response);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


}
