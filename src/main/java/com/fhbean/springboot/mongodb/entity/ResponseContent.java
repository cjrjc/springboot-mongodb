package com.fhbean.springboot.mongodb.entity;

import java.util.Date;

public class ResponseContent {
	
	private Date ctime;
	private String content;
	
	public ResponseContent() {
		super();
	}
	
	public ResponseContent(Date ctime, String content) {
		super();
		this.ctime = ctime;
		this.content = content;
	}

	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
