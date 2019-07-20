package org.ecore;

import javax.annotation.Resource;

import org.ecore.model.CommunityMember;
import org.ecore.model.Need;
import org.ecore.model.School;
import org.ecore.model.Tag;
import org.ecore.model.Teacher;
import org.ecore.repository.CommunityMemberRepository;
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
	
	@Override
	public void run(String... args) throws Exception{
		CommunityMember member1 = communityMemberRepo.save(new CommunityMember("member1", "email1"));
		
		CommunityMember member2 = communityMemberRepo.save(new CommunityMember("member2", "email2"));
		
		Need need1 = needRepo.save(new Need("need1", 1, "desc1"));
		Need need2 = needRepo.save(new Need("need2", 1, "desc2"));
		
		School school1 = schoolRepo.save(new School("school1", "district1", "address1", "mapUrl1"));
		School school2 = schoolRepo.save(new School("school2", "district2", "address2", "mapUrl2"));
		
		Tag tag1 = tagRepo.save(new Tag("tag1"));
		Tag tag2 = tagRepo.save(new Tag("tag2"));
		
		Teacher teacher1 = teacherRepo.save(new Teacher("name1", "specialty1", school1));
		Teacher teacher2 = teacherRepo.save(new Teacher("name2", "specialty2", school2));
		Teacher teacher3 = teacherRepo.save(new Teacher("name3", "specialty1", school1));
		Teacher teacher4 = teacherRepo.save(new Teacher("name4", "specialty2", school2));
	}
}
