package com.slavik.mishorarios.model;

import java.util.List;

public class User {

    private int id;
    private String name;
    private String lastName;
    private String email;
    private String passwd;
    private List<Course> courses;

    public User() {

    }

    public User(String name, String lastName, String email, String passwd) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.passwd = passwd;
    }

    public User(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
