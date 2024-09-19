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
import java.util.Locale;
import java.util.Objects;

import cdio.desert_eagle.project_bts.data.SharedPref;
import cdio.desert_eagle.project_bts.model.request.Message;
import cdio.desert_eagle.project_bts.model.request.UserMessage;
import cdio.desert_eagle.project_bts.repository.message.MessageRepository;
import cdio.desert_eagle.project_bts.repository.message.MessageRepositoryImpl;

public class MessageViewModel extends AndroidViewModel {

    private final MessageRepository messageRepository;
    public MutableLiveData<String> realTimeMessageLiveData;
    public MutableLiveData<List<Message>> messagesLiveData;
    private final Long userId;
    public final String userAvatar;


    public MessageViewModel(@NonNull Application application) {
        super(application);
        this.messageRepository = new MessageRepositoryImpl();
        realTimeMessageLiveData = new MutableLiveData<>();
        messagesLiveData = new MutableLiveData<>();
        SharedPref sharedPref = new SharedPref(application);
        userId = sharedPref.getLongData("userId");
        userAvatar = sharedPref.getStringData("avatar");
    }

    public void sendMessage(UserMessage userMessage, Message message) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", new Locale("vie", "VNM"));
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        String timeStamp = simpleDateFormat.format(now);
        message.setSentAt(timeStamp);
        message.setUserId(userId);
        userMessage.setLastMessage(timeStamp);
        messageRepository.sendMessage(userMessage, message, userId, new MessageRepository.MessageResultListener<Void>() {
            @Override
            public void onSuccess(Void response) {
                realTimeMessageLiveData.postValue("ok");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("hoangdeptrai", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getMessage(Long receiverId) {
        String messageId = (userId < receiverId) ? userId + ":" + receiverId : receiverId + ":" + userId;
        messageRepository.getMessage(userId, messageId, new MessageRepository.MessageResultListener<List<Message>>() {
            @Override
            public void onSuccess(List<Message> response) {
                messagesLiveData.postValue(response);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
}