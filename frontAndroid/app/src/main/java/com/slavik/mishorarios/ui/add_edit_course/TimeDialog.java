package com.slavik.mishorarios.ui.add_edit_course;

import android.app.TimePickerDialog;
import android.content.Context;

import java.util.Calendar;

public class TimeDialog extends TimePickerDialog {
    public TimeDialog(Context context, TimePickerDialog.OnTimeSetListener listener) {
        super(
                context,
                listener,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                false);
    }

}
