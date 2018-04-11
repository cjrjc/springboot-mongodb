package com.fhbean.springboot.mongodb.service;

import java.util.List;

import com.fhbean.springboot.mongodb.entity.ResourceComment;

public interface ResourceCommentService {
	
	/**
	 * 查询某资源的所有评论
	 * 
	 * @param resourceId
	 * @return
	 */
	public List<ResourceComment> findResourceComment(Long resourceId);

	/**
	 * 对指定资源发表评论
	 * 
	 * @param resourceId      资源ID
	 * @param commentUserId   用户ID
	 * @param commentUserName 用户名
	 * @param commentContent  评论内容
	 * @return 1成功，0失败
	 */
	public int saveComment(Long resourceId, Long commentUserId, String commentUserName, String commentContent);
	
	/**
	 * 回复评论
	 * 
	 * @param id                 原评论的ID
	 * @param responseUserId     回复评论的用户ID
	 * @param responseUserName   回复评论的用户名
	 * @param responseContent    回复的内容
	 * @return 1成功，0失败
	 */
	public int saveResponse(String id, Long responseUserId, String responseUserName, String responseContent);

	/**
	 * 一楼评论者 回复二楼评论者 的评论
	 * 
	 * @param id                 原评论的ID
	 * @param responseUserId     回复评论的用户ID
	 * @param index              要回复的评论索引值，从0开始
	 * @param replyContent       一楼评论者 回复的内容
	 * @return 1成功，0失败
	 */
	public int saveReply(String id, Long responseUserId, int index, String replyContent);
}
