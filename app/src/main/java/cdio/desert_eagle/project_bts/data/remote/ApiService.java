package cdio.desert_eagle.project_bts.data.remote;

import androidx.annotation.Nullable;

import java.util.List;

import cdio.desert_eagle.project_bts.model.request.CommentRequest;
import cdio.desert_eagle.project_bts.model.request.LoginRequest;
import cdio.desert_eagle.project_bts.model.request.ResetPasswordRequest;
import cdio.desert_eagle.project_bts.model.response.Comment;
import cdio.desert_eagle.project_bts.model.response.CommentResponse;
import cdio.desert_eagle.project_bts.model.response.Follower;
import cdio.desert_eagle.project_bts.model.response.PageResponse;
import cdio.desert_eagle.project_bts.model.response.Post;
import cdio.desert_eagle.project_bts.model.response.Reaction;
import cdio.desert_eagle.project_bts.model.response.Report;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.model.response.User;
import cdio.desert_eagle.project_bts.model.response.UserPosts;
import cdio.desert_eagle.project_bts.model.response.UserResponse;
import cdio.desert_eagle.project_bts.model.response.VerifyingResponse;
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

    @GET("/api/posts/all")
    Call<ResponseObject<PageResponse<Post>>> getAllPosts(
            @Query("page") int page,
            @Query("size") int size
    );

    @Multipart
    @Headers("multipart:true")
    @POST("/api/posts/create")
    Call<ResponseObject<Post>> createPost(
            @Part("userId") RequestBody userId,
            @Part("caption") RequestBody caption,
            @Part("createdAt") RequestBody createAt,
            @Part MultipartBody.Part content
    );

    @DELETE("/api/posts/delete/{id}")
    Call<ResponseObject<String>> deletePost(@Path("id") Long postId);

    // follow
    @POST("/api/follows/follow/{userId}/{followId}")
    Call<ResponseObject<Follower>> followUser(@Path("userId") long userId, @Path("followId") long followId);

    @POST("/api/follows/unfollow/{userId}/{followId}")
    Call<ResponseObject<String>> unFollowUser(@Path("userId") long userId, @Path("followId") long followId);

    @GET("/api/follows/follow/{userId}/{followId}")
    Call<ResponseObject<Boolean>> getFollowUser(@Path("userId") long userId, @Path("followId") long followId);

    // reaction
    @GET("/api/reactions/user/{user_id}/post/{post_id}")
    Call<Boolean> existedReaction(@Path("user_id") Long userId, @Path("post_id") Long postId);

    @GET("/api/reactions/liked/{userId}/{userIdPostsOwner}")
    Call<List<Boolean>> likedPosts(
            @Path("userId") Long userId,
            @Path("userIdPostsOwner") Long userIdPostsOwner,
            @Query("page") int page,
            @Query("size") int size
    );

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

    @GET("/api/users/{userId}")
    Call<ResponseObject<UserResponse>> getUserById(@Path("userId") Long userId);

    @Multipart
    @Headers("multipart:true")
    @PUT("/api/users/update")
    Call<ResponseObject<User>> updateProfile(
            @Part("userId") RequestBody userId,
            @Part("username") RequestBody username,
            @Nullable @Part("bio") RequestBody bio,
            @Nullable @Part MultipartBody.Part image
    );

    @POST("/api/users/reset-password/{email}")
    Call<ResponseObject<User>> resetPassword(@Path("email") String email, @Body ResetPasswordRequest request);

    // report
    @POST("/api/reports/report/{userId}/{postId}")
    Call<ResponseObject<Report>> reportPost(
            @Path("userId") Long userId,
            @Path("postId") Long postId,
            @Body String reason
    );

    // verify otp

    @POST("/api/forgot-password/verify/{email}")
    Call<VerifyingResponse> verifyingEmail(@Path("email") String email);

    @POST("/api/forgot-password/verify/otp/{otp}/{email}")
    Call<VerifyingResponse> verifyingOtp(@Path("otp") Integer otp, @Path("email") String email);
}
