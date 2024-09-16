package cdio.desert_eagle.project_bts.repository.search;

import java.util.List;

import cdio.desert_eagle.project_bts.model.response.User;
import cdio.desert_eagle.project_bts.repository.BaseResult;

public interface SearchRepository {

    void searchByUsername(String username, int page, int size, SearchResultListener<List<User>> listener);

    interface SearchResultListener<T> extends BaseResult<T> {
    }
}
