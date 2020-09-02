package orio.mariano.service.implementation;

import orio.mariano.domain.person.Student;
import orio.mariano.persistence.CoursePersistenceLayer;
import orio.mariano.service.CourseService;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GenericCourseService implements CourseService {

    private CoursePersistenceLayer persistenceLayer;

    public GenericCourseService(CoursePersistenceLayer coursePersistenceLayer) {
        this.persistenceLayer = coursePersistenceLayer;
    }

    @Override
    public Set<Student> getAllStudentsBySubject(Integer subjectId) {

        if (Objects.isNull(subjectId)) {
            return Collections.emptySet();
        }

        return persistenceLayer.getAll().stream()
            .filter(course -> Objects.nonNull(course.getSubject()))
            .filter(course -> subjectId.equals(course.getSubject().getSubjectId()))
            .filter(course -> Objects.nonNull(course.getStudents()))
            .flatMap(course -> course.getStudents().stream())
            .collect(Collectors.toSet());
    }
}
