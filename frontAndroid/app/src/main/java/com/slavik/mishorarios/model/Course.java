package com.slavik.mishorarios.model;

import com.slavik.mishorarios.util.DaysOfWeek;
import com.slavik.mishorarios.util.Format;

public class Course {

    private int id;
    private int user_id;
    private String name;
    private int startHour, endHour, startMinute, endMinute, dayOfWeek;

    public Course() {
    }

    public Course(int user_id, String name, int startHour, int endHour, int startMinute, int endMinute, int dayOfWeek) {
        this.user_id = user_id;
        this.name = name;
        this.startHour = startHour;
        this.endHour = endHour;
        this.startMinute = startMinute;
        this.endMinute = endMinute;
        this.dayOfWeek = dayOfWeek;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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

    public String getStart() {
        return Format.hour(startHour, startMinute);
    }

    public String getEnd() {
        return Format.hour(endHour, endMinute);
    }

    public String getDay() {
        return DaysOfWeek.days[dayOfWeek];
    }
}
