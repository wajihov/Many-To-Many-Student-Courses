package fr.gopartner.domaine.course;

import fr.gopartner.core.exception.StudentCourseException;
import fr.gopartner.core.rest.Codes;
import fr.gopartner.dto.CoursesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;
    private final CoursesMapper coursesMapper;

    public CourseService(CourseRepository courseRepository, CoursesMapper coursesMapper) {
        this.courseRepository = courseRepository;
        this.coursesMapper = coursesMapper;
    }

    private Course searchCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new StudentCourseException(Codes.ERROR_COURSE_NOT_FOUND));
        return course;
    }

    public CoursesDto createCourse(CoursesDto coursesDto) {
        if (coursesDto == null) {
            throw new StudentCourseException(Codes.ERROR_COURSE_NOT_FOUND);
        }
        Course course = coursesMapper.toEntity(coursesDto);
        course = courseRepository.save(course);
        log.info("The course {} has been successfully added.", course.getName());
        return coursesMapper.toDto(course);
    }

    public CoursesDto findCourseById(Long id) {
        if (id == null) {
            throw new StudentCourseException(Codes.ERROR_COURSE_NOT_FOUND);
        }
        Course course = searchCourseById(id);
        log.info("Search for courses with ID {} found.", id);
        return coursesMapper.toDto(course);
    }

    public List<CoursesDto> findCourses() {
        List<Course> courses = courseRepository.findAll();
        log.info("The courses sought are {}.", courses.size());
        return coursesMapper.toDtos(courses);
    }

    public void deleteCourse(Long id) {
        Course course = searchCourseById(id);
        log.info("The course {} has been successfully deleted.", course.getName());
        courseRepository.delete(course);
    }

    public CoursesDto updateCourse(Long id, CoursesDto coursesDto) {
        Course course = coursesMapper.toEntity(coursesDto);
        course.setId(id);
        course = courseRepository.save(course);
        log.info("The course was successfully changed.");
        return coursesMapper.toDto(course);
    }

    public List<CoursesDto> getCoursesByName(String name) {
        List<Course> courses = courseRepository.findCourseByNameContaining(name);
        log.info("Searched courses  are {}", courses.size());
        return coursesMapper.toDtos(courses);
    }
}
