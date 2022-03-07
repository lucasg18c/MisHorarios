package com.slavik.misHorariosApi.data;

import com.slavik.misHorariosApi.model.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends CrudRepository<Course, Integer> {

    @Query("SELECT c FROM Course c WHERE c.user_id = :id ORDER BY c.dayOfWeek, c.startHour, c.startMinute")
    Iterable<Course> getByUser(@Param("id") int user_id);
}
