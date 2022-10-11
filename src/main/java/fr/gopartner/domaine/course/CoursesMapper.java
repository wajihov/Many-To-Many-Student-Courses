package fr.gopartner.domaine.course;

import fr.gopartner.core.exception.StudentCourseException;
import fr.gopartner.core.rest.Codes;
import fr.gopartner.core.utils.CollectionUtils;
import fr.gopartner.dto.CoursesDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoursesMapper {

    public Course toEntity(CoursesDto coursesDto) {
        if (coursesDto == null) {
            throw new StudentCourseException(Codes.ERROR_COURSE_NOT_FOUND);
        }
        return Course.builder()
                .id(coursesDto.getId())
                .name(coursesDto.getName())
                .description(coursesDto.getDescription())
                .build();
    }

    public CoursesDto toDto(Course course) {
        if (course == null) {
            throw new StudentCourseException(Codes.ERROR_COURSE_NOT_FOUND);
        }
        return CoursesDto.builder()
                .id(course.getId())
                .name(course.getName())
                .description((course.getDescription()))
                .build();
    }

    public List<CoursesDto> toDtoList(List<Course> courses) {
        if (CollectionUtils.isNullOrEmpty(courses)) {
            throw new StudentCourseException(Codes.ERROR_COURSES_NOT_FOUND);
        }
        return courses.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Course> toEntities(List<CoursesDto> coursesDtoList) {
        if (CollectionUtils.isNullOrEmpty(coursesDtoList)) {
            throw new StudentCourseException(Codes.ERROR_COURSES_NOT_FOUND);
        }
        return coursesDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

}
