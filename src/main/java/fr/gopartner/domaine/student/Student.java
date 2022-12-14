package fr.gopartner.domaine.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.gopartner.domaine.course.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private LocalDate dateBirth;
    private String email;
    private String grade;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "students_courses",
            joinColumns = {
                    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false, updatable = false)
            }
    )
    private List<Course> courses = new ArrayList<>();


}
