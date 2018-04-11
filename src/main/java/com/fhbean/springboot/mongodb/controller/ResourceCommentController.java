package com.fhbean.springboot.mongodb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fhbean.springboot.mongodb.service.ResourceCommentService;

@RestController
@RequestMapping(value = "/rc")
public class ResourceCommentController {

	@Autowired
	private ResourceCommentService resourceCommentService;
	
	
	/**
	 * 列出某个资源的所有评论
	 * 
	 * 客户端页面要做到这些权限限制
	 * 三级按钮：发表评论btn1，回复评论btn2，回复评论的评论btn3
	 * 当前登录用户是本条评论的第一级评论者，
	 *     可以看到发表评论按钮btn1
	 *     不可以看到第二级的回复按钮btn2，即不能给自己的评论回复
	 *     可以看到第三级的按钮btn3，这个按钮可以给回复评论的人回复评论
	 * 
	 * 当前登录用户是本条评论的第二级评论者，即回复评论者
	 *     可以看到发表评论按钮btn1
	 *     可以看到第二级的回复按钮btn2，即可以给他人的评论回复
	 *     不可以看到第三级的按钮btn3，即不可以给自己的回复发表回复评论
	 *     
	 * 当前登录用户不是本条评论的第一级和第二级用户    
	 *     可以看到发表评论按钮btn1
	 *     可以看到第二级的回复按钮btn2，即可以给他人的评论回复
	 *     不可以看到第三级的按钮btn3，只有版主才可以回复评论的评论
	 * 
	 * @param resourceId
	 * @return
	 * @throws Exception
	 * 
	 * example http://localhost:8080/rc/listComments/1
	 */
	@ResponseBody
	@RequestMapping(value = "/listComments/{resourceId}")
	public Object listComments(@PathVariable(value = "resourceId", required = true)Long resourceId) throws Exception {
		
		return resourceCommentService.findResourceComment(resourceId);
	}
	
	/**
	 * 发表评论
	 * @param resourceId
	 * @param commentUserId
	 * @param commentUserName
	 * @param commentContent
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/saveComment")
	public Object saveComment(
			@RequestParam(value = "resourceId", required = true)Long resourceId, 
			@RequestParam(value = "commentUserId", required = true)Long commentUserId, 
			@RequestParam(value = "commentUserName", required = true)String commentUserName, 
			@RequestParam(value = "commentContent", required = true)String commentContent) throws Exception {
		return resourceCommentService.saveComment(resourceId, commentUserId, commentUserName, commentContent);
	}
	
	/**
	 * 回复评论
	 * @param id
	 * @param responseUserId
	 * @param responseUserName
	 * @param responseContent
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResponse")
	public Object saveResponse(
			@RequestParam(value = "id", required = true)String id, 
			@RequestParam(value = "responseUserId", required = true)Long responseUserId, 
			@RequestParam(value = "responseUserName", required = true)String responseUserName, 
			@RequestParam(value = "responseContent", required = true)String responseContent) throws Exception {

		return resourceCommentService.saveResponse(id, responseUserId, responseUserName, responseContent);
	}
	
	/**
	 * 回复评论的评论
	 *     
	 * @param id
	 * @param responseUserId
	 * @param index
	 * @param replyContent
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/saveReply")
	public Object saveReply(
			@RequestParam(value = "id", required = true)String id, 
			@RequestParam(value = "responseUserId", required = true)Long responseUserId, 
			@RequestParam(value = "index", required = true)Integer index, 
			@RequestParam(value = "replyContent", required = true)String replyContent) throws Exception {
		if (null == index) {
			index = 0;
		}
		return resourceCommentService.saveReply(id, responseUserId, index, replyContent);
	}
}
