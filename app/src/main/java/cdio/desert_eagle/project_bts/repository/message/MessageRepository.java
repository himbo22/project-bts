package cdio.desert_eagle.project_bts.repository.message;

import cdio.desert_eagle.project_bts.model.request.Message;
import cdio.desert_eagle.project_bts.model.request.UserMessage;
import cdio.desert_eagle.project_bts.repository.BaseResult;

public interface MessageRepository {

    void sendMessage(UserMessage userMessage, Message message, Long userId, MessageResultListener<Void> listener);

    void getMessage(MessageResultListener<String> listener);

    void getUsersGotMessage(Long userId, MessageResultListener<Void> listener);

    interface MessageResultListener<T> extends BaseResult<T> {

    }
}
