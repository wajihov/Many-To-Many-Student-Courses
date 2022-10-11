package fr.gopartner.domaine.course;

import fr.gopartner.core.utils.JsonUtils;
import fr.gopartner.dto.CoursesDto;
import org.hamcrest.core.IsAnything;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class CourseControllerTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    void GIVEN_courses_WHEN_getCourses_THEN_should_get_courses_from_database() throws Exception {
        //WHEN && THEN
        mockMvc.perform(get("/courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(IsAnything.anything()));
    }

    @Test
    void GIVEN_courseDto_WHEN_updateCourse_THEN_should_update_course() throws Exception {
        //GIVEN
        long courseId = 1L;
        CoursesDto coursesDto = new CoursesDto();
        coursesDto.setId(2L);
        coursesDto.setName("Physique");
        coursesDto.setDescription("the science of life");
        Mockito.when(courseService.updateCourse(Mockito.anyLong(), Mockito.any())).thenReturn(coursesDto);
        //WHEN && THEN
        mockMvc.perform(put("/courses/" + courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(coursesDto)))
                .andExpect(status().isNoContent())
                .andExpect(content().string(IsAnything.anything()));
    }

    @Test
    void GIVEN_courseDto_WHEN_createCourse_THEN_SHOULD_Get_Response_OK() throws Exception {
        //GIVEN
        CoursesDto coursesDto = new CoursesDto();
        coursesDto.setId(2L);
        coursesDto.setName("Theatre");
        coursesDto.setDescription("Add another culture");
        Mockito.when(courseService.createCourse(Mockito.any())).thenReturn(coursesDto);
        //WHEN & THEN
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(coursesDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(IsAnything.anything()));
    }

    @Test
    void GIVEN_courseId_WHEN_deleteCourseById_THEN_should_delete_Course_from_database() throws Exception {
        // GIVEN
        long courseId = 6L;
        //WHEN && THEN
        mockMvc.perform(delete("/courses/" + courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void GIVEN_courseId_WHEN_findCourseById_THEN_should_get_course_from_database() throws Exception {
        //GIVEN
        long courseId = 4L;
        //WHEN & THEN
        mockMvc.perform(get("/courses/" + courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


}