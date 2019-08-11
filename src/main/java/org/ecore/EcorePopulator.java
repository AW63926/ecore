package org.ecore;

import javax.annotation.Resource;

import org.ecore.model.CommunityMember;
import org.ecore.model.Material;
import org.ecore.model.Need;
import org.ecore.model.School;
import org.ecore.model.Tag;
import org.ecore.model.Teacher;
import org.ecore.repository.CommunityMemberRepository;
import org.ecore.repository.MaterialRepository;
import org.ecore.repository.NeedRepository;
import org.ecore.repository.SchoolRepository;
import org.ecore.repository.TagRepository;
import org.ecore.repository.TeacherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EcorePopulator implements CommandLineRunner{

	@Resource 
	private CommunityMemberRepository communityMemberRepo;
	
	@Resource
	private NeedRepository needRepo;
	
	@Resource
	private SchoolRepository schoolRepo;
	
	@Resource
	private TagRepository tagRepo;
	 
	@Resource
	private TeacherRepository teacherRepo;
	
	@Resource
	private MaterialRepository materialRepo;
	
	
	private Teacher teacher;
	
	
	private Tag tag;
	
	@Override
	public void run(String... args) throws Exception{
		CommunityMember member1 = communityMemberRepo.save(new CommunityMember("member1", "email1"));
		
		CommunityMember member2 = communityMemberRepo.save(new CommunityMember("member2", "email2"));
		


		School school1 = schoolRepo.save(new School("Middleboro High School", "Middleboro City Schools", "2900 N. High St Middleboro OH 44455", "mapUrl1"));
		School school2 = schoolRepo.save(new School("New Berlin Middle School", "Wayne County Local Schools", "14500 Main St East City Oh 44499", "mapUrl2"));
		
		Tag tag1 = tagRepo.save(new Tag("pencils"));
		Tag tag2 = tagRepo.save(new Tag("paper"));

		
		Teacher teacher1 = teacherRepo.save(new Teacher("Ryan Kelley", "English", school1, "kelley.ryanj@gmail.com"));
		Teacher teacher2 = teacherRepo.save(new Teacher("Baker Mayfield", "PE", school2, "brownsboss@gmail.com"));
		Teacher teacher3 = teacherRepo.save(new Teacher("Justin Fields", "PE", school1, "ousman@osu.edu"));
		Teacher teacher4 = teacherRepo.save(new Teacher("name4", "specialty2", school2, "email4"));
		


		Need need1 = needRepo.save(new Need("need1", 1, "desc1", teacher1, tag));
		Need need2 = needRepo.save(new Need("need2", 1, "desc2", teacher2, tag));

		
		Material material1 = materialRepo.save(new Material("material1", 1, "desc1", teacher1, tag));

		Material material2 = materialRepo.save(new Material("material2", 2, "desc2", teacher2, tag));
	}
}
