package cdio.desert_eagle.project_bts.repository.message;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cdio.desert_eagle.project_bts.model.request.MESSAGE_TYPE;
import cdio.desert_eagle.project_bts.model.request.Message;
import cdio.desert_eagle.project_bts.model.request.UserMessage;
import cdio.desert_eagle.project_bts.model.response.MessageResponse;

public class MessageRepositoryImpl implements MessageRepository {
    private final DatabaseReference reference;

    public MessageRepositoryImpl() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://project-bts-85e4b-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = database.getReference();
    }

    @Override
    public void sendMessage(UserMessage userMessage, Message message, Long userId, MessageResultListener<Void> listener) {
        // users/{userId}/{receiverId}
        DatabaseReference dbUserReference = reference.child("users")
                .child(String.valueOf(userId)).child(String.valueOf(userMessage.getUserId()));
        DatabaseReference dbMessageReference = reference.child("messages");
        dbUserReference.setValue(userMessage);
        String messageId = (userId < userMessage.getUserId()) ? userId + ":" + userMessage.getUserId() : userMessage.getUserId() + ":" + userId;
        dbMessageReference.child(messageId)
                .child(message.getSentAt())
                .child("message")
                .setValue(new MessageResponse(message.getUserId(), message.getMessage()))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onSuccess(task.getResult());
                    }
                });
    }

    @Override
    public void getMessage(Long userId, String messageId, MessageResultListener<List<Message>> listener) {
        reference.child("messages").child(messageId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    final List<Message> messages = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        MessageResponse messageResponse = dataSnapshot.child("message").getValue(MessageResponse.class);

                        Message message = new Message(
                                messageResponse.getKey(),
                                dataSnapshot.getKey(),
                                messageResponse.getValue(),
                                (Objects.equals(userId, messageResponse.getKey())) ?
                                        MESSAGE_TYPE.SENDING : MESSAGE_TYPE.RECEIVING);
                        messages.add(message);
                    }
                    listener.onSuccess(messages);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void getUsersMessaged(Long userId, MessageResultListener<List<UserMessage>> listener) {
        reference.child("users").child(String.valueOf(userId)).orderByChild("lastMessage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    final List<UserMessage> userMessages = new ArrayList<>();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        final UserMessage userMessage = data.getValue(UserMessage.class);
                        userMessages.add(userMessage);
                    }
                    listener.onSuccess(userMessages);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailure(error.toException());
            }
        });
    }


}
