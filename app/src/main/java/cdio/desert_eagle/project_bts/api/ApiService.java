package cdio.desert_eagle.project_bts.api;

import cdio.desert_eagle.project_bts.model.request.CommentRequest;
import cdio.desert_eagle.project_bts.model.request.LoginRequest;
import cdio.desert_eagle.project_bts.model.response.Comment;
import cdio.desert_eagle.project_bts.model.response.CommentResponse;
import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.Reaction;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.User;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // auth
    @POST("/api/users/login")
    Call<ResponseObject<User>> login(@Body LoginRequest loginRequest);

    @Multipart
    @Headers("multipart:true")
    @POST("/api/users/create")
    Call<ResponseObject<User>> register(
            @Part("username") RequestBody user,
            @Part("password") RequestBody password,
            @Part("email") RequestBody email,
            @Part("bio") RequestBody bio,
            @Part MultipartBody.Part image
    );

    // post
    @GET("/api/posts/user/{id}")
    Call<ResponseObject<PageResponse<UserPosts>>> getAllUserPosts(@Path("id") Long id, @Query("page") int page, @Query("size") int size);


    // reaction
    @GET("/api/reactions/user/{user_id}/post/{post_id}")
    Call<Boolean> existedReaction(@Path("user_id") Long userId, @Path("post_id") Long postId);

    @DELETE("/api/reactions/delete/user/{user_id}/post/{post_id}")
    Call<ResponseObject<String>> deleteReaction(@Path("user_id") Long userId, @Path("post_id") Long postId);

    @POST("/api/reactions/add/user/{user_id}/post/{post_id}")
    Call<ResponseObject<Reaction>> addReaction(@Path("user_id") Long userId, @Path("post_id") Long postId);

    // comment
    @GET("/api/comments/post/{id}")
    Call<ResponseObject<PageResponse<CommentResponse>>> getPostComments(
            @Path("id") Long postId,
            @Query(value = "page") int page,
            @Query(value = "size") int size
    );

    @PUT("/api/comments/add")
    Call<ResponseObject<Comment>> addComment(@Body CommentRequest commentRequest);

    // user
    @GET("/api/users/search/{username}")
    Call<ResponseObject<PageResponse<User>>> searchUsersByUsername(
            @Path("username") String username,
            @Query(value = "page") int page,
            @Query(value = "size") int size
    );
}
