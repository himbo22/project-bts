package cdio.desert_eagle.project_bts.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cdio.desert_eagle.project_bts.data.local.SharedPref;
import cdio.desert_eagle.project_bts.model.response.Post;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.repository.post.PostRepository;
import cdio.desert_eagle.project_bts.repository.post.PostRepositoryImpl;
import cdio.desert_eagle.project_bts.utils.RealPathUtil;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostViewModel extends AndroidViewModel {

    private final PostRepository postRepository;
    private final Application application;
    public Long userId;
    public String username;
    public String avatar;
    public MutableLiveData<String> errorLiveData;
    public MutableLiveData<String> successfulShareStatusLiveData;

    public PostViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        this.postRepository = new PostRepositoryImpl();
        SharedPref sharedPref = new SharedPref(application);
        userId = sharedPref.getLongData("userId");
        username = sharedPref.getStringData("username");
        avatar = sharedPref.getStringData("avatar");
        errorLiveData = new MutableLiveData<>();
        successfulShareStatusLiveData = new MutableLiveData<>();
    }


    public void createPost(String caption, Uri image) {
        String imageRealPath = RealPathUtil.getRealPath(application, image);
        File imageFile;
        if (image != null) {
            imageFile = new File(imageRealPath);
        } else {
            errorLiveData.postValue("Something goes wrong");
            return;
        }

        // create multipart body image to call api
        RequestBody requestImage = RequestBody.create(imageFile, MultipartBody.FORM);
        // the name "content" is must match to the Part's name in backend side,
        // for instance, my backend tech is Spring
        // and in controller, i set the @RequestPart(name = "content") ......
        // then the "content" below must match with the @RequestPart name in backend
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData("content", imageFile.getName(), requestImage);

        // create other request body

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", new Locale("vie", "VNM"));
        String currentDateAndTime = sdf.format(new Date());

        RequestBody userIdBody = RequestBody.create(String.valueOf(userId), MultipartBody.FORM);
        RequestBody captionBody = RequestBody.create(caption, MultipartBody.FORM);
        RequestBody createdAtBody = RequestBody.create(currentDateAndTime, MultipartBody.FORM);

        // call api
        postRepository.createPost(userIdBody, captionBody, createdAtBody, imageBody, new PostRepository.PostResultListener<ResponseObject<Post>>() {
            @Override
            public void onSuccess(ResponseObject<Post> response) {
                if (response.getData() == null) {
                    errorLiveData.postValue("Please try again!");
                } else {
                    successfulShareStatusLiveData.postValue("Ok");
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}
