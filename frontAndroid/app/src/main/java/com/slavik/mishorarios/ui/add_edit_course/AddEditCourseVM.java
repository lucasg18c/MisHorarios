package com.slavik.mishorarios.ui.add_edit_course;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.slavik.mishorarios.data.local.Repository;
import com.slavik.mishorarios.data.remote.ServiceProvider;
import com.slavik.mishorarios.model.Course;
import com.slavik.mishorarios.util.Format;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditCourseVM extends ViewModel {

    private MutableLiveData<String> hourStart = new MutableLiveData<>("Seleccionar");
    private MutableLiveData<String> hourEnd = new MutableLiveData<>("Seleccionar");
    private MutableLiveData<String> name = new MutableLiveData<>("");
    private MutableLiveData<Integer> dayIndex = new MutableLiveData<>(0);
    private MutableLiveData<String> error = new MutableLiveData<>("");
    private MutableLiveData<Boolean> isAcceptEnabled = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isSuccessful = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isEdit = new MutableLiveData<>(false);

    private int _hourStart = -1, _hourEnd = -1, _minuteStart = -1, _minuteEnd = -1, _dayIndex = -1;
    private String _name;

    public LiveData<Boolean> getIsEdit() {
        return isEdit;
    }

    public LiveData<String> getName() {
        return name;
    }

    public LiveData<Integer> getDayIndex() {
        return dayIndex;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> isAcceptEnabled() {
        return isAcceptEnabled;
    }

    public LiveData<Boolean> isSuccessful() {
        return isSuccessful;
    }

    public LiveData<String> getHourStart() {
        return hourStart;
    }

    public LiveData<String> getHourEnd() {
        return hourEnd;
    }


    public void onHourStartChanged(int hour, int minute) {
        _hourStart = hour;
        _minuteStart = minute;
        hourStart.postValue(Format.hour(hour, minute));
        updateIsAcceptEnabled();
    }

    public void onHourEndChanged(int hour, int minute) {
        _hourEnd = hour;
        _minuteEnd = minute;
        hourEnd.postValue(Format.hour(hour, minute));
        updateIsAcceptEnabled();
    }

    public void accept() {
        if (!isAcceptEnabled.getValue()) return;

        Repository repo = Repository.getInstance();

        Course course = new Course(
                repo.getUser().getId(),
                _name,
                _hourStart,
                _hourEnd,
                _minuteStart,
                _minuteEnd,
                _dayIndex
        );

        if (isEdit.getValue()) {
            course.setId(repo.getEditCourse().getId());
            editCourse(course);
        }

        else {
            addCourse(course);
        }
    }

    private void editCourse(Course course) {
        ServiceProvider.getInstance().getCourseService()
                .editCourse(course).enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                isSuccessful.postValue(true);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                isSuccessful.postValue(true);
            }
        });
    }

    private void addCourse(Course course) {
        ServiceProvider.getInstance().getCourseService()
                .addCourse(course).enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                isSuccessful.postValue(true);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                isSuccessful.postValue(true);
            }
        });
    }

    private void showError() {
        error.postValue("Ups... Ocurrió un error");
    }

    public void onNameChanged(String name) {
        this._name = name;
        updateIsAcceptEnabled();
    }

    public void onDayChanged(int dayIndex) {
        _dayIndex = dayIndex;
        updateIsAcceptEnabled();
    }

    private void updateIsAcceptEnabled() {
        if (_hourStart == -1 || _hourEnd == -1 || _minuteStart == -1 ||
                _minuteEnd == -1 || _dayIndex == -1 || _name.length() == 0) {
            isAcceptEnabled.postValue(false);
            return;
        }

        if (_hourStart * 60 + _minuteStart >= _hourEnd * 60 + _minuteEnd) {
            isAcceptEnabled.postValue(false);
            return;
        }

        isAcceptEnabled.postValue(true);
    }

    public void initIsEdit() {
        Course course = Repository.getInstance().getEditCourse();

        if (course == null) return;

        onHourEndChanged(course.getEndHour(), course.getEndMinute());
        onHourStartChanged(course.getStartHour(), course.getStartMinute());
        _name = course.getName();
        name.setValue(_name);
        _dayIndex = course.getDayOfWeek();
        dayIndex.setValue(_dayIndex);
        updateIsAcceptEnabled();
        isEdit.setValue(true);
    }

    public void delete() {
        ServiceProvider.getInstance().getCourseService()
                .deleteCourse(Repository.getInstance().getEditCourse().getId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                isSuccessful.postValue(true);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                isSuccessful.postValue(true);
            }
        });
    }
}
