package orio.mariano.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import orio.mariano.domain.course.Course;
import orio.mariano.domain.course.Subject;
import orio.mariano.domain.person.Student;
import orio.mariano.persistence.CoursePersistenceLayer;
import orio.mariano.service.implementation.GenericCourseService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class GenericCourseServiceTest {

    @Mock
    private CoursePersistenceLayer persistenceLayer;

    @InjectMocks
    private GenericCourseService underTest;

    @Test
    public void testGetAllStudentsBySubjectWhenEmptyStudentList() {
        when(persistenceLayer.getAll()).thenReturn(new LinkedList<>());

        Set<Student> result = underTest.getAllStudentsBySubject(1);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllStudentsBySubjectWhenNullSubjectId() {
        when(persistenceLayer.getAll()).thenReturn(new LinkedList<>());

        Set<Student> result = underTest.getAllStudentsBySubject(null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void testGetAllStudentsBySubjectWhenThrowsException() {
        when(persistenceLayer.getAll()).thenThrow(new RuntimeException("Forcing persistence layer to throw an exception"));

        underTest.getAllStudentsBySubject(1);
    }

    @Test
    public void testGetAllStudentsBySubjectWhenStudentsArePresent() {

        Subject searchedSubject = new Subject();
        searchedSubject.setSubjectId(1);

        Subject notSearchedSubject = new Subject();
        notSearchedSubject.setSubjectId(2);

        Student student1 = createSimpleStudent(1, "John", "Doe");
        Student student2 = createSimpleStudent(2, "Larry", "Smith");
        Student student3 = createSimpleStudent(3, "Mary", "Smith");
        Student student4 = createSimpleStudent(4, "Dudley", "Dursley");
        Student student5 = createSimpleStudent(5, "Leonel", "Messi");

        Course courseWithNullSubject = new Course();
        courseWithNullSubject.setCourseId(1);
        courseWithNullSubject.setStudents(Collections.singletonList(createSimpleStudent(1, "John", "Doe")));

        Course courseWithValidSubjectAndNullStudents = new Course();
        courseWithValidSubjectAndNullStudents.setCourseId(2);
        courseWithValidSubjectAndNullStudents.setSubject(searchedSubject);

        Course course3 = new Course();
        course3.setCourseId(3);
        course3.setSubject(searchedSubject);
        course3.setStudents(Arrays.asList(student1, student4, student5));

        Course course4 = new Course();
        course4.setCourseId(4);
        course4.setSubject(searchedSubject);
        course4.setStudents(Arrays.asList(student1, student2));

        Course course5 = new Course();
        course5.setCourseId(5);
        course5.setSubject(searchedSubject);
        course5.setStudents(Arrays.asList(student4, student3, student5));

        when(persistenceLayer.getAll()).thenReturn(Arrays.asList(courseWithNullSubject,
            courseWithValidSubjectAndNullStudents,
            course3,
            course4,
            course5));

        Set<Student> result = underTest.getAllStudentsBySubject(1);

        assertNotNull(result);
        assertEquals(5, result.size());
        assertTrue(result.contains(student1));
        assertTrue(result.contains(student2));
        assertTrue(result.contains(student3));
        assertTrue(result.contains(student4));
        assertTrue(result.contains(student5));
    }

    private Student createSimpleStudent(Integer studentId, String firstName, String lastName) {
        Student student = new Student();
        student.setId(studentId);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        return student;
    }
}