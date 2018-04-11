package com.fhbean.springboot.mongodb.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 
resource_comment {
	id:,
	resourceId:,
	commentUserId:,
	commentUserName:,
	commentContent:,
	ctime:,
	status:,
	commentResponses:[
      {
    	  responseUserId:,
    	  responseUserName:,
    	  responseContents:[
                {ctime:, content:},
                {ctime:, content:},
                {ctime:, content:}
    	  ],
    	  getReplys:[
                {ctime:, content:},
                {ctime:, content:},
                {ctime:, content:}
    	  ]
      },
      {
    	  responseUserId:,
    	  responseUserName:,
    	  responseContents:[
                {ctime:, content:},
                {ctime:, content:},
                {ctime:, content:}
    	  ],
    	  getReplys:[
                {ctime:, content:},
                {ctime:, content:},
                {ctime:, content:}
    	  ]
      }
	]
}

 */
@Document(collection = "resource_comment")
public class ResourceComment {
	@Id
	private String id;
	
	/**
	 * 接收评论的资源ID或作品ID
	 */
	@Indexed
	@Field(value = "resource_id")
	private Long resourceId;
	
	/**
	 * 发表评论的用户ID
	 */
	@Indexed
	@Field(value = "comment_user_id")
	private Long commentUserId;
	
	/**
	 * 发表评论的用户名
	 */
	@Field(value = "comment_user_name")
	private String commentUserName;
	
	/**
	 * 发表的评论内容
	 */
	@Field(value = "comment_content")
	private String commentContent;
	
	/**
	 * 发表评论的时间
	 */
	private Date ctime;
	
	/**
	 * 此条评论的状态:0为正常....
	 */
	private String status;
	
	/**
	 * 对此评论的回复
	 */
	@Field(value = "comment_responses")
	private CommentResponse[] commentResponses;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public Long getCommentUserId() {
		return commentUserId;
	}
	public void setCommentUserId(Long commentUserId) {
		this.commentUserId = commentUserId;
	}
	public String getCommentUserName() {
		return commentUserName;
	}
	public void setCommentUserName(String commentUserName) {
		this.commentUserName = commentUserName;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public CommentResponse[] getCommentResponses() {
		return commentResponses;
	}
	public void setCommentResponses(CommentResponse[] commentResponses) {
		this.commentResponses = commentResponses;
	}

}
