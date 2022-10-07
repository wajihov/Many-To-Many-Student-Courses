package fr.gopartner.domaine.student;

import fr.gopartner.StudentsApiDelegate;
import fr.gopartner.dto.StudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController implements StudentsApiDelegate {

    @Override
    public ResponseEntity<StudentDto> createStudent(StudentDto studentDto) {
        return StudentsApiDelegate.super.createStudent(studentDto);
    }

    @Override
    public ResponseEntity<Void> deleteStudent(Long id) {
        return StudentsApiDelegate.super.deleteStudent(id);
    }

    @Override
    public ResponseEntity<StudentDto> findStudentById(Long id) {
        return StudentsApiDelegate.super.findStudentById(id);
    }

    @Override
    public ResponseEntity<List<StudentDto>> findStudentByName(String name) {
        return StudentsApiDelegate.super.findStudentByName(name);
    }

    @Override
    public ResponseEntity<List<StudentDto>> findStudents() {
        return StudentsApiDelegate.super.findStudents();
    }

    @Override
    public ResponseEntity<StudentDto> updateStudent(Long id, StudentDto studentDto) {
        return StudentsApiDelegate.super.updateStudent(id, studentDto);
    }
}
