package edu.neag.tia.RestClient.client;


import java.util.List;
import java.util.SplittableRandom;

import edu.neag.tia.RestClient.ResComment;
import edu.neag.tia.RestClient.ResPosition;
import edu.neag.tia.RestClient.ResUser;
import edu.neag.tia.RestClient.dto.CommentDTO;
import edu.neag.tia.RestClient.dto.PositionDTO;
import edu.neag.tia.RestClient.dto.UserLoginDTO;
import edu.neag.tia.RestClient.dto.UserRegisterDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @POST("user/login")
    Call<ResUser> login(@Body UserLoginDTO userLoginDTO, @Header("Authorization") String credentials);

    @POST("user/register")
    Call<ResUser> register(@Body UserRegisterDTO userRegisterDTO);

    @POST("position/addPos")
    Call<ResPosition> addPosition(@Body PositionDTO positionDTO, @Header("Authorization") String credentials);

    @PUT("user/update/data/{email}/{lastPassword}")
    Call<ResUser> updateDate(@Path ("email") String email, @Path("lastPassword") String lastPassword, @Body UserRegisterDTO userRegisterDTO, @Header("Authorization") String credentials);

    @POST("comment/addComment")
    Call<ResComment> addComment(@Body CommentDTO commentDTO, @Header("Authorization")String credentials);

    @GET("comment/getAllComments")
    Call<List<ResComment>> getAllComments(@Header("Authorization")String credentials);


}
