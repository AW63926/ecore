package org.ecore.controllerTest;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.Arrays;
import java.util.Collection;

import org.ecore.controller.SchoolController;
import org.ecore.model.CommunityMember;
import org.ecore.model.School;
import org.ecore.notFoundException.SchoolNotFoundException;
import org.ecore.repository.SchoolRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

public class SchoolControllerTest {
	
	@InjectMocks
	private SchoolController underTest;
	
	@Mock
	private School school;
	Long schoolId;
	
	@Mock
	private School anotherSchool;
	
	@Mock
	private SchoolRepository schoolRepo;
	
	@Mock
	private Model model;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldAddASingleSchoolToModel() throws SchoolNotFoundException { 
		long arbitrarySchoolId = 1;
		when(schoolRepo.findById(arbitrarySchoolId)).thenReturn(Optional.of(school));
	
		underTest.findOneSchool(arbitrarySchoolId, model);
		verify(model).addAttribute("schools", school);
	}
	
	@Test
	public void shouldAddMultipleSchoolsToModel() {
		Collection<School> allSchools = Arrays.asList(school, anotherSchool);
		when(schoolRepo.findAll()).thenReturn(allSchools);
		
		underTest.findAllSchools(model);
		verify(model).addAttribute("schools", allSchools);
	}
	
	@Test
	public void shouldAddAdditionalSchoolToModel() {
		String name = "name";
		String district = "district";
		String address = "address";
		String mapUrl = "mapUrl";
		underTest.addSchool(name, district, address, mapUrl);
		ArgumentCaptor<School> schoolArgument = ArgumentCaptor.forClass(School.class);
		verify(schoolRepo).save(schoolArgument.capture());
		assertEquals("name", schoolArgument.getValue().getName());
	}
	
	@Test
	public void shouldDeleteSchoolFromModel() {
		String schoolName = school.getName();
		when(schoolRepo.getByNameIgnoreCaseLike(schoolName)).thenReturn(school);
		underTest.deleteSchool(schoolName);
		verify(schoolRepo).delete(school);
	}
}
