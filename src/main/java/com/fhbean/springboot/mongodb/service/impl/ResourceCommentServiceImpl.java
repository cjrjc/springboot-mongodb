package com.fhbean.springboot.mongodb.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fhbean.springboot.mongodb.entity.CommentResponse;
import com.fhbean.springboot.mongodb.entity.ResourceComment;
import com.fhbean.springboot.mongodb.entity.ResponseContent;
import com.fhbean.springboot.mongodb.reposistory.ResourceCommentRepository;
import com.fhbean.springboot.mongodb.service.ResourceCommentService;

@Service(value = "resourceCommentService")
public class ResourceCommentServiceImpl implements ResourceCommentService {

	@Autowired
	private ResourceCommentRepository resourceCommentRepository;

	/**
	 * 查询某个资源的所有评论
	 */
	@Override
	public List<ResourceComment> findResourceComment(Long resourceId) {
		if (null == resourceId) {
			return null;
		}
		
		ResourceComment rc = new ResourceComment();
		rc.setResourceId(resourceId);
		return resourceCommentRepository.findAll(Example.of(rc));
	}

	/**
	 * 发表评论
	 */
	@Override
	public int saveComment(Long resourceId, Long commentUserId, String commentUserName, String commentContent) {
		//数据合法性校验 begin
		if (null == resourceId || null == commentUserId) {
			return 0;
		}
		
		if (StringUtils.isEmpty(commentUserName) || StringUtils.isEmpty(commentContent)) {
			return 0;
		}
		//数据合法性校验 end
		
		//保存评论 begin
		ResourceComment rc = new ResourceComment();
		rc.setResourceId(resourceId);
		rc.setCommentUserId(commentUserId);
		rc.setCommentUserName(commentUserName);
		rc.setCommentContent(commentContent);
		rc.setCtime(new Date());
		rc.setStatus("0"); //此处可采用常量或枚举类型
		resourceCommentRepository.save(rc);
		//保存评论 end
		
		return 0;
	}

	/**
	 * 回复评论
	 */
	@Override
	public int saveResponse(String id, Long responseUserId, String responseUserName, String responseContent) {
		//数据合法性校验 begin
		if (StringUtils.isEmpty(id)) {
			return 0;
		}
		
		if (null == responseUserId) {
			return 0;
		}
		
		if (StringUtils.isEmpty(responseUserName) || StringUtils.isEmpty(responseContent)) {
			return 0;
		}
		//数据合法性校验 end
		
		//查找指定评论 begin
		ResourceComment rc = new ResourceComment();
		rc.setId(id);
		
		Optional<ResourceComment> optional = resourceCommentRepository.findOne(Example.of(rc));
		if (!optional.isPresent()) {
			//未找到指定的评论
			return 0;
		}
		
		rc = optional.get();
		//查找指定评论 end
		
		//得到巳有回复
		CommentResponse[] crs = rc.getCommentResponses();
		if (null == crs) {
			//此评论之前没有任何人回复,当前回复作为第一条回复
			CommentResponse cr = getNewResponse(responseUserId, responseUserName, responseContent);
			rc.setCommentResponses(new CommentResponse[]{cr});
			
			resourceCommentRepository.save(rc);
			return 1;
		}
		
		//己有人回复过评论，判断responseUserId是否回复过评论 begin
		//responseUserId之前是否回复过此评论 begin
		boolean responsed = false;
		for (int i = 0; i < crs.length; i++) {
			if (crs[i].getResponseUserId().equals(responseUserId)) {
				responsed = true; //之前回复过
				ResponseContent[] rcsNew = getNewResponseContent(responseContent, crs, crs[i].getResponseContents());
				
				crs[i].setResponseContents(rcsNew);
				
				break; //已经回复，不再继续循环
			}
		}
		//己有人回复过评论，判断responseUserId是否回复过评论 end
		if (responsed) {
			//之前，responseUserId已经回复过此评论，在现有回复后追加一个回复即可
			resourceCommentRepository.save(rc);
			return 1;
		}
		
		//之前别人回复过此评论，但responseUserId没有回复过此评论 begin
		//将之前所有人的回复转储到新的数组
		CommentResponse[] crsNew = new CommentResponse[crs.length + 1];
		CommentResponse cr = getNewResponse(responseUserId, responseUserName, responseContent);
		for (int i = 0; i < crs.length; i++) {
			crsNew[i] = crs[i];
		}
		crsNew[crsNew.length - 1] = cr;
		//之前别人回复过此评论，但responseUserId没有回复过此评论 end
		rc.setCommentResponses(crsNew);
		
		resourceCommentRepository.save(rc);
		return 1;
	}

