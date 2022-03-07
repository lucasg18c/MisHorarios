package com.slavik.mishorarios.util;

public class Format {

    public static String hour(int hours, int minutes) {
        String time = "";
        if (hours < 10) time += "0";
        time += hours + ":";

        if (minutes < 10) time += "0";
        time += String.valueOf(minutes);

        return time;
    }
}
