package orio.mariano.service.implementation;

import orio.mariano.domain.person.Student;
import orio.mariano.persistence.StudentPersistenceLayer;
import orio.mariano.service.StudentService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GenericStudentService implements StudentService {

    private StudentPersistenceLayer persistenceLayer;

    public GenericStudentService(StudentPersistenceLayer studentPersistenceLayer) {
        this.persistenceLayer = studentPersistenceLayer;
    }

    @Override
    public Map<Character, Set<Student>> getAllGroupedByLastNamePrefix() {
        Map<Character, Set<Student>> groupedStudents = new HashMap<>();

        persistenceLayer.getAll().forEach(student ->
            groupedStudents.computeIfAbsent(student.getLastName().charAt(0), key -> new HashSet<>())
                .add(student));

        return groupedStudents;
    }

}
