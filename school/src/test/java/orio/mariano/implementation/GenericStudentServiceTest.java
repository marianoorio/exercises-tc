package orio.mariano.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import orio.mariano.domain.person.Student;
import orio.mariano.persistence.StudentPersistenceLayer;
import orio.mariano.service.implementation.GenericStudentService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class GenericStudentServiceTest {

    @Mock
    private StudentPersistenceLayer persistenceLayer;

    @InjectMocks
    private GenericStudentService underTest;

    @Test
    public void testGetAllGroupedByLastNamePrefixWhenEmptyStudentList() {
        when(persistenceLayer.getAll()).thenReturn(new LinkedList<>());

        Map<Character, Set<Student>> result = underTest.getAllGroupedByLastNamePrefix();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void testGetAllGroupedByLastNamePrefixWhenThrowsException() {
        when(persistenceLayer.getAll()).thenThrow(new RuntimeException("Forcing persistence layer to throw an exception"));

        underTest.getAllGroupedByLastNamePrefix();
    }

    @Test
    public void testGetAllGroupedByLastNamePrefixWhenStudentsArePresent() {

        List<Student> students = Arrays.asList(
            createSimpleStudent(1, "John", "Doe"),
            createSimpleStudent(2, "Larry", "Smith"),
            createSimpleStudent(3, "Mary", "Smith"),
            createSimpleStudent(3, "Mary", "Smith"),
            createSimpleStudent(4, "Dudley", "Dursley"),
            createSimpleStudent(5, "Leonel", "Messi")
        );
        when(persistenceLayer.getAll()).thenReturn(students);

        Map<Character, Set<Student>> result = underTest.getAllGroupedByLastNamePrefix();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(2, result.get('D').size());
        assertEquals(2, result.get('S').size());
        assertEquals(1, result.get('M').size());
    }

    private Student createSimpleStudent(Integer studentId, String firstName, String lastName) {
        Student student = new Student();
        student.setId(studentId);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        return student;
    }
}