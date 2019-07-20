package org.ecore.controllerTest;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Collection;
import java.util.Optional;
import java.util.Arrays;

import org.ecore.controller.TeacherController;
import org.ecore.model.Teacher;
import org.ecore.notFoundException.TeacherNotFoundException;
import org.ecore.repository.TeacherRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
	
	
	

}
