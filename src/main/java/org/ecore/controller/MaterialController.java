package org.ecore.controller;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.filestorage.StorageService;
import org.ecore.model.Material;
import org.ecore.model.Tag;
import org.ecore.notFoundException.MaterialNotFoundException;
import org.ecore.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MaterialController {

	@Resource
	MaterialRepository materialRepo;
	
	@Autowired
	StorageService storageService;
	
	

	@RequestMapping("/material")
	public String findOneMaterial(@RequestParam(value = "id") long id, Model model) throws MaterialNotFoundException {

		Optional<Material> material = materialRepo.findById(id);
		if (material.isPresent()) {
			model.addAttribute("material", material.get());
			return "material";
		}

		throw new MaterialNotFoundException();
	}

	@RequestMapping("/all-materials")
	public String findAllMaterials(Model model) {
		model.addAttribute("materials", materialRepo.findAll());
		return "all-materials";

	}

	@RequestMapping("/add-material")
	public String addMaterial(@RequestParam("file") MultipartFile file, String name, int quantity, String descMaterial,RedirectAttributes redirectAttributes) {
		Material material = materialRepo.findByName(name);

		if (material == null) {

			material = materialRepo.save(new Material(name, quantity, descMaterial));

		}
		material.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
		
		
		return "redirect:/all-materials";
	}

	@RequestMapping("/delete-material")
	public String deleteMaterialById(Long id) {

		materialRepo.deleteById(id);

		return "redirect:/all-materials";

	}
}
