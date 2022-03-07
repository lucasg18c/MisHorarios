package com.slavik.mishorarios.data.remote;

import com.slavik.mishorarios.model.Course;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CourseService {

    @GET("courses/user/{id}")
    Call<List<Course>> getByUser(@Path("id") int id);

    @POST("courses")
    Call<String> addCourse(@Body Course course);

    @DELETE("courses/{id}")
    Call<String> deleteCourse(@Path("id") int courseId);

    @PUT("courses")
    Call<String> editCourse(@Body Course course);

    @GET("courses/today/user/{id}")
    Call<List<Course>> getTodayByUser(@Path("id") int id);
}
