package fr.gopartner.domaine.course;

import fr.gopartner.CoursesApiDelegate;
import fr.gopartner.dto.CoursesDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController implements CoursesApiDelegate {


    @Override
    public ResponseEntity<List<CoursesDto>> allCourses() {
        return CoursesApiDelegate.super.allCourses();
    }

    @Override
    public ResponseEntity<CoursesDto> coursesIdPut(Long id, CoursesDto coursesDto) {
        return CoursesApiDelegate.super.coursesIdPut(id, coursesDto);
    }

    @Override
    public ResponseEntity<CoursesDto> createCourse(CoursesDto coursesDto) {
        return CoursesApiDelegate.super.createCourse(coursesDto);
    }

    @Override
    public ResponseEntity<Void> deleteCourse(Long id) {
        return CoursesApiDelegate.super.deleteCourse(id);
    }

    @Override
    public ResponseEntity<CoursesDto> findCourseById(Long id) {
        return CoursesApiDelegate.super.findCourseById(id);
    }

    @Override
    public ResponseEntity<CoursesDto> findCourseByName(String name) {
        return CoursesApiDelegate.super.findCourseByName(name);
    }
}
