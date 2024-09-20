package cdio.desert_eagle.project_bts.listener;

public interface BaseResult<T> {
    void onSuccess(T response);

    void onFailure(Throwable t);
}
