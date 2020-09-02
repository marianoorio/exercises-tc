package orio.mariano.persistence;


import orio.mariano.domain.course.Course;

import java.util.List;

public interface CoursePersistenceLayer {

    List<Course> getAll();
}
