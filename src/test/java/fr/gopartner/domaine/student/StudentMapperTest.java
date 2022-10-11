package fr.gopartner.domaine.student;

import fr.gopartner.domaine.course.Course;
import fr.gopartner.dto.CoursesDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class StudentMapperTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    void Given_student_WHEN_toDto_THEN_SHOULD_return_studentDto() {
        //GIVEN
        Student student = new Student();
        student.setId(1L);
        student.setName("Stephane");
        student.setLastname("Sharai");
        student.setEmail("stephane@gmail.com");
        student.setGrade("level 3");
        student.setDateBirth(LocalDate.parse("1999-12-01"));

        Course course = new Course();
        course.setId(1L);
        course.setName("English");
        course.setDescription("Studying the language of the shakespeare");

        student.setCourses(new ArrayList<Course>() {
            {
                add(course);
            }
        });
        //WHEN
        fr.gopartner.dto.StudentDto studentDto = studentMapper.toDto(student);
        //THEN
        Assertions.assertEquals(student.getId(), studentDto.getId());
        Assertions.assertEquals(student.getName(), studentDto.getName());
        Assertions.assertEquals(student.getLastname(), studentDto.getLastname());
        Assertions.assertEquals(student.getGrade(), studentDto.getGrade());
        Assertions.assertEquals(student.getDateBirth(), LocalDate.parse(studentDto.getDateBirth()));
        Assertions.assertEquals(student.getEmail(), studentDto.getEmail());
        Assertions.assertEquals(student.getCourses().size(), studentDto.getCourses().size());
    }

    @Test
    void Given_studentDto_WHEN_toStudent_THEN_SHOULD_return_student() {
        //GIVEN
        fr.gopartner.dto.StudentDto studentDto = new fr.gopartner.dto.StudentDto();
        studentDto.setId(2L);
        studentDto.setName("jack");
        studentDto.setLastname("Jacopo");
        studentDto.setGrade("level 2");
        studentDto.setDateBirth("1997-12-05");

        CoursesDto coursesDto = new CoursesDto();
        coursesDto.setId(3L);
        coursesDto.setName("French");
        coursesDto.setDescription("the language of Moliere");

        studentDto.setCourses(new ArrayList<CoursesDto>() {
            {
                add(coursesDto);
            }
        });
        //WHEN
        Student student = studentMapper.toEntity(studentDto);
        //THEN
        Assertions.assertEquals(student.getId(), studentDto.getId());
        Assertions.assertEquals(student.getName(), studentDto.getName());
        Assertions.assertEquals(student.getLastname(), studentDto.getLastname());
        Assertions.assertEquals(student.getGrade(), studentDto.getGrade());
        Assertions.assertEquals(student.getEmail(), studentDto.getEmail());
        Assertions.assertEquals(student.getDateBirth(), LocalDate.parse(studentDto.getDateBirth()));
        Assertions.assertEquals(student.getCourses().size(), studentDto.getCourses().size());
    }

    @Test
    void Given_students_WHEN_toDtoList_THEN_SHOULD_return_studentsList() {
        //GIVEN
        Student firstStudent = new Student();
        firstStudent.setId(1L);
        firstStudent.setName("Stephane");
        firstStudent.setLastname("Sharai");
        firstStudent.setEmail("stephane@gmail.com");
        firstStudent.setGrade("level 3");
        firstStudent.setDateBirth(LocalDate.parse("1999-12-01"));

        Student secondStudent = new Student();
        secondStudent.setId(2L);
        secondStudent.setName("Joe");
        secondStudent.setLastname("Hart");
        secondStudent.setEmail("joyhart@gmail.com");
        secondStudent.setGrade("level 2");
        secondStudent.setDateBirth(LocalDate.parse("1996-04-06"));

        Course course = new Course();
        course.setId(1L);
        course.setName("English communication");
        course.setDescription("Studying the language of the shakespeare");
        Course course2 = new Course();
        course2.setId(2L);
        course2.setName("French");
        course2.setDescription("the language of Moliere");

        firstStudent.setCourses(new ArrayList<Course>() {
            {
                add(course);
                add(course2);
            }
        });
        secondStudent.setCourses(new ArrayList<Course>() {
            {
                add(course);
                add(course2);
            }
        });
        List<Student> studentList = new ArrayList<>();
        studentList.add(firstStudent);
        studentList.add(secondStudent);
        //WHEN
        List<fr.gopartner.dto.StudentDto> studentDtoList = studentMapper.toDtoList(studentList);
        //THEN
        Assertions.assertEquals(studentList.size(), studentDtoList.size());
        for (int i = 0; i < studentDtoList.size(); i++) {
            Assertions.assertEquals(studentList.get(i).getId(), studentDtoList.get(i).getId());
            Assertions.assertEquals(studentList.get(i).getName(), studentDtoList.get(i).getName());
            Assertions.assertEquals(studentList.get(i).getLastname(), studentDtoList.get(i).getLastname());
            Assertions.assertEquals(studentList.get(i).getGrade(), studentDtoList.get(i).getGrade());
            Assertions.assertEquals(studentList.get(i).getEmail(), studentDtoList.get(i).getEmail());
            Boolean dateTest = studentList.get(i).getDateBirth().isEqual(LocalDate.parse(studentDtoList.get(i).getDateBirth()));
            Assertions.assertEquals(dateTest, true);
            for (int j = 0; j < studentDtoList.get(i).getCourses().size(); j++) {
                Assertions.assertEquals(studentList.get(i).getCourses().get(j).getId(), studentDtoList.get(i).getCourses().get(j).getId());
                Assertions.assertEquals(studentList.get(i).getCourses().get(j).getName(), studentDtoList.get(i).getCourses().get(j).getName());
                Assertions.assertEquals(studentList.get(i).getCourses().get(j).getDescription(), studentDtoList.get(i).getCourses().get(j).getDescription());
            }
        }
    }

    @Test
    void Given_listStudent_WHEN_toDtoList_THEN_SHOULD_return_RuntimeException() {
        //GIVEN
        List<Student> students = new ArrayList<>();
        //WHEN
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            studentMapper.toDtoList(students);
        });
        Assertions.assertEquals("STUDENTS NOT FOUND", e.getMessage());
    }

    @Test
    void Given_Student_WHEN_toDto_THEN_SHOULD_return_RuntimeException() {
        //GIVEN & WHEN
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            studentMapper.toDto(null);
        });
        Assertions.assertEquals("STUDENT NOT FOUND", e.getMessage());
    }

    @Test
    void Given_StudentDto_WHEN_toEntity_THEN_SHOULD_return_RuntimeException() {
        //GIVEN & WHEN
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            studentMapper.toEntity(null);
        });
        Assertions.assertEquals("STUDENT NOT FOUND", e.getMessage());
    }

    @Test
    void Given_StudentDto_WHEN_toEntity_have_2_Param_THEN_SHOULD_return_RuntimeException() {
        //GIVEN
        List<CoursesDto> coursesDtoList = new ArrayList<>();
        //WHEN
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            studentMapper.toEntity(null, coursesDtoList);
        });
        Assertions.assertEquals("STUDENT NOT FOUND", e.getMessage());
    }


}

