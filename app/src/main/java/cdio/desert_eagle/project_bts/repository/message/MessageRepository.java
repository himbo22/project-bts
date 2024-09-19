package cdio.desert_eagle.project_bts.repository.message;

import java.util.List;

import cdio.desert_eagle.project_bts.model.request.Message;
import cdio.desert_eagle.project_bts.model.request.UserMessage;
import cdio.desert_eagle.project_bts.repository.BaseResult;

public interface MessageRepository {

    void sendMessage(UserMessage userMessage, Message message, Long userId, MessageResultListener<Void> listener);

    void getMessage(Long userId, String messageId, MessageResultListener<List<Message>> listener);

    void getUsersMessaged(Long userId, MessageResultListener<List<UserMessage>> listener);

    interface MessageResultListener<T> extends BaseResult<T> {

    }
}
