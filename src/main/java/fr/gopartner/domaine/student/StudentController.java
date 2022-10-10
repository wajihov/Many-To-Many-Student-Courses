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
        StudentDto studentDtoSaved = studentService.createStudent(studentDto);
        return new ResponseEntity<>(studentDtoSaved, HttpStatus.CREATED);
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
    public ResponseEntity<List<StudentDto>> findStudents(String name) {
        List<StudentDto> studentDtoList = studentService.findAllStudents(name);
        return new ResponseEntity<>(studentDtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StudentDto> updateStudent(Long id, StudentDto studentDto) {
        StudentDto changedStudent = studentService.updateStudent(id, studentDto);
        return new ResponseEntity<>(changedStudent, HttpStatus.OK);
    }


}
