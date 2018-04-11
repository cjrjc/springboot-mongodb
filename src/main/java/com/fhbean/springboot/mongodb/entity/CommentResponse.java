package com.fhbean.springboot.mongodb.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public class CommentResponse {
	/**
	 * 回复评论的用户ID
	 */
	@Field(value = "response_user_id")
	private Long responseUserId;
	
	/**
	 * 回复评论的用户名
	 */
	@Field(value = "response_user_name")
	private String responseUserName;
	
	/**
	 * 回复的评论内容
	 */
	@Field(value = "response_contents")
	private ResponseContent[] responseContents;
	
	/**
	 * 一楼评论作者 对 回复评论者 的回复
	 */
	@Field(value = "get_replys")
	private ResponseContent[] getReplys;
	
	public Long getResponseUserId() {
		return responseUserId;
	}
	public void setResponseUserId(Long responseUserId) {
		this.responseUserId = responseUserId;
	}
	public String getResponseUserName() {
		return responseUserName;
	}
	public void setResponseUserName(String responseUserName) {
		this.responseUserName = responseUserName;
	}
	public ResponseContent[] getResponseContents() {
		return responseContents;
	}
	public void setResponseContents(ResponseContent[] responseContents) {
		this.responseContents = responseContents;
	}
	public ResponseContent[] getGetReplys() {
		return getReplys;
	}
	public void setGetReplys(ResponseContent[] getReplys) {
		this.getReplys = getReplys;
	}

}
