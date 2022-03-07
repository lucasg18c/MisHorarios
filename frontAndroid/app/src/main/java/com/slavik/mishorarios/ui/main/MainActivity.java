package com.slavik.mishorarios.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.slavik.mishorarios.R;
import com.slavik.mishorarios.data.remote.CourseService;
import com.slavik.mishorarios.model.Course;
import com.slavik.mishorarios.ui.add_edit_course.AddEditActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private MainVM vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShowAllToday = findViewById(R.id.btnAllToday);

        RecyclerView coursesList = findViewById(R.id.coursesList);
        CoursesAdapter adapter = new CoursesAdapter( course -> {
            vm.courseSelected(course);
            startActivity(new Intent(MainActivity.this, AddEditActivity.class));
        });

        coursesList.setLayoutManager(new LinearLayoutManager(this));
        coursesList.setAdapter(adapter);

        findViewById(R.id.btnEditar).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddEditActivity.class)));

        vm = new ViewModelProvider(this).get(MainVM.class);

        vm.getError().observe(this, error -> {
            if (error.length() == 0) return;

            Toast.makeText(
                    MainActivity.this,
                    error,
                    Toast.LENGTH_SHORT)
                    .show();
        });

        vm.getCourses().observe(this, adapter::setCourses);

        vm.getIsAllDays().observe(this, isAllDays ->
                btnShowAllToday.setText(isAllDays ? "Todos los días" : "Hoy"));

        btnShowAllToday.setOnClickListener(v -> vm.flipDays());

    }

    @Override
    protected void onResume() {
        super.onResume();
        vm.refresh();
    }
}