package fr.gopartner.domaine.student;

import fr.gopartner.core.exception.StudentCourseException;
import fr.gopartner.core.rest.Codes;
import fr.gopartner.core.utils.StringUtils;
import fr.gopartner.dto.StudentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;


    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    private Student searchStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentCourseException(Codes.ERROR_STUDENT_NOT_FOUND));
        return student;
    }

    public StudentDto createStudent(StudentDto studentDto) {
        if (studentDto == null) {
            throw new StudentCourseException(Codes.ERROR_STUDENT_NOT_FOUND);
        }
        Student student = studentMapper.toEntity(studentDto);
        student = studentRepository.save(student);
        log.info("the student {} is saved successfully", student.getName());
        return studentMapper.toDto(student);
    }

    public StudentDto findStudentById(Long id) {
        Student student = searchStudent(id);
        log.info("The student {} you're looking for has been found", student.getName());
        return studentMapper.toDto(student);
    }

    public List<StudentDto> findAllStudents(String name) {
        List<Student> students;
        if (StringUtils.isNotNullOrNotEmpty(name)) {
            students = studentRepository.findStudentByNameContaining(name);
        } else {
            students = studentRepository.findAll();
        }
        log.info("All students  in the database {} are displayed", students.size());
        return studentMapper.toDtoList(students);
    }

    public void deleteStudent(Long id) {
        Student student = searchStudent(id);
        studentRepository.delete(student);
        log.info("the student with id {} has been successfully deleted", student.getName());
    }

    public StudentDto updateStudent(Long id, StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);
        student.setId(id);
        student = studentRepository.save(student);
        log.info("The student {} has been successfully changed", student.getName());
        return studentMapper.toDto(student);
    }
}
