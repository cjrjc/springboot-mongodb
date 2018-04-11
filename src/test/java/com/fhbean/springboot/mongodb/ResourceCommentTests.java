package com.fhbean.springboot.mongodb;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import com.fhbean.springboot.mongodb.entity.ResourceComment;
import com.fhbean.springboot.mongodb.reposistory.ResourceCommentRepository;
import com.fhbean.springboot.mongodb.service.ResourceCommentService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceCommentTests {

	@Autowired
	private ResourceCommentRepository resourceCommentRepository;
	
	@Autowired
	private ResourceCommentService resourceCommentService;
	
	@Before
	public void setUp() {
		
	}
	
	/**
	 * 测试评论
	 */
	@Test
	public void testSaveComment() {
		Long resourceId = 1L;
		Long commentUserId = 1L;
		String commentUserName = "user" + commentUserId;
		String commentContent = commentUserName + "'s comment on " + System.currentTimeMillis();
		resourceCommentService.saveComment(resourceId, commentUserId, commentUserName, commentContent);
	}
	
	/**
	 * 测试评论回复
	 */
	@Test
	public void testSaveResponse() {
		String id = "5acdae25c986d7518c1a7081";
		Long responseUserId = 2L;
		String responseUserName = "user" + responseUserId;
		String responseContent = responseUserName + "'s response on " + System.currentTimeMillis();
		
		resourceCommentService.saveResponse(id, responseUserId, responseUserName, responseContent);
	}
	
	/**
	 * 测试回复评论的评论
	 */
	@Test
	public void testSaveReply() {
		String id = "5acdae25c986d7518c1a7081";
		Long responseUserId = 2L;
		int index = 1;
		String replyContent = "user"+responseUserId+" reply index["+index+"] on " + System.currentTimeMillis();
		
		resourceCommentService.saveReply(id, responseUserId, index, replyContent);
	}
	
	@Test
	public void testFind() {
		ResourceComment rc = new ResourceComment();
		rc.setResourceId(1L);
		rc.setCommentUserId(1L);
		Example<ResourceComment> example = Example.of(rc);
		Optional<ResourceComment> optional = resourceCommentRepository.findOne(example);
		Assert.assertTrue(optional.isPresent());
		Assert.assertEquals("user1", optional.get().getCommentUserName());
	}
	
	
}
