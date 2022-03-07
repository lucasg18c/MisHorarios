package com.slavik.mishorarios.ui.main;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slavik.mishorarios.R;
import com.slavik.mishorarios.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {

    private List<Course> courses = new ArrayList<>();
    private ItemClickedListener listener;

    public CoursesAdapter(ItemClickedListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(courses.get(position));
        holder.itemView.setOnClickListener(view -> listener.onItemClicked(courses.get(position)));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtName;
        private final TextView txtDay;
        private final TextView txtStart;
        private final TextView txtEnd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.lblName);
            txtDay = itemView.findViewById(R.id.lblDay);
            txtStart = itemView.findViewById(R.id.lblStart);
            txtEnd = itemView.findViewById(R.id.lblEnd);
        }

        public void bind(Course course) {
            txtName.setText(course.getName());
            txtDay.setText(course.getDay());
            txtStart.setText("de " + course.getStart());
            txtEnd.setText("a " + course.getEnd());
        }
    }

    public interface ItemClickedListener{
        void onItemClicked (Course course);
    }
}
