package fr.gopartner.domaine.course;

import fr.gopartner.core.utils.CollectionUtils;
import fr.gopartner.dto.CoursesDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoursesMapper {

    public Course toEntity(CoursesDto coursesDto) {
        if (coursesDto == null) {
            return null;
        }
        return Course.builder()
                .id(coursesDto.getId())
                .name(coursesDto.getName())
                .description(coursesDto.getDescription())
                .build();
    }

    public CoursesDto toDto(Course course) {
        if (course == null) {
            return null;
        }
        return CoursesDto.builder()
                .id(course.getId())
                .name(course.getName())
                .description((course.getDescription()))
                .build();
    }

    public List<CoursesDto> toDtos(List<Course> courses) {
        if (CollectionUtils.isNullOrEmpty(courses)) {
            return null;
        }
        return courses.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Course> toEntities(List<CoursesDto> coursesDtos) {
        if (CollectionUtils.isNullOrEmpty(coursesDtos)) {
            return null;
        }
        return coursesDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

}
