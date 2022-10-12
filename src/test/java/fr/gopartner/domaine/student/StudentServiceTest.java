package fr.gopartner.domaine.student;

import fr.gopartner.domaine.course.Course;
import fr.gopartner.dto.CoursesDto;
import fr.gopartner.dto.StudentDto;
import lombok.var;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class StudentServiceTest {

    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private StudentMapper studentMapper;

    @Autowired
    private StudentService studentService;

    @Captor
    private ArgumentCaptor<Student> studentArgumentCaptor;

    @Test
    void GIVEN_StudentDto_WHEN_CreateStudent_THEN_Should_save_on_database() {
        //GIVEN
        var studentDto = new StudentDto();
        studentDto.setId(1L);
        studentDto.setName("Joel");
        studentDto.setLastname("Gregor");
        studentDto.setGrade("Level 3");
        studentDto.setEmail("joel@hotmail.com");
        studentDto.setDateBirth("1980-12-12");
        var firstCourseDto = new CoursesDto();
        firstCourseDto.setId(1L);
        firstCourseDto.setName("English");
        firstCourseDto.setDescription("Learn the language of shakespeare");
        var secondCourseDto = new CoursesDto();
        secondCourseDto.setId(2L);
        secondCourseDto.setName("French");
        secondCourseDto.setDescription("The language of Moliere");
        List<CoursesDto> coursesDtoList = new ArrayList<CoursesDto>() {
            {
                add(firstCourseDto);
                add(secondCourseDto);
            }
        };
        studentDto.setCourses(coursesDtoList);

        var student = new Student();
        student.setId(studentDto.getId());
        student.setName(studentDto.getName());
        student.setLastname(studentDto.getLastname());
        student.setEmail(studentDto.getEmail());
        student.setGrade(studentDto.getGrade());
        student.setDateBirth(LocalDate.parse(studentDto.getDateBirth()));
        var firstCourse = new Course();
        var secondCourse = new Course();
        firstCourse.setId(firstCourseDto.getId());
        firstCourse.setName(firstCourseDto.getName());
        firstCourse.setDescription(firstCourseDto.getDescription());
        secondCourse.setId(secondCourseDto.getId());
        secondCourse.setName(secondCourseDto.getName());
        secondCourse.setDescription(secondCourseDto.getDescription());
        List<Course> studentCourses = new ArrayList<Course>() {
            {
                add(firstCourse);
                add(secondCourse);
            }
        };
        student.setCourses(studentCourses);
        Mockito.when(studentMapper.toEntity(studentDto)).thenReturn(student);
        Mockito.when(studentMapper.toDto(student)).thenReturn(studentDto);
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        //WHEN
        studentDto = studentService.createStudent(studentDto);
        //THEN
        Mockito.verify(studentRepository).save(studentArgumentCaptor.capture());
        Student studentDtoSaved = studentArgumentCaptor.getValue();
        Assertions.assertEquals(studentDtoSaved.getId(), studentDto.getId());
        Assertions.assertEquals(studentDtoSaved.getName(), studentDto.getName());
        Assertions.assertEquals(studentDtoSaved.getLastname(), studentDto.getLastname());
        Assertions.assertEquals(studentDtoSaved.getEmail(), studentDto.getEmail());
        Assertions.assertEquals(studentDtoSaved.getGrade(), studentDto.getGrade());
        Boolean dateTest = studentDtoSaved.getDateBirth().isEqual(LocalDate.parse(studentDto.getDateBirth()));
        Assertions.assertEquals(dateTest, true);
        Assertions.assertEquals(studentDtoSaved.getCourses().size(), studentDto.getCourses().size());
        for (int i = 0; i < studentDto.getCourses().size(); i++) {
            Assertions.assertEquals(student.getCourses().get(i).getId(), studentDto.getCourses().get(i).getId());
            Assertions.assertEquals(student.getCourses().get(i).getName(), studentDto.getCourses().get(i).getName());
        }
    }

    @Test
    void GIVEN_StudentDto_WHEN_findStudentById_THEN_Should_find_on_database() {
        //GIVEN
        var studentDto = new StudentDto();
        studentDto.setId(1L);
        studentDto.setName("Joel");
        studentDto.setLastname("Gregor");
        studentDto.setGrade("Level 3");
        studentDto.setEmail("joel@hotmail.com");
        studentDto.setDateBirth("1980-12-12");
        var firstCourseDto = new CoursesDto();
        firstCourseDto.setId(1L);
        firstCourseDto.setName("English");
        firstCourseDto.setDescription("Learn the language of shakespeare");
        var secondCourseDto = new CoursesDto();
        secondCourseDto.setId(2L);
        secondCourseDto.setName("French");
        secondCourseDto.setDescription("The language of Moliere");
        List<CoursesDto> coursesDtoList = new ArrayList<CoursesDto>() {
            {
                add(firstCourseDto);
                add(secondCourseDto);
            }
        };
        studentDto.setCourses(coursesDtoList);

        var student = new Student();
        student.setId(studentDto.getId());
        student.setName(studentDto.getName());
        student.setLastname(studentDto.getLastname());
        student.setEmail(studentDto.getEmail());
        student.setGrade(studentDto.getGrade());
        student.setDateBirth(LocalDate.parse(studentDto.getDateBirth()));
        var firstCourse = new Course();
        var secondCourse = new Course();
        firstCourse.setId(firstCourseDto.getId());
        firstCourse.setName(firstCourseDto.getName());
        firstCourse.setDescription(firstCourseDto.getDescription());
        secondCourse.setId(secondCourseDto.getId());
        secondCourse.setName(secondCourseDto.getName());
        secondCourse.setDescription(secondCourseDto.getDescription());
        List<Course> studentCourses = new ArrayList<Course>() {
            {
                add(firstCourse);
                add(secondCourse);
            }
        };
        student.setCourses(studentCourses);
        Mockito.when(studentMapper.toDto(student)).thenReturn(studentDto);
        Mockito.when(studentRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(student));
        //WHEN
        studentDto = studentService.findStudentById(1L);
        //THEN
        Assertions.assertEquals(student.getId(), studentDto.getId());
        Assertions.assertEquals(student.getName(), studentDto.getName());
        Assertions.assertEquals(student.getLastname(), studentDto.getLastname());
        Assertions.assertEquals(student.getEmail(), studentDto.getEmail());
        Assertions.assertEquals(student.getGrade(), studentDto.getGrade());
        boolean testDateBirth = student.getDateBirth().isEqual(LocalDate.parse(studentDto.getDateBirth()));
        Assertions.assertEquals(true, testDateBirth);
        Assertions.assertEquals(student.getCourses().size(), studentDto.getCourses().size());
        for (int i = 0; i < studentDto.getCourses().size(); i++) {
            Assertions.assertEquals(student.getCourses().get(i).getId(), studentDto.getCourses().get(i).getId());
            Assertions.assertEquals(student.getCourses().get(i).getName(), studentDto.getCourses().get(i).getName());
            Assertions.assertEquals(student.getCourses().get(i).getDescription(), studentDto.getCourses().get(i).getDescription());
        }
    }

    @Test
    void GIVEN_2_Students_saved_WHEN_getAllStudents_with_findAll_then_should_return_Students_from_database() {
        //GIVEN
        var firstStudent = new Student();
        firstStudent.setId(1L);
        firstStudent.setName("Joel");
        firstStudent.setLastname("Gregor");
        firstStudent.setGrade("Level 3");
        firstStudent.setEmail("joel@hotmail.com");
        firstStudent.setDateBirth(LocalDate.parse("1980-12-12"));
        var firstCourse = new Course();
        firstCourse.setId(1L);
        firstCourse.setName("English");
        firstCourse.setDescription("Learn the language of shakespeare");
        var secondCourse = new Course();
        secondCourse.setId(2L);
        secondCourse.setName("French");
        secondCourse.setDescription("The language of Moliere");
        var coursesList = new ArrayList<Course>() {
            {
                add(firstCourse);
                add(secondCourse);
            }
        };
        firstStudent.setCourses(coursesList);

        var secondStudent = new Student();
        secondStudent.setId(2L);
        secondStudent.setName("Olga");
        secondStudent.setLastname("Atone");
        secondStudent.setEmail("olga@hotmail.com");
        secondStudent.setGrade("level 5");
        secondStudent.setDateBirth(LocalDate.parse("1990-10-15"));
        secondStudent.setCourses(coursesList);
        var listStudents = new ArrayList<Student>() {
            {
                add(firstStudent);
                add(secondStudent);
            }
        };

        var firstStudentDto = new StudentDto();
        firstStudentDto.setId(firstStudent.getId());
        firstStudentDto.setName(firstStudent.getName());
        firstStudentDto.setLastname(firstStudent.getLastname());
        firstStudentDto.setEmail(firstStudent.getEmail());
        firstStudentDto.setGrade(firstStudent.getGrade());
        firstStudentDto.setDateBirth(String.valueOf(firstStudent.getDateBirth()));
        var firstCourseDto = new CoursesDto();
        firstCourseDto.setId(firstCourse.getId());
        firstCourseDto.setName(firstCourse.getName());
        firstCourseDto.setDescription(firstCourse.getDescription());
        var secondCourseDto = new CoursesDto();
        secondCourseDto.setId(secondCourse.getId());
        secondCourseDto.setName(secondCourse.getName());
        secondCourseDto.setDescription(secondCourse.getDescription());
        List<CoursesDto> listCoursesDto = new ArrayList<CoursesDto>() {
            {
                add(firstCourseDto);
                add(secondCourseDto);
            }
        };

        firstStudentDto.setCourses(listCoursesDto);

        var secondStudentDto = new StudentDto();
        secondStudentDto.setId(secondStudent.getId());
        secondStudentDto.setName(secondStudent.getName());
        secondStudentDto.setLastname(secondStudent.getLastname());
        secondStudentDto.setEmail(secondStudent.getEmail());
        secondStudentDto.setGrade(secondStudent.getGrade());
        secondStudentDto.setDateBirth(String.valueOf(secondStudent.getDateBirth()));
        secondStudentDto.setCourses(listCoursesDto);
        List<StudentDto> listStudentsDto = new ArrayList<StudentDto>() {
            {
                add(firstStudentDto);
                add(secondStudentDto);
            }
        };
        Mockito.when(studentRepository.findAll()).thenReturn(listStudents);
        Mockito.when(studentMapper.toDtoList(listStudents)).thenReturn(listStudentsDto);
        // WHEN
        listStudentsDto = studentService.findAllStudents(null);
        // THEN
        Assertions.assertEquals(listStudentsDto.size(), listStudents.size());
        for (int i = 0; i < listStudentsDto.size(); i++) {
            Assertions.assertEquals(listStudentsDto.get(i).getId(), listStudents.get(i).getId());
            Assertions.assertEquals(listStudentsDto.get(i).getName(), listStudents.get(i).getName());
            Assertions.assertEquals(listStudentsDto.get(i).getLastname(), listStudents.get(i).getLastname());
            Assertions.assertEquals(listStudentsDto.get(i).getEmail(), listStudents.get(i).getEmail());
            Assertions.assertEquals(listStudentsDto.get(i).getGrade(), listStudents.get(i).getGrade());
            for (int j = 0; j < listStudents.get(i).getCourses().size(); j++) {
                Assertions.assertEquals(listStudentsDto.get(i).getCourses().get(j).getId(), listStudents.get(i).getCourses().get(j).getId());
                Assertions.assertEquals(listStudentsDto.get(i).getCourses().get(j).getName(), listStudents.get(i).getCourses().get(j).getName());
                Assertions.assertEquals(listStudentsDto.get(i).getCourses().get(j).getDescription(), listStudents.get(i).getCourses().get(j).getDescription());
            }
        }
    }

    @Test
    void GIVEN_2_Students_saved_WHEN_getAllStudents_with_findStudent_with_arg_then_should_return_Students_from_database() {
        //GIVEN
        var firstStudent = new Student();
        firstStudent.setId(1L);
        firstStudent.setName("Joel");
        firstStudent.setLastname("Gregor");
        firstStudent.setGrade("Level 3");
        firstStudent.setEmail("joel@hotmail.com");
        firstStudent.setDateBirth(LocalDate.parse("1980-12-12"));
        var firstCourse = new Course();
        firstCourse.setId(1L);
        firstCourse.setName("English");
        firstCourse.setDescription("Learn the language of shakespeare");
        var secondCourse = new Course();
        secondCourse.setId(2L);
        secondCourse.setName("French");
        secondCourse.setDescription("The language of Moliere");
        var coursesList = new ArrayList<Course>() {
            {
                add(firstCourse);
                add(secondCourse);
            }
        };
        firstStudent.setCourses(coursesList);

        var secondStudent = new Student();
        secondStudent.setId(2L);
        secondStudent.setName("Olga");
        secondStudent.setLastname("Atone");
        secondStudent.setEmail("olga@hotmail.com");
        secondStudent.setGrade("level 5");
        secondStudent.setDateBirth(LocalDate.parse("1990-10-15"));
        secondStudent.setCourses(coursesList);
        var listStudents = new ArrayList<Student>() {
            {
                add(firstStudent);
                add(secondStudent);
            }
        };

        var firstStudentDto = new StudentDto();
        firstStudentDto.setId(firstStudent.getId());
        firstStudentDto.setName(firstStudent.getName());
        firstStudentDto.setLastname(firstStudent.getLastname());
        firstStudentDto.setEmail(firstStudent.getEmail());
        firstStudentDto.setGrade(firstStudent.getGrade());
        firstStudentDto.setDateBirth(String.valueOf(firstStudent.getDateBirth()));
        var firstCourseDto = new CoursesDto();
        firstCourseDto.setId(firstCourse.getId());
        firstCourseDto.setName(firstCourse.getName());
        firstCourseDto.setDescription(firstCourse.getDescription());
        var secondCourseDto = new CoursesDto();
        secondCourseDto.setId(secondCourse.getId());
        secondCourseDto.setName(secondCourse.getName());
        secondCourseDto.setDescription(secondCourse.getDescription());
        List<CoursesDto> listCoursesDto = new ArrayList<CoursesDto>() {
            {
                add(firstCourseDto);
                add(secondCourseDto);
            }
        };

        firstStudentDto.setCourses(listCoursesDto);

        var secondStudentDto = new StudentDto();
        secondStudentDto.setId(secondStudent.getId());
        secondStudentDto.setName(secondStudent.getName());
        secondStudentDto.setLastname(secondStudent.getLastname());
        secondStudentDto.setEmail(secondStudent.getEmail());
        secondStudentDto.setGrade(secondStudent.getGrade());
        secondStudentDto.setDateBirth(String.valueOf(secondStudent.getDateBirth()));
        secondStudentDto.setCourses(listCoursesDto);
        List<StudentDto> listStudentsDto = new ArrayList<StudentDto>() {
            {
                add(firstStudentDto);
                add(secondStudentDto);
            }
        };
        Mockito.when(studentRepository.findStudentByNameContaining(Mockito.anyString())).thenReturn(listStudents);
        Mockito.when(studentMapper.toDtoList(listStudents)).thenReturn(listStudentsDto);
        // WHEN
        listStudentsDto = studentService.findAllStudents("joe");
        // THEN
        Assertions.assertEquals(listStudentsDto.size(), listStudents.size());
        for (int i = 0; i < listStudentsDto.size(); i++) {
            Assertions.assertEquals(listStudentsDto.get(i).getId(), listStudents.get(i).getId());
            Assertions.assertEquals(listStudentsDto.get(i).getName(), listStudents.get(i).getName());
            Assertions.assertEquals(listStudentsDto.get(i).getLastname(), listStudents.get(i).getLastname());
            Assertions.assertEquals(listStudentsDto.get(i).getEmail(), listStudents.get(i).getEmail());
            Assertions.assertEquals(listStudentsDto.get(i).getGrade(), listStudents.get(i).getGrade());
            for (int j = 0; j < listStudents.get(i).getCourses().size(); j++) {
                Assertions.assertEquals(listStudentsDto.get(i).getCourses().get(j).getId(), listStudents.get(i).getCourses().get(j).getId());
                Assertions.assertEquals(listStudentsDto.get(i).getCourses().get(j).getName(), listStudents.get(i).getCourses().get(j).getName());
                Assertions.assertEquals(listStudentsDto.get(i).getCourses().get(j).getDescription(), listStudents.get(i).getCourses().get(j).getDescription());
            }
        }
    }

    @Test
    void GIVEN_ID_Student_WHEN_deleteStudentById_then_should_delete_from_database() {
        //GIVEN
        long studentId = 1L;
        var firstStudent = new Student();
        firstStudent.setId(1L);
        firstStudent.setName("Joel");
        firstStudent.setLastname("Gregor");
        firstStudent.setGrade("Level 3");
        firstStudent.setEmail("joel@hotmail.com");
        firstStudent.setDateBirth(LocalDate.parse("1980-12-12"));
        var firstCourse = new Course();
        firstCourse.setId(1L);
        firstCourse.setName("English");
        firstCourse.setDescription("Learn the language of shakespeare");
        var secondCourse = new Course();
        secondCourse.setId(2L);
        secondCourse.setName("French");
        secondCourse.setDescription("The language of Moliere");
        var coursesList = new ArrayList<Course>() {
            {
                add(firstCourse);
                add(secondCourse);
            }
        };
        firstStudent.setCourses(coursesList);
        Mockito.when(studentRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(firstStudent));
        //WHEN
        studentService.deleteStudent(studentId);
        //then
        Mockito.verify(studentRepository).delete(studentArgumentCaptor.capture());
        var studentVerified = studentArgumentCaptor.getValue();
        Assertions.assertEquals(studentVerified.getId(), firstStudent.getId());
        Assertions.assertEquals(studentVerified.getName(), firstStudent.getName());
        Assertions.assertEquals(studentVerified.getLastname(), firstStudent.getLastname());
        Assertions.assertEquals(studentVerified.getEmail(), firstStudent.getEmail());
        Assertions.assertEquals(studentVerified.getGrade(), firstStudent.getGrade());
        boolean testDateBirth = studentVerified.getDateBirth().isEqual(firstStudent.getDateBirth());
        Assertions.assertEquals(testDateBirth, true);
        for (int i = 0; i < studentVerified.getCourses().size(); i++) {
            Assertions.assertEquals(studentVerified.getCourses().get(i).getId(), firstStudent.getCourses().get(i).getId());
            Assertions.assertEquals(studentVerified.getCourses().get(i).getName(), firstStudent.getCourses().get(i).getName());
            Assertions.assertEquals(studentVerified.getCourses().get(i).getDescription(), firstStudent.getCourses().get(i).getDescription());
        }
    }

    @Test
    void GIVEN_Student_WHEN_updateStudent_THEN_SHOULD_UPDATE_ON_DATABASE() {
        long studentId = 2L;
        var firstStudent = new Student();
        firstStudent.setId(1L);
        firstStudent.setName("Joel");
        firstStudent.setLastname("Gregor");
        firstStudent.setGrade("Level 3");
        firstStudent.setEmail("joel@hotmail.com");
        firstStudent.setDateBirth(LocalDate.parse("1980-12-12"));
        var firstCourse = new Course();
        firstCourse.setId(1L);
        firstCourse.setName("English");
        firstCourse.setDescription("Learn the language of shakespeare");
        var secondCourse = new Course();
        secondCourse.setId(2L);
        secondCourse.setName("French");
        secondCourse.setDescription("The language of Moliere");
        var coursesList = new ArrayList<Course>() {
            {
                add(firstCourse);
                add(secondCourse);
            }
        };
        firstStudent.setCourses(coursesList);
        var secondStudentDto = new StudentDto();
        secondStudentDto.setName("Olga");
        secondStudentDto.setLastname("Atone");
        secondStudentDto.setEmail("olga@hotmail.com");
        secondStudentDto.setGrade("level 5");
        secondStudentDto.setDateBirth("1990-10-15");
        var firstCourseDto = new CoursesDto();
        firstCourseDto.setId(1L);
        firstCourseDto.setName("German");
        firstCourseDto.setDescription("German is one of the Indo-European languages belonging to the Germanic language family.");
        var secondCoursesDto = new CoursesDto();
        secondCoursesDto.setId(2L);
        secondCoursesDto.setName("French");
        secondCoursesDto.setDescription("The language of Moliere");
        var coursesDtoList = new ArrayList<CoursesDto>() {
            {
                add(firstCourseDto);
                add(secondCoursesDto);
            }
        };
        secondStudentDto.setCourses(coursesDtoList);
        Mockito.when(studentMapper.toEntity(secondStudentDto)).thenReturn(firstStudent);
        Mockito.when(studentRepository.save(Mockito.any())).thenReturn(firstStudent);
        Mockito.when(studentMapper.toDto(firstStudent)).thenReturn(secondStudentDto);
        // WHEN
        var studentDtoUpdate = studentService.updateStudent(studentId, secondStudentDto);
        // THEN
        Assertions.assertEquals(studentDtoUpdate.getId(), secondStudentDto.getId());
        Assertions.assertEquals(studentDtoUpdate.getName(), secondStudentDto.getName());
        Assertions.assertEquals(studentDtoUpdate.getLastname(), secondStudentDto.getLastname());
        Assertions.assertEquals(studentDtoUpdate.getEmail(), secondStudentDto.getEmail());
        Assertions.assertEquals(studentDtoUpdate.getGrade(), secondStudentDto.getGrade());
        boolean testDateBirth = studentDtoUpdate.getDateBirth().equals(secondStudentDto.getDateBirth());
        Assertions.assertEquals(testDateBirth, true);
        for (int i = 0; i < secondStudentDto.getCourses().size(); i++) {
            Assertions.assertEquals(studentDtoUpdate.getCourses().get(i).getId(), secondStudentDto.getCourses().get(i).getId());
            Assertions.assertEquals(studentDtoUpdate.getCourses().get(i).getName(), secondStudentDto.getCourses().get(i).getName());
            Assertions.assertEquals(studentDtoUpdate.getCourses().get(i).getDescription(), secondStudentDto.getCourses().get(i).getDescription());
        }
    }

    @Test
    void Given_student_WHEN_toEntity_THEN_SHOULD_return_RuntimeException() {
        //GIVEN & WHEN
        var student = new Student();
        Mockito.when(studentRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(student));
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            studentService.findStudentById(null);
        });
        Assertions.assertEquals("STUDENT NOT FOUND", e.getMessage());
    }
}