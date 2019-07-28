package org.ecore.mvcTest;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.controller.MaterialController;
import org.ecore.model.Material;
import org.ecore.repository.MaterialRepository;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import static org.hamcrest.Matchers.*;


import org.ecore.controller.CommunityMemberContoller;
import org.ecore.model.CommunityMember;
import org.ecore.repository.CommunityMemberRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MaterialController.class)

public class MaterialMockMvcTest {
	
	@Resource
	private MockMvc mvc;
	
	@MockBean
	private MaterialRepository materialRepo;
	
	@Mock
	private Material material;
	
	@Mock
	private Material anotherMaterial;
	
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
