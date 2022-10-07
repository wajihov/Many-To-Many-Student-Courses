package fr.gopartner.domaine.student;

import fr.gopartner.core.utils.CollectionUtils;
import fr.gopartner.domaine.course.Course;
import fr.gopartner.domaine.course.CoursesMapper;
import fr.gopartner.dto.CoursesDto;
import fr.gopartner.dto.StudentDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentMapper {

    private final CoursesMapper coursesMapper;

    public StudentMapper(CoursesMapper coursesMapper) {
        this.coursesMapper = coursesMapper;
    }

    public Student toEntity(StudentDto studentDto) {
        if (studentDto == null) {
            return null;
        }
        List<CoursesDto> coursesDtos = studentDto.getCourses();
        Student student = Student.builder()
                .id(studentDto.getId())
                .name(studentDto.getName())
                .lastname(studentDto.getLastname())
                .email(studentDto.getEmail())
                .grade(studentDto.getGrade())
                .dateBirth(LocalDateTime.parse(studentDto.getDateBirth()))
                .courses(coursesMapper.toEntities(coursesDtos))
                .build();
        return student;


    }

    public StudentDto toDto(Student student) {
        if (student == null) {
            return null;
        }
        List<Course> courses = student.getCourses();
        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .lastname(student.getLastname())
                .dateBirth(String.valueOf(student.getDateBirth()))
                .email(student.getEmail())
                .grade(student.getGrade())
                .courses(coursesMapper.toDtos(courses))
                .build();
    }

    public List<StudentDto> toDtos(List<Student> students) {
        if (CollectionUtils.isNullOrEmpty(students)) {
            return null;
        }
        return students.stream().map(this::toDto).collect(Collectors.toList());
    }
}
