package org.ecore.mvcTest;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.controller.MaterialController;
import org.ecore.filestorage.StorageService;
import org.ecore.model.Material;
import org.ecore.model.Tag;
import org.ecore.model.Teacher;
import org.ecore.repository.MaterialRepository;
import org.ecore.repository.TagRepository;
import org.ecore.repository.TeacherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(MaterialController.class)

public class MaterialMockMvcTest {
	
	@Resource
	private MockMvc mvc;
	
	@MockBean
	private MaterialRepository materialRepo;
	
	@MockBean
	private TeacherRepository teacherRepo;
	
	@MockBean
	private TagRepository tagRepo;
	
	@MockBean
	private StorageService storage;
	
	@Mock
	private Material material;
	
	@Mock
	private Material anotherMaterial;
	
	@Mock
	private Tag tag;
	
	
	@Mock
	private Teacher teacher;
	
	
	@Test
	public void shouldRouteToSingleMaterialView() throws Exception {
		long materialId = 1;
		when(materialRepo.findById(materialId)).thenReturn(Optional.of(material));
		
		mvc.perform(get("/material?id=1")).andExpect(view().name(is("material")));
	}
	
	@Test
	public void shouldRouteToSingleMaterial () throws Exception {
		long materialId = 1;
		when(materialRepo.findById(materialId)).thenReturn(Optional.of(material));
		
		mvc.perform(get("/material?id=1")).andExpect(status().isOk());
		
	}
	
	@Test
	public void shouldNotRouteToSingleMaterialView () throws Exception{
		mvc.perform(get("/material?id=1")).andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldPutSingleMaterialIntoModel () throws Exception {
		when(materialRepo.findById(1l)).thenReturn(Optional.of(material));
		
		mvc.perform(get("/material?id=1")).andExpect(model().attribute("material", is(material)));
		
	}
	
	@Test
	public void shouldRouteToAllMaterials () throws Exception {
		mvc.perform(get("/all-materials")).andExpect(view().name(is("all-materials")));
	}
	
	@Test
	public void shouldRouteToAllMaterialsView () throws Exception {
		mvc.perform(get("/all-materials")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldAddAllMaterialsToModel () throws Exception {
		Collection<Material> allMaterials = Arrays.asList(material, anotherMaterial);
		when(materialRepo.findAll()).thenReturn(allMaterials);
		
		mvc.perform(get("/all-materials")).andExpect(model().attribute("materials", is(allMaterials)));
	}

}
