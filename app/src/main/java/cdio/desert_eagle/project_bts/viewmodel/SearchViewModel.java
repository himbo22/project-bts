package cdio.desert_eagle.project_bts.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cdio.desert_eagle.project_bts.model.response.User;
import cdio.desert_eagle.project_bts.repository.search.SearchRepository;
import cdio.desert_eagle.project_bts.repository.search.SearchRepositoryImpl;

public class SearchViewModel extends ViewModel {

    private final SearchRepository searchRepository;
    public MutableLiveData<List<User>> usersSearchResult;
    private int pages = 0;

    public SearchViewModel() {
        searchRepository = new SearchRepositoryImpl();
        usersSearchResult = new MutableLiveData<>();
    }

    public void searchUsersByUsername(String username) {
        searchRepository.searchByUsername(username, pages, 20, new SearchRepository.SearchResultListener<List<User>>() {
            @Override
            public void onSuccess(List<User> response) {
                usersSearchResult.postValue(response);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void loadMore(String username) {
        pages++;
        searchRepository.searchByUsername(username, pages, 20, new SearchRepository.SearchResultListener<List<User>>() {
            @Override
            public void onSuccess(List<cdio.desert_eagle.project_bts.model.response.User> response) {
                if (response.isEmpty()) {
                    pages--;
                    return;
                }
                Log.d("hoangdeptrai", "onSuccess: cac");
                usersSearchResult.postValue(response);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}
