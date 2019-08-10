package org.ecore.mvcTest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.controller.SchoolController;
import org.ecore.filestorage.StorageService;
import org.ecore.model.School;
import org.ecore.model.Teacher;
import org.ecore.repository.MaterialRepository;
import org.ecore.repository.NeedRepository;
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
@WebMvcTest(SchoolController.class)
public class SchoolMockMvcTest {

	@Resource
	private MockMvc mvc;

	@MockBean
	private SchoolRepository schoolRepo;
	
	@MockBean
	private TeacherRepository teacherRepo;
	
	@MockBean
	private MaterialRepository materialRepo;
	
	@MockBean
	private NeedRepository needRepo;
	
	@MockBean
	private StorageService storage;
	
	@MockBean
	private Teacher teacher;

	@MockBean
	private School school;

	@Mock
	private School anotherSchool;

	@Test
	public void shouldRouteToSingleSchool() throws Exception {
		long schoolId = 1;
		when(schoolRepo.findById(schoolId)).thenReturn(Optional.of(school));

		mvc.perform(get("/school?id=1")).andExpect(view().name(is("school")));

	}

	@Test
	public void shouldBeOkForSingleSchool() throws Exception {
		long schoolId = 1;
		when(schoolRepo.findById(schoolId)).thenReturn(Optional.of(school));

		mvc.perform(get("/school?id=1")).andExpect(status().isOk());

	}

	@Test
	public void shouldNotBeOkForSingleSchool() throws Exception {

		mvc.perform(get("/school?id=1")).andExpect(status().isNotFound());

	}

	@Test
	public void shouldPutSingleSchoolIntoModel() throws Exception {
		when(schoolRepo.findById(1L)).thenReturn(Optional.of(school));

		mvc.perform(get("/school?id=1")).andExpect(model().attribute("schools", is(school)));

	}

	@Test
	public void shouldRouteToAllSchoolsView() throws Exception {
		mvc.perform(get("/all-schools")).andExpect(view().name(is("all-schools")));
	}

	@Test
	public void shouldPutAllSchoolsIntoModel() throws Exception {
		Collection<School> allSchools = Arrays.asList(school, anotherSchool);
		when(schoolRepo.findAll()).thenReturn(allSchools);

		mvc.perform(get("/all-schools")).andExpect(model().attribute("schools", allSchools));
	}
}
