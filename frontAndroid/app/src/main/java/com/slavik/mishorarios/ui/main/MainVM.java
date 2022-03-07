package com.slavik.mishorarios.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.slavik.mishorarios.data.local.Repository;
import com.slavik.mishorarios.data.remote.ServiceProvider;
import com.slavik.mishorarios.model.Course;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainVM extends ViewModel {

    private MutableLiveData<String> error = new MutableLiveData<>("");
    private MutableLiveData<List<Course>> courses = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Boolean> isAllDays = new MutableLiveData<>(false);

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<List<Course>> getCourses() {
        return courses;
    }

    public LiveData<Boolean> getIsAllDays() {
        return isAllDays;
    }

    public void refresh() {
        Repository.getInstance().setEditCourse(null);

        if (isAllDays.getValue()) {
            getAllDays();
        } else {
            getToday();
        }
    }

    private void getToday() {
        ServiceProvider.getInstance().getCourseService()
                .getTodayByUser(Repository.getInstance().getUser().getId())
                .enqueue(new Callback<List<Course>>() {
                    @Override
                    public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                        if (!response.isSuccessful()) {
                            showError();
                            return;
                        }
                        courses.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Course>> call, Throwable t) {
                        showError();
                    }
                });
    }

    private void getAllDays() {
        ServiceProvider.getInstance().getCourseService()
                .getByUser(Repository.getInstance().getUser().getId())
                .enqueue(new Callback<List<Course>>() {
                    @Override
                    public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                        if (!response.isSuccessful()) {
                            showError();
                            return;
                        }
                        courses.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Course>> call, Throwable t) {
                        showError();
                    }
                });
    }

    private void showError() {
        error.postValue("Ups... Ocurrió un error");
    }

    public void courseSelected(Course course) {
        Repository.getInstance().setEditCourse(course);
    }

    public void flipDays() {
        isAllDays.setValue(!isAllDays.getValue());
        refresh();
    }
}
