package org.ecore.JpaTests;

import java.util.Optional;

import javax.annotation.Resource;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.ecore.filestorage.StorageService;
import org.ecore.model.CommunityMember;
import org.ecore.repository.CommunityMemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest

public class CommunityMemberJpaTest {
	
	@Resource
	CommunityMemberRepository communityMemberRepo;
	
	@MockBean
	private StorageService storage;
	
	@Resource
	TestEntityManager entityManager;

	@Test
	public void shouldSaveAndLoadCommnunityMember() {
		CommunityMember communityMember = communityMemberRepo.save(new CommunityMember("name", "email"));
		long communityMemberId = communityMember.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<CommunityMember> result = communityMemberRepo.findById(communityMemberId);
		communityMember = result.get();
		assertThat(communityMember.getName(), is("name"));
	}
	
	@Test
	public void shouldGenerateCommunityMemberId() {
		CommunityMember communityMember = communityMemberRepo.save(new CommunityMember("name", "email"));
		long communityMemberId = communityMember.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(communityMemberId, is(greaterThan(0L)));
	}
}
