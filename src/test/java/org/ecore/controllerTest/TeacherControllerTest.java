package org.ecore.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.ecore.controller.TeacherController;
import org.ecore.model.School;
import org.ecore.model.Teacher;
import org.ecore.notFoundException.TeacherNotFoundException;
import org.ecore.repository.SchoolRepository;
import org.ecore.repository.TeacherRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;

public class TeacherControllerTest {
	
	@InjectMocks
	private TeacherController underTest;
	
	@Mock
	private Teacher teacher;
	Long teacherId;
	
	@Mock
	private TeacherRepository teacherRepo;
	
	@Mock
	private SchoolRepository schoolRepo;
	
		
	@Mock
	private Model model;
	
	@Mock
	private Teacher anotherTeacher;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldAddSingleTeacherToModel() throws TeacherNotFoundException {
		long arbitraryTeacherId = 1;
		when(teacherRepo.findById(arbitraryTeacherId)).thenReturn(Optional.of(teacher));
		
		underTest.findOneTeacher(arbitraryTeacherId, model);
		verify(model).addAttribute("teachers", teacher);
		
	}
	
	@Test
	public void shouldAddMultipleTeachersToModel() {
		Collection<Teacher> allTeachers = Arrays.asList(teacher, anotherTeacher);
		when(teacherRepo.findAll()).thenReturn(allTeachers);
	
		underTest.findAllTeachers(model);
		verify(model).addAttribute("teachers", allTeachers);
	}
	
	@Test
	public void shouldAddAdditionalTeachersToModel() {
		String schoolName = "school name";
		String teacherName = "new teacher";
		String teacherSpecialty = "teacher specialty";
		String email = "email";
		underTest.addTeacher(teacherName, teacherSpecialty, schoolName, email);
		
		ArgumentCaptor<Teacher> teacherArgument = ArgumentCaptor.forClass(Teacher.class);
		verify(teacherRepo).save(teacherArgument.capture());
		assertEquals("new teacher", teacherArgument.getValue().getName());
	}
	
	@Test
	public void shouldRemoveTeacherFromModelByName() {
		String teacherName = teacher.getName();
		when(teacherRepo.findByNameIgnoreCaseLike(teacherName)).thenReturn(teacher);
		underTest.deleteTeacherByName(teacherName);
		verify(teacherRepo).delete(teacher);
	}
	
	@Test
	public void shouldRemoveTeacherFromModelById() {
		underTest.deleteTeacherById(teacherId);
		verify(teacherRepo).deleteById(teacherId);
	}

}
