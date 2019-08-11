package org.ecore.fileupload;

import java.io.File;
import java.io.FileInputStream;

import java.io.InputStream;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;


import org.ecore.fileupload.FileUploadController;
import org.ecore.model.Material;
import org.ecore.model.PostedResource;

import org.ecore.repository.MaterialRepository;
import org.ecore.repository.PostedResourceRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.ecore.filestorage.ResourceUploadService;


@Controller
public class FileUploadController {

  
    
    @Autowired
    MaterialRepository materialRepo;
    
    @Autowired
    PostedResourceRepository postedResourceRepo;
    
    @Autowired
    ResourceUploadService uploader;

    @PostMapping("/add-item")
	public String addResource(@RequestParam(value = "id") long id,
			@RequestParam(value = "file") MultipartFile file) throws Exception {
    	
    	Material material = materialRepo.findById(id).get();
		String virtualFileUrl = uploader.uploadMultipartFile(file);
		
		postedResourceRepo.save(new PostedResource("/uploads/" + virtualFileUrl, material));

		return "redirect:/material?id=" + id;
	}
	
	//this deals with file upload

	@GetMapping("/uploads/{file:.+}") // allows "." to be part of PathVariable instead of truncating it
	public void serveImage( HttpServletResponse response,
			@PathVariable("file") String fileName) throws Exception {

		// Resolve path of requested file
		File requestedFile = uploader.getUploadedFile(fileName);


		// Serve file by streaming it directly to the response
		InputStream in = new FileInputStream(requestedFile);
		IOUtils.copy(in, response.getOutputStream());
	}
}
