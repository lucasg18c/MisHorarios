package com.slavik.mishorarios.data.remote;

import com.slavik.mishorarios.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("users/login")
    Call<User> logIn(@Body User user);

    @POST("users/signup")
    Call<User> signUp(@Body User user);

}
