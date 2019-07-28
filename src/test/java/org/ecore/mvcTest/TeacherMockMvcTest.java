package org.ecore.mvcTest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import java.util.Arrays;
import org.ecore.controller.TeacherController;
import org.ecore.model.Teacher;
import org.ecore.repository.SchoolRepository;
import org.ecore.repository.TeacherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(TeacherController.class)

public class TeacherMockMvcTest {
	
	@Resource
	private MockMvc mvc;
	
	@MockBean
	private TeacherRepository teacherRepo;
	
	@MockBean
	SchoolRepository schoolRepo;
	
	@Mock
	private Teacher teacher;
	
	@Mock
	private Teacher teacher2;
	
	@Test
	public void shouldRouteToSingleTeacherView() throws Exception{
		long arbitraryTeacherId = 1;
		when(teacherRepo.findById(arbitraryTeacherId)).thenReturn(Optional.of(teacher));
		mvc.perform(get("/teacher?id=1")).andExpect(view().name(is("teacher")));
	}
	
	@Test
	public void shouldBeOkForSingleTeacher() throws Exception{
		long arbitraryTeacherId = 1;
		when(teacherRepo.findById(arbitraryTeacherId)).thenReturn(Optional.of(teacher));
		mvc.perform(get("/teacher?id=1")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldNotBeOkForSingleCourse() throws Exception {
		mvc.perform(get("/course?id")).andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldPutSingleTeacherIntoModel() throws Exception {
		when(teacherRepo.findById(1L)).thenReturn(Optional.of(teacher));
		mvc.perform(get("/teacher?id=1")).andExpect(model().attribute("teachers", is(teacher)));
	}
	
	@Test
	public void shouldRouteToAllTeachersView() throws Exception {
		mvc.perform(get("/all-teachers")).andExpect(view().name(is("all-teachers")));
	}
	
	@Test
	public void shouldBeOkForAllTeachers() throws Exception{
		mvc.perform(get("/all-teachers")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldPutlAllTeachersInModel() throws Exception {
		Collection<Teacher>allTeachers=Arrays.asList(teacher, teacher2);
		when(teacherRepo.findAll()).thenReturn(allTeachers);
		mvc.perform(get("/all-teachers")).andExpect(model().attribute("teachers", is(allTeachers)));
		
	}

}
