package fr.gopartner.domaine.student;

import fr.gopartner.core.exception.StudentCourseException;
import fr.gopartner.core.rest.Codes;
import fr.gopartner.core.utils.CollectionUtils;
import fr.gopartner.domaine.course.Course;
import fr.gopartner.domaine.course.CoursesMapper;
import fr.gopartner.dto.CoursesDto;
import fr.gopartner.dto.StudentDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentMapper {

    private final CoursesMapper coursesMapper;

    public StudentMapper(CoursesMapper coursesMapper) {
        this.coursesMapper = coursesMapper;
    }

    public Student toEntity(StudentDto studentDto, List<CoursesDto> coursesDtos) {
        if (studentDto == null) {
            throw new StudentCourseException(Codes.ERROR_STUDENT_NOT_FOUND);
        }
        Student student = Student.builder()
                .id(studentDto.getId())
                .name(studentDto.getName())
                .lastname(studentDto.getLastname())
                .email(studentDto.getEmail())
                .grade(studentDto.getGrade())
                .dateBirth(LocalDate.parse(studentDto.getDateBirth()))
                .courses(coursesMapper.toEntities(coursesDtos))
                .build();
        return student;
    }

    public Student toEntity(StudentDto studentDto) {
        if (studentDto == null) {
            throw new StudentCourseException(Codes.ERROR_STUDENT_NOT_FOUND);
        }
        List<CoursesDto> coursesDtos = studentDto.getCourses();
        Student student = Student.builder()
                .id(studentDto.getId())
                .name(studentDto.getName())
                .lastname(studentDto.getLastname())
                .email(studentDto.getEmail())
                .grade(studentDto.getGrade())
                .dateBirth(LocalDate.parse(studentDto.getDateBirth()))
                .courses(coursesMapper.toEntities(coursesDtos))
                .build();
        return student;


    }

    public StudentDto toDto(Student student) {
        if (student == null) {
            throw new StudentCourseException(Codes.ERROR_STUDENT_NOT_FOUND);
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
            throw new StudentCourseException(Codes.ERROR_STUDENTS_NOT_FOUND);
        }
        return students.stream().map(this::toDto).collect(Collectors.toList());
    }
}
