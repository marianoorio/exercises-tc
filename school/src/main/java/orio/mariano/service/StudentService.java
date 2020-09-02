package orio.mariano.service;


import orio.mariano.domain.person.Student;

import java.util.Map;
import java.util.Set;

public interface StudentService {

    Map<Character, Set<Student>> getAllGroupedByLastNamePrefix();

}
