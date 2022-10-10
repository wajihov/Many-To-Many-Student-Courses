package fr.gopartner.domaine.student;

import fr.gopartner.StudentsApiDelegate;
import fr.gopartner.dto.StudentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController implements StudentsApiDelegate {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public ResponseEntity<StudentDto> createStudent(StudentDto studentDto) {
        return new ResponseEntity<>(studentService.createStudent(studentDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteStudent(Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StudentDto> findStudentById(Long id) {
        StudentDto studentDto = studentService.findStudentById(id);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<StudentDto>> findStudentByName(String name) {
        List<StudentDto> list = studentService.searchStudentByName(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<StudentDto>> findStudents() {
        List<StudentDto> list = studentService.findAllStudents();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StudentDto> updateStudent(Long id, StudentDto studentDto) {
        StudentDto changedStudent = studentService.updateStudent(id, studentDto);
        return new ResponseEntity<>(changedStudent, HttpStatus.OK);
    }
}
