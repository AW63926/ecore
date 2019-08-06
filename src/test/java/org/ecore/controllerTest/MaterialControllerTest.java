package org.ecore.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.ecore.controller.MaterialController;
import org.ecore.model.Material;
import org.ecore.model.Tag;
import org.ecore.model.Teacher;
import org.ecore.notFoundException.MaterialNotFoundException;
import org.ecore.repository.MaterialRepository;
import org.ecore.repository.TagRepository;
import org.ecore.repository.TeacherRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

public class MaterialControllerTest {
	
	@InjectMocks
	private MaterialController underTest;
	
	@Mock
	private MaterialRepository materialRepo;
	
	@Mock
	private Material material;
	private Long id;
	
	@Mock
	private Material anotherMaterial;
	
	@Mock
	Model model;
	
	@Mock
	private TeacherRepository teacherRepo;
	
	@Mock
	private TagRepository tagRepo;
	
	
	@Mock
	private Teacher teacher;
	
	@Mock
	private Tag tag;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldAddOneMaterialToModel() throws MaterialNotFoundException{
		long arbitraryMaterialId = 1;
		when(materialRepo.findById(arbitraryMaterialId)).thenReturn(Optional.of(material));
		
		underTest.findOneMaterial(arbitraryMaterialId, model);
		verify(model).addAttribute("material", material);
				
	}
	
	@Test
	public void shouldAddAllMaterialsTomodel() {
		Collection<Material> allMaterials = Arrays.asList(material, anotherMaterial);
		when(materialRepo.findAll()).thenReturn(allMaterials);
		underTest.findAllMaterials(model);
		verify(model).addAttribute("materials", allMaterials);
		
	}
	
	@Test
	public void shouldAddAdditonalMaterialToModelbyName() {
		String materialName = "name";
		int quantity = 1;
		String materialDesc = "desc";
		String teacherName = "teacher";
		String tagName = "tag name";
		
		when(teacherRepo.findByNameIgnoreCaseLike(teacherName)).thenReturn(teacher);
		underTest.addMaterial(materialName, quantity, materialDesc, teacherName, tagName);
		ArgumentCaptor<Material> materialArgument = ArgumentCaptor.forClass(Material.class);
		verify(materialRepo).save(materialArgument.capture());
		assertEquals("name", materialArgument.getValue().getName());		
		
	}
	
	@Test
	public void shouldDeleteMaterialById() {
		underTest.deleteMaterialById(id);
		verify(materialRepo).deleteById(id);
		
	}
	
}