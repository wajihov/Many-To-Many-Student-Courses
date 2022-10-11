package fr.gopartner.domaine.student;

import fr.gopartner.core.exception.StudentCourseException;
import fr.gopartner.core.rest.Codes;
import fr.gopartner.core.utils.StringUtils;
import fr.gopartner.domaine.course.CourseService;
import fr.gopartner.dto.CoursesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final CourseService courseService;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.courseService = courseService;
    }

    private Student searchStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentCourseException(Codes.ERROR_STUDENT_NOT_FOUND));
        return student;
    }

    public fr.gopartner.dto.StudentDto createStudent(fr.gopartner.dto.StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);
        student = studentRepository.save(student);
        log.info("the student is saved successfully");
        return studentMapper.toDto(student);
    }

    public fr.gopartner.dto.StudentDto findStudentById(Long id) {
        Student student = searchStudent(id);
        log.info("The student you're looking for has been found");
        return studentMapper.toDto(student);
    }

    public List<fr.gopartner.dto.StudentDto> findAllStudents(String name) {
        List<Student> students;
        if (StringUtils.isNotNullOrNotEmpty(name)) {
            students = studentRepository.findStudentByNameContaining(name);
        } else {
            students = studentRepository.findAll();
        }
        log.info("All students in the database are displayed");
        return studentMapper.toDtoList(students);
    }

    public void deleteStudent(Long id) {
        Student student = searchStudent(id);
        studentRepository.delete(student);
        log.info("the student with id {} has been successfully deleted", id);
    }

    public fr.gopartner.dto.StudentDto updateStudent(Long id, fr.gopartner.dto.StudentDto studentDto) {
        List<CoursesDto> coursesDtoList = studentDto.getCourses();
        coursesDtoList = coursesDtoList.stream().map(coursesDto ->
                courseService.updateCourse(coursesDto.getId(), coursesDto)).collect(Collectors.toList());
        Student student = studentMapper.toEntity(studentDto, coursesDtoList);
        student.setId(id);
        student = studentRepository.save(student);
        log.info("The student with ID {} has been successfully changed", id);
        return studentMapper.toDto(student);
    }
}
