package fr.gopartner.domaine.course;

import fr.gopartner.CoursesApiDelegate;
import fr.gopartner.dto.CoursesDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController implements CoursesApiDelegate {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @Override
    public ResponseEntity<List<CoursesDto>> allCourses() {
        List<CoursesDto> coursesDtos = courseService.findCourses();
        return new ResponseEntity<>(coursesDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CoursesDto> coursesIdPut(Long id, CoursesDto coursesDto) {
        CoursesDto courseModified = courseService.updateCourse(id, coursesDto);
        return new ResponseEntity<>(courseModified, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CoursesDto> createCourse(CoursesDto coursesDto) {
        CoursesDto courseCreated = courseService.createCourse(coursesDto);
        return new ResponseEntity<>(courseCreated, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteCourse(Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CoursesDto> findCourseById(Long id) {
        CoursesDto coursesDto = courseService.findCourseById(id);
        return new ResponseEntity<>(coursesDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CoursesDto>> findCourseByName(String name) {
        List<CoursesDto> coursesDtoList = courseService.getCoursesByName(name);
        return new ResponseEntity<>(coursesDtoList, HttpStatus.OK);
    }
}
