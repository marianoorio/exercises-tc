package orio.mariano.persistence;

import orio.mariano.domain.person.Student;

import java.util.List;

public interface StudentPersistenceLayer {

    List<Student> getAll();
}
