package cdio.desert_eagle.project_bts.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import cdio.desert_eagle.project_bts.model.request.UserMessage;
import cdio.desert_eagle.project_bts.repository.message.MessageRepository;
import cdio.desert_eagle.project_bts.repository.message.MessageRepositoryImpl;

public class UserListViewModel extends ViewModel {

    private final MessageRepository messageRepository;
    public MutableLiveData<String> realTimeMessageLiveData;

    public UserListViewModel() {
        messageRepository = new MessageRepositoryImpl();
        realTimeMessageLiveData = new MutableLiveData<>();
    }

    public void sendMessage(UserMessage userMessage, Long userId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d'T'HH:mm:ss+HH:mm");
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        String timeStamp = simpleDateFormat.format(now);
        userMessage.setSentAt(timeStamp);
        messageRepository.sendMessage(userMessage, userId, new MessageRepository.MessageResultListener<Void>() {
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
