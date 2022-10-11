package fr.gopartner.domaine.student;

import fr.gopartner.core.utils.JsonUtils;
import fr.gopartner.dto.CoursesDto;
import fr.gopartner.dto.StudentDto;
import org.hamcrest.core.IsAnything;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class StudentControllerTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void studentDto_WHEN_createStudent_THEN_SHOULD_Get_Response_CREATED() throws Exception {
        //GIVEN
        fr.gopartner.dto.StudentDto studentDto = new fr.gopartner.dto.StudentDto();
        studentDto.setId(1L);
        studentDto.setName("Stephane");
        studentDto.setLastname("Sharai");
        studentDto.setEmail("stephane@gmail.com");
        studentDto.setGrade("level 3");
        studentDto.setDateBirth("1999-12-01");

        CoursesDto firstCoursesDto = new CoursesDto();
        firstCoursesDto.setId(1L);
        firstCoursesDto.setName("English");
        firstCoursesDto.setName("Learn the language of shakespeare");

        CoursesDto secondCoursesDto = new CoursesDto();
        secondCoursesDto.setId(2L);
        secondCoursesDto.setName("Physique");
        secondCoursesDto.setDescription("the science of life");
        List<CoursesDto> coursesDtoList = new ArrayList<CoursesDto>() {
            {
                add(firstCoursesDto);
                add(secondCoursesDto);
            }
        };
        studentDto.setCourses(coursesDtoList);

        Mockito.when(studentService.createStudent(Mockito.any())).thenReturn(studentDto);
        //WHEN && THEN
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(studentDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(IsAnything.anything()));
    }

    @Test
    void GIVEN_studentId_WHEN_deleteStudentById_THEN_should_delete_student_from_database() throws Exception {
        //GIVEN
        long studentId = 3L;
        //WHEN && THEN
        mockMvc.perform(delete("/students/" + studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void GIVEN_studentId_WHEN_findStudentById_THEN_should_get_student_from_database() throws Exception {
        //GIVEN
        long studentId = 2L;
        //WHEN && THEN
        mockMvc.perform(get("/students/" + studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void GIVEN_studentDtoList_WHEN_getStudents_THEN_should_get_studentsList_from_database() throws Exception {
        //GIVEN
        StudentDto studentDto = new StudentDto();
        studentDto.setId(1L);
        studentDto.setName("Stephane");
        studentDto.setLastname("Sharai");
        studentDto.setEmail("stephane@gmail.com");
        studentDto.setGrade("level 3");
        studentDto.setDateBirth("1999-12-01");

        CoursesDto firstCourseDto = new CoursesDto();
        firstCourseDto.setId(1L);
        firstCourseDto.setName("English");
        firstCourseDto.setName("Learn the language of shakespeare");

        CoursesDto secondCourseDto = new CoursesDto();
        secondCourseDto.setId(2L);
        secondCourseDto.setName("Physique");
        secondCourseDto.setDescription("the science of life");
        List<CoursesDto> coursesDtoList = new ArrayList<CoursesDto>() {
            {
                add(firstCourseDto);
                add(secondCourseDto);
            }
        };
        studentDto.setCourses(coursesDtoList);

        StudentDto secondStudentDto = new StudentDto();
        secondStudentDto.setId(2L);
        secondStudentDto.setName("Joe");
        secondStudentDto.setLastname("Hart");
        secondStudentDto.setEmail("joyhart@gmail.com");
        secondStudentDto.setGrade("level 2");
        secondStudentDto.setDateBirth("1996-04-06");
        secondStudentDto.setCourses(coursesDtoList);

        List<StudentDto> studentsListDto = new ArrayList<StudentDto>()  {
            {
                add(studentDto);
                add(secondStudentDto);
            }
        };
        Mockito.when(studentService.findAllStudents(Mockito.anyString())).thenReturn(studentsListDto);
        //
        mockMvc.perform(get("/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(IsAnything.anything()));
    }

    @Test
    void GIVEN_studentDto_WHEN_updateStudent_THEN_should_update_student() throws Exception {
        //GIVEN
        long studentId = 3L;
        fr.gopartner.dto.StudentDto studentDto = new fr.gopartner.dto.StudentDto();
        studentDto.setId(1L);
        studentDto.setName("Stephane");
        studentDto.setLastname("Sharai");
        studentDto.setEmail("stephane@gmail.com");
        studentDto.setGrade("level 3");
        studentDto.setDateBirth("1999-12-01");

        CoursesDto firstCourseDto = new CoursesDto();
        firstCourseDto.setId(1L);
        firstCourseDto.setName("English");
        firstCourseDto.setName("Learn the language of shakespeare");

        CoursesDto secondCourseDto = new CoursesDto();
        secondCourseDto.setId(2L);
        secondCourseDto.setName("Physique");
        secondCourseDto.setDescription("the science of life");
        List<CoursesDto> coursesDtoList = new ArrayList<CoursesDto>() {
            {
                add(firstCourseDto);
                add(secondCourseDto);
            }
        };
        studentDto.setCourses(coursesDtoList);
        Mockito.when(studentService.updateStudent(Mockito.anyLong(), Mockito.any())).thenReturn(studentDto);
        //WHEN && THEN
        mockMvc.perform(put("/students/" + studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(studentDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(IsAnything.anything()));
    }
}