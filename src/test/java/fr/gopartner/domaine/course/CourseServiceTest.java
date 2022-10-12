package fr.gopartner.domaine.course;

import fr.gopartner.dto.CoursesDto;
import lombok.var;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CourseServiceTest {

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    CoursesMapper coursesMapper;

    @Autowired
    private CourseService courseService;

    @Captor
    private ArgumentCaptor<Course> courseArgumentCaptor;

    @Test
    void GIVEN_coursesDto_WHEN_CreateCourse_THEN_Should_save_on_database() {
        //GIVEN
        var coursesDto = new CoursesDto();
        coursesDto.setId(1L);
        coursesDto.setName("Philosophy");
        coursesDto.setDescription("That identifies and clarifies the beliefs.");

        var course = new Course();
        course.setName(coursesDto.getName());
        course.setDescription(coursesDto.getDescription());
        Mockito.when(coursesMapper.toEntity(coursesDto)).thenReturn(course);
        Mockito.when(coursesMapper.toDto(course)).thenReturn(coursesDto);
        Mockito.when(courseRepository.save(course)).thenReturn(course);
        //WHEN
        coursesDto = courseService.createCourse(coursesDto);
        //THEN
        Mockito.verify(courseRepository).save(courseArgumentCaptor.capture());
        Course courseCreated = courseArgumentCaptor.getValue();
        Assertions.assertEquals(courseCreated.getName(), coursesDto.getName());
        Assertions.assertEquals(courseCreated.getDescription(), coursesDto.getDescription());
    }

    @Test
    void GIVEN_coursesId_WHEN_findCourseById_THEN_Should_get_on_database() {
        //GIVEN
        long courseId = 5L;
        Course course = new Course();
        course.setId(5L);
        course.setName("Philosophy");
        course.setDescription("That identifies and clarifies the beliefs.");
        var coursesDto = new CoursesDto();
        coursesDto.setId(course.getId());
        coursesDto.setName(course.getName());
        coursesDto.setDescription(course.getDescription());

        Mockito.when(coursesMapper.toDto(course)).thenReturn(coursesDto);
        Mockito.when(courseRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(course));
        //WHEN
        coursesDto = courseService.findCourseById(courseId);
        //THEN
        Assertions.assertEquals(coursesDto.getId(), course.getId());
        Assertions.assertEquals(coursesDto.getName(), course.getName());
        Assertions.assertEquals(coursesDto.getDescription(), course.getDescription());
    }

    @Test
    void GIVEN_Id_Course_WHEN_deleteCourseById_then_should_delete_from_database() {
        //GIVEN
        long courseId = 4L;
        Course course = new Course();
        course.setId(4L);
        course.setName("Philosophy");
        course.setDescription("That identifies and clarifies the beliefs.");
        Mockito.when(courseRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(course));
        //WHEN
        courseService.deleteCourse(courseId);
        //THEN
        Mockito.verify(courseRepository).delete(courseArgumentCaptor.capture());
        Course courseFormCaptor = courseArgumentCaptor.getValue();
        Assertions.assertEquals(courseFormCaptor.getId(), course.getId());
        Assertions.assertEquals(courseFormCaptor.getName(), course.getName());
        Assertions.assertEquals(courseFormCaptor.getDescription(), course.getDescription());

    }

    @Test
    void GIVEN_All_courses_saved_WHEN_getAllCourses_then_should_return_course_from_database() {
        //GIVEN
        Course firstCourse = new Course();
        firstCourse.setId(1L);
        firstCourse.setName("English");
        firstCourse.setDescription("Learn the language of shakespeare");

        Course secondCourse = new Course();
        secondCourse.setId(2L);
        secondCourse.setName("Physique");
        secondCourse.setDescription("the science of life");
        List<Course> courseList = new ArrayList<Course>() {
            {
                add(firstCourse);
                add(secondCourse);
            }
        };
        CoursesDto firstCoursesDto = new CoursesDto();
        firstCoursesDto.setId(firstCourse.getId());
        firstCoursesDto.setName(firstCourse.getName());
        firstCoursesDto.setDescription(firstCourse.getDescription());

        CoursesDto secondCoursesDto = new CoursesDto();
        secondCoursesDto.setId(secondCourse.getId());
        secondCoursesDto.setName(secondCourse.getName());
        secondCoursesDto.setDescription(secondCourse.getDescription());

        List<CoursesDto> dtoList = new ArrayList<>();
        dtoList.add(firstCoursesDto);
        dtoList.add(secondCoursesDto);

        Mockito.when(coursesMapper.toDtoList(courseList)).thenReturn(dtoList);
        Mockito.when(courseRepository.findAll()).thenReturn(courseList);
        // WHEN
        dtoList = courseService.findCourses(null);
        // THEN
        Assertions.assertEquals(dtoList.size(), courseList.size());
        for (int i = 0; i < courseList.size(); i++) {
            Assertions.assertEquals(dtoList.get(i).getId(), courseList.get(i).getId());
            Assertions.assertEquals(dtoList.get(i).getName(), courseList.get(i).getName());
            Assertions.assertEquals(dtoList.get(i).getDescription(), courseList.get(i).getDescription());
        }
    }

    @Test
    void GIVEN_All_courses_saved_WHEN_getAllCourses_with_name_then_should_return_courses_from_database() {
        //GIVEN
        Course firstCourse = new Course();
        firstCourse.setId(1L);
        firstCourse.setName("English");
        firstCourse.setDescription("Learn the language of shakespeare");

        Course secondCourse = new Course();
        secondCourse.setId(2L);
        secondCourse.setName("Physique");
        secondCourse.setDescription("the science of life");
        List<Course> courseList = new ArrayList<Course>() {
            {
                add(firstCourse);
                add(secondCourse);
            }
        };
        CoursesDto firstCoursesDto = new CoursesDto();
        firstCoursesDto.setId(firstCourse.getId());
        firstCoursesDto.setName(firstCourse.getName());
        firstCoursesDto.setDescription(firstCourse.getDescription());

        CoursesDto secondCoursesDto = new CoursesDto();
        secondCoursesDto.setId(secondCourse.getId());
        secondCoursesDto.setName(secondCourse.getName());
        secondCoursesDto.setDescription(secondCourse.getDescription());

        List<CoursesDto> dtoList = new ArrayList<>();
        dtoList.add(firstCoursesDto);
        dtoList.add(secondCoursesDto);

        Mockito.when(coursesMapper.toDtoList(courseList)).thenReturn(dtoList);
        Mockito.when(courseRepository.findCourseByNameContaining(Mockito.anyString())).thenReturn(courseList);
        // WHEN
        dtoList = courseService.findCourses("Francais");
        // THEN
        Assertions.assertEquals(dtoList.size(), courseList.size());
        for (int i = 0; i < courseList.size(); i++) {
            Assertions.assertEquals(dtoList.get(i).getId(), courseList.get(i).getId());
            Assertions.assertEquals(dtoList.get(i).getName(), courseList.get(i).getName());
            Assertions.assertEquals(dtoList.get(i).getDescription(), courseList.get(i).getDescription());
        }
    }

    @Test
    void GIVEN_Course_WHEN_updateCourse_THEN_SHOULD_UPDATE_ON_DATABASE() {
        //GIVEN
        long courseId = 1L;
        var firstCourse = new Course();
        firstCourse.setId(1L);
        firstCourse.setName("English");
        firstCourse.setDescription("Learn the language of shakespeare");

        var coursesDto = new CoursesDto();
        coursesDto.setName("Philosophy");
        coursesDto.setDescription("That identifies and clarifies the beliefs.");

        Mockito.when(coursesMapper.toEntity(coursesDto)).thenReturn(firstCourse);
        Mockito.when(courseRepository.save(Mockito.any())).thenReturn(firstCourse);
        Mockito.when(coursesMapper.toDto(firstCourse)).thenReturn(coursesDto);
        // WHEN
        var coursesDtoUpdated = courseService.updateCourse(courseId, coursesDto);
        // THEN
        Assertions.assertEquals(coursesDto.getId(), coursesDtoUpdated.getId());
        Assertions.assertEquals(coursesDto.getName(), coursesDtoUpdated.getName());
        Assertions.assertEquals(coursesDto.getDescription(), coursesDtoUpdated.getDescription());
    }

    @Test
    void Given_course_WHEN_toEntity_THEN_SHOULD_return_RuntimeException() {
        //GIVEN & WHEN
        var course = new Course();
        Mockito.when(courseRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(course));
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            courseService.findCourseById(null);
        });
        Assertions.assertEquals("COURSE NOT FOUND", e.getMessage());
    }


}