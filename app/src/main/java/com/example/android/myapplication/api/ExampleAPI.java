package com.example.android.myapplication.api;


import com.example.android.myapplication.domain.model.Echo;

import retrofit.Call;
import retrofit.http.GET;

public interface ExampleAPI
{
    @GET("/key/value/one/two")
    Call<Echo> getEcho();

//    @FormUrlEncoded
//    @Headers({
//            "Content-Type: application/x-www-form-urlencoded",
//            "Accept: application/json"
//    })
//    @POST("/api/users/sign-up")
//    Call<SignUpEvent> postSignUp(@Query("access-token")     String token,
//                                 @Field("username")         String username,
//                                 @Field("email")            String email,
//                                 @Field("password")         String password,
//                                 @Field("confirmPassword")  String password2);
}
