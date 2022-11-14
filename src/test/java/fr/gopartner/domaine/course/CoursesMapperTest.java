package fr.gopartner.domaine.course;

import fr.gopartner.core.exception.StudentCourseException;
import fr.gopartner.dto.CoursesDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class CoursesMapperTest {

    @Autowired
    private CoursesMapper coursesMapper;

    @Test
    void Given_coursesDto_WHEN_toEntity_THEN_SHOULD_return_Course() {
        //GIVEN
        Course course = new Course();
        course.setId(1L);
        course.setName("French");
        course.setDescription("The language of Moliere");
        //WHEN
        CoursesDto coursesDto = coursesMapper.toDto(course);
        //THEN
        Assertions.assertEquals(coursesDto.getId(), course.getId());
        Assertions.assertEquals(coursesDto.getName(), course.getName());
        Assertions.assertEquals(coursesDto.getDescription(), course.getDescription());
    }

    @Test
    void Given_course_WHEN_toDto_THEN_SHOULD_return_CoursesDto() {
        //GIVEN
        CoursesDto coursesDto = new CoursesDto();
        coursesDto.setId(2L);
        coursesDto.setName("English");
        coursesDto.setName("Learn the language of shakespeare");
        //WHEN
        Course course = coursesMapper.toEntity(coursesDto);
        //THEN
        Assertions.assertEquals(course.getId(), coursesDto.getId());
        Assertions.assertEquals(course.getName(), coursesDto.getName());
        Assertions.assertEquals(course.getDescription(), coursesDto.getDescription());
    }

    @Test
    void Given_listCourses_WHEN_toEntities_THEN_SHOULD_return_listCoursesDto() {
        //GIVEN
        Course firstCourse = new Course();
        firstCourse.setId(1L);
        firstCourse.setName("English");
        firstCourse.setName("Learn the language of shakespeare");
        Course secondCourse = new Course();
        secondCourse.setId(2L);
        secondCourse.setName("French");
        secondCourse.setDescription("The language of Moliere");
        List<Course> courseList = new ArrayList<>();
        courseList.add(firstCourse);
        courseList.add(secondCourse);
        //WHEN
        List<CoursesDto> coursesDtoList = coursesMapper.toDtoList(courseList);
        //THEN
        for (int i = 0; i < coursesDtoList.size(); i++) {
            Assertions.assertEquals(courseList.get(i).getId(), coursesDtoList.get(i).getId());
            Assertions.assertEquals(courseList.get(i).getName(), coursesDtoList.get(i).getName());
            Assertions.assertEquals(courseList.get(i).getDescription(), coursesDtoList.get(i).getDescription());
        }
    }

    @Test
    void Given_listCoursesDtoList_WHEN_toDtoList_THEN_SHOULD_return_listCourses() {
        //GIVEN
        CoursesDto firstCoursesDto = new CoursesDto();
        firstCoursesDto.setId(1L);
        firstCoursesDto.setName("English");
        firstCoursesDto.setName("Learn the language of shakespeare");
        CoursesDto secondCoursesDto = new CoursesDto();
        secondCoursesDto.setId(2L);
        secondCoursesDto.setName("French");
        secondCoursesDto.setDescription("The language of Moliere");
        List<CoursesDto> coursesDtoList = new ArrayList<>();
        coursesDtoList.add(firstCoursesDto);
        coursesDtoList.add(secondCoursesDto);
        //WHEN
        List<Course> courses = coursesMapper.toEntities(coursesDtoList);
        //THEN
        for (int i = 0; i < courses.size(); i++) {
            Assertions.assertEquals(courses.get(i).getId(), coursesDtoList.get(i).getId());
            Assertions.assertEquals(courses.get(i).getName(), coursesDtoList.get(i).getName());
            Assertions.assertEquals(courses.get(i).getDescription(), coursesDtoList.get(i).getDescription());
        }
    }

    @Test
    void Given_course_WHEN_toEntity_THEN_SHOULD_return_StudentCourseException() {
        //GIVEN & WHEN
        StudentCourseException e = Assertions.assertThrows(StudentCourseException.class, () ->
                coursesMapper.toEntity(null)
        );
        Assertions.assertEquals("COURSE NOT FOUND", e.getMessage());
    }

    @Test
    void Given_courseDto_WHEN_toDto_THEN_SHOULD_return_StudentCourseException() {
        //GIVEN & WHEN
        StudentCourseException e = Assertions.assertThrows(StudentCourseException.class, () ->
                coursesMapper.toDto(null)
        );
        Assertions.assertEquals("COURSE NOT FOUND", e.getMessage());
    }

    @Test
    void Given_courseDtoList_WHEN_toDtoList_THEN_SHOULD_return_StudentCourseException() {
        //GIVEN & WHEN
        StudentCourseException e = Assertions.assertThrows(StudentCourseException.class, () ->
                coursesMapper.toDtoList(null)
        );
        Assertions.assertEquals("COURSES NOT FOUND", e.getMessage());
    }

    @Test
    void Given_courseList_WHEN_toEntitiesList_THEN_SHOULD_return_StudentCourseException() {
        //GIVEN & WHEN
        StudentCourseException e = Assertions.assertThrows(StudentCourseException.class, () ->
                coursesMapper.toEntities(null)
        );
        Assertions.assertEquals("COURSES NOT FOUND", e.getMessage());
    }
}