package orio.mariano.service;


import orio.mariano.domain.person.Student;

import java.util.Set;

public interface CourseService {

    Set<Student> getAllStudentsBySubject(Integer subjectId);

}