	/**
	 * 回复评论的评论
	 */
	@Override
	public int saveReply(String id, Long responseUserId, int index, String replyContent) {
		//数据合法性校验 begin
		if (StringUtils.isEmpty(id)) {
			return 0;
		}
		
		if (null == responseUserId) {
			return 0;
		}
		
		if (index < 0) {
			//索引值不能小于零
			return 0;
		}
		
		if (StringUtils.isEmpty(replyContent)) {
			return 0;
		}
		//数据合法性校验 end
		
		//查找指定评论 begin
		ResourceComment rc = new ResourceComment();
		rc.setId(id);
		
		Optional<ResourceComment> optional = resourceCommentRepository.findOne(Example.of(rc));
		if (!optional.isPresent()) {
			//未找到指定的评论
			return 0;
		}
		
		rc = optional.get();
		//查找指定评论 end
		
		CommentResponse[] crs = rc.getCommentResponses();
		if (null == crs || crs.length < 1) {
			//没有人回复过评论
			return 0;
		}
		
		for (int i = 0; i < crs.length; i++) {
			if (responseUserId.equals(crs[i].getResponseUserId())) {
				//找到responseUserId回复的评论
				ResponseContent[] rcs = crs[i].getResponseContents();//回复的评论数组
				if (null == rcs || index >= rcs.length) {
					//responseUserId没有对评论回复任何内容或index越界，指定的回复不存在，不再继续
					return 0;
				}
				
				ResponseContent[] grs = crs[i].getGetReplys();
				if (null == grs) {
					//之前没有回复过评论的评论
					grs = new ResponseContent[index+1];
					ResponseContent reply = new ResponseContent(new Date(), replyContent);
					grs[index] = reply;
					crs[i].setGetReplys(grs);
				} else {
					//之前回复过评论的评论
					if (index >= grs.length) {
						//索引值超过现有回复数组的长度，给数组增长
						ResponseContent[] grsNew = new ResponseContent[index + 1];
						ResponseContent reply = new ResponseContent(new Date(), replyContent);
						grsNew[index] = reply;
						crs[i].setGetReplys(grsNew);
					} else {
						//索引值未超过回复数组的长度，reply内容直接覆盖索引值的reply
						ResponseContent reply = new ResponseContent(new Date(), replyContent);
						grs[index] = reply;
						crs[i].setGetReplys(grs);
					}
				}
				
				resourceCommentRepository.save(rc);
				return 1;
			}
		}
		
		return 0;
	}
	
	private ResponseContent[] getNewResponseContent(String responseContent, CommentResponse[] crs, ResponseContent[] rcs) {
		ResponseContent[] rcsNew = null;
		int rcsSize = 0;
		if (null == rcs) {
			rcsSize++;
			rcsNew = new ResponseContent[rcsSize]; //防止程序异常，没有回复
		} else {
			rcsSize = rcs.length + 1;
			rcsNew = new ResponseContent[rcsSize];
			for (int i = 0; i < rcs.length; i++) {
				//原先的回复转储到新的数组
				rcsNew[i] = rcs[i];
			}
		}
		ResponseContent rcon = new ResponseContent();
		rcon.setCtime(new Date());
		rcon.setContent(responseContent);
		rcsNew[rcsSize - 1] = rcon;
		return rcsNew;
	}

	private CommentResponse getNewResponse(Long responseUserId, String responseUserName, String responseContent) {
		CommentResponse cr = new CommentResponse();
		cr.setResponseUserId(responseUserId);
		cr.setResponseUserName(responseUserName);
		ResponseContent rct = new ResponseContent();
		rct.setCtime(new Date());
		rct.setContent(responseContent);
		cr.setResponseContents(new ResponseContent[] {rct});
		return cr;
	}

	
}
