package cdio.desert_eagle.project_bts.repository.message;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cdio.desert_eagle.project_bts.model.request.Message;
import cdio.desert_eagle.project_bts.model.request.UserMessage;

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
                .child(String.valueOf(userId))
                .setValue(message.getMessage())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onSuccess(task.getResult());
                    }
                });
    }

    @Override
    public void getMessage(MessageResultListener<String> listener) {
    }

    @Override
    public void getUsersGotMessage(Long userId, MessageResultListener<Void> listener) {
        reference.child("users").child(String.valueOf(userId)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
