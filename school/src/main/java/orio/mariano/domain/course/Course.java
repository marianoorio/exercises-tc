package orio.mariano.domain.course;

import lombok.Getter;
import lombok.Setter;
import orio.mariano.domain.person.Student;

import java.util.List;

@Getter
@Setter

public class Course {

    private Integer courseId;
    private Subject subject;
    private List<Student> students;
}
