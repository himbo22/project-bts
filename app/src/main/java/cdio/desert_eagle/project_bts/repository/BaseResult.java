package cdio.desert_eagle.project_bts.repository;

public interface BaseResult<T> {
    void onSuccess(T response);

    void onFailure(Throwable t);
}
