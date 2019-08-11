package org.ecore.filestorage;


	import java.io.File;
	import java.nio.file.Path;
	import java.nio.file.Paths;
	import java.util.UUID;

	import org.springframework.stereotype.Component;
	import org.springframework.web.multipart.MultipartFile;

	@Component
	public class ResourceUploadService {
		
		// String uploadDirectory = "C: find path name for user machine and insert here";
		//then change uploadMultipartfile and getUploadedFile from getUploadDirectory() to uploadDirectory

		private String getUploadDirectory() {
			String userHomeDirectory = System.getProperty("user.home");
			String uploadDirectory = Paths.get(userHomeDirectory, "spring-uploads").toString();
			
			new File(uploadDirectory).mkdirs();
			
			return uploadDirectory;
		}

		private String getFileExtension(String fileName) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		}

		public String uploadMultipartFile(MultipartFile imageFile) throws Exception {
			// Upload image - stream uploaded data to a temporary file
			String fileName = imageFile.getOriginalFilename();

			// Transfer the temporary file to its permanent location
			String fileExtension = getFileExtension(fileName);
			String virtualFileUrl = UUID.randomUUID().toString() + "." + fileExtension;
			File fileUpload = new File(getUploadDirectory(), virtualFileUrl);
			imageFile.transferTo(fileUpload);

			return virtualFileUrl;
		}

		public File getUploadedFile(String fileName) {
			Path filePath = Paths.get(getUploadDirectory(), fileName);
			String filePathString = filePath.toString();
			return new File(filePathString);
		}

	}