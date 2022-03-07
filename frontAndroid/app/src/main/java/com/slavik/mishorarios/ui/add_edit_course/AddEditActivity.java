package com.slavik.mishorarios.ui.add_edit_course;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.slavik.mishorarios.R;
import com.slavik.mishorarios.ui.main.MainActivity;
import com.slavik.mishorarios.util.DaysOfWeek;
import com.slavik.mishorarios.util.TextWatcherLite;

public class AddEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        AddEditCourseVM vm = new ViewModelProvider(this).get(AddEditCourseVM.class);

        Button btnAccept, btnCancel, btnDelete;
        btnAccept = findViewById(R.id.btnAccept);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);

        Spinner txtDay = findViewById(R.id.txtDay);
        txtDay.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                DaysOfWeek.days
        ));
        txtDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vm.onDayChanged(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        TextView txtStart, txtEnd;
        EditText txtName = findViewById(R.id.txtName);
        txtName.addTextChangedListener(new TextWatcherLite() {
            @Override
            public void onTextChanged(String text) {
                vm.onNameChanged(text);
            }
        });

        txtStart = findViewById(R.id.txtStart);
        txtEnd = findViewById(R.id.txtEnd);

        txtStart.setOnClickListener(v ->
                new TimeDialog(this, (timePicker, hour, minute) ->
                        vm.onHourStartChanged(hour, minute)).show());

        txtEnd.setOnClickListener(v ->
                new TimeDialog(this, (timePicker, hour, minute) ->
                        vm.onHourEndChanged(hour, minute)).show());

        vm.getHourStart().observe(this, txtStart::setText);
        vm.getHourEnd().observe(this, txtEnd::setText);

        btnCancel.setOnClickListener(v -> finish());

        btnAccept.setOnClickListener(v -> vm.accept());

        btnDelete.setOnClickListener(v -> vm.delete());

        vm.getError().observe(this, error -> {
            if (error.length() == 0) return;
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });

        vm.isSuccessful().observe(this, isSuccessful -> {
            if (isSuccessful) {
                Intent i = new Intent(AddEditActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        vm.isAcceptEnabled().observe(this, btnAccept::setEnabled);

        vm.initIsEdit();
        txtName.setText(vm.getName().getValue());
        txtDay.setSelection(vm.getDayIndex().getValue());
        if (vm.getIsEdit().getValue()) {
            btnAccept.setText("Editar");
            btnDelete.setVisibility(View.VISIBLE);
        }
    }
}