package com.slavik.mishorarios.data.local;

import com.slavik.mishorarios.model.Course;
import com.slavik.mishorarios.model.User;

public class Repository {
    private Repository() {

    }

    private static Repository instance;
    public static Repository getInstance() {
        if (instance == null) instance = new Repository();
        return instance;
    }

    private User user;
    private Course editCourse;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getEditCourse() {
        return editCourse;
    }

    public void setEditCourse(Course editCourse) {
        this.editCourse = editCourse;
    }
}
