package com.slavik.misHorariosApi.controller;

import com.slavik.misHorariosApi.data.CourseRepository;
import com.slavik.misHorariosApi.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api/courses")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseRepository repository;

    @PostMapping
    public @ResponseBody String addCourse(@RequestBody Course course) {
        repository.save(course);
        return "Saved";
    }

    @GetMapping
    public @ResponseBody Iterable<Course> getAll() {
        return repository.findAll();
    }

    @PutMapping
    public @ResponseBody String update(@RequestBody Course course) {
        repository.save(course);
        return "Saved";
    }

    @GetMapping("/{id}")
    public @ResponseBody Optional<Course> getCoursesByID(@PathVariable int id) {
        return repository.findById(id);
    }

    @GetMapping("/today/user/{id}")
    public @ResponseBody Iterable<Course> getRecentCoursesByUser(@PathVariable int id) {
        Iterable<Course> courses = repository.getByUser(id);
        List<Course> todayCourses = new ArrayList<>();
        int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2;

        for (Course c : courses) {
            if (c.getDayOfWeek() == today) {
                todayCourses.add(c);
            }
        }
        return todayCourses;
    }

    @GetMapping("/user/{id}")
    public @ResponseBody Iterable<Course> getAllCoursesByUser(@PathVariable int id) {
        return repository.getByUser(id);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteCourse(@PathVariable int id) {
        repository.deleteById(id);
        return "Deleted";
    }
}
