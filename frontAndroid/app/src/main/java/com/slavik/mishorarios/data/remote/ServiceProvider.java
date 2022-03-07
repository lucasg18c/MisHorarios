package com.slavik.mishorarios.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceProvider {

    private Retrofit retrofit;
    private CourseService courseService;
    private UserService userService;

    private ServiceProvider() {

    }

    private static ServiceProvider instance;

    public static ServiceProvider getInstance() {
        if (instance == null) {
            instance = new ServiceProvider();
            instance.retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.136:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            instance.courseService = instance.retrofit.create(CourseService.class);
            instance.userService = instance.retrofit.create(UserService.class);
        }
        return instance;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public UserService getUserService() {
        return userService;
    }
}
