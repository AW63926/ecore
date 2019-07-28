package org.ecore.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.ecore.filestorage.StorageException;
import org.ecore.filestorage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



@Entity
public class Material implements StorageService{
	
	@GeneratedValue	
	@Id
	long id;
	private String name;
	
	private int quantity;
	
	private String descMaterial;
	
	private Path rootLocation;
	
	@ManyToMany
	private Collection<Tag> tags;

	public Material(String name, int quantity, String descMaterial,Tag...tags) {
		this.name = name;
		this.quantity = quantity;
		this.descMaterial = descMaterial;
		this.tags = new HashSet<> (Arrays.asList(tags));
		
	}
	
	

	public long getId() {
		
		return id;
	}

	public String getName() {
		
		return name;
	}
	
	public int getQuantity() {
		
		return quantity;
	}
	
	public String getDescMaterial () {
		
		return descMaterial;
	}

	public Collection<Tag> getTags() {
		
		return tags;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Material other = (Material) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public Stream<Path> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path load(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource loadAsResource(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}
	
	 @Override
	    public void store(MultipartFile file) {
	        String filename = StringUtils.cleanPath(file.getOriginalFilename());
	        try {
	            if (file.isEmpty()) {
	                throw new StorageException("Failed to store empty file " + filename);
	            }
	            if (filename.contains("..")) {
	                // This is a security check
	                throw new StorageException(
	                        "Cannot store file with relative path outside current directory "
	                                + filename);
	            }
	            try (InputStream inputStream = file.getInputStream()) {
	                Files.copy(inputStream, this.rootLocation.resolve(filename),
	                    StandardCopyOption.REPLACE_EXISTING);
	            }
	        }
	        catch (IOException e) {
	            throw new StorageException("Failed to store file " + filename, e);
	        }
	    }

}
