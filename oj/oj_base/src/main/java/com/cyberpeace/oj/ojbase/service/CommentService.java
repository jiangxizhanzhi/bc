package com.cyberpeace.oj.ojbase.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.service.bo.CommentBo;

public interface CommentService {

	/**
	 * 新增评论
	 * @param comment
	 * @return
	 */
	Integer addNewComment(Comment comment);

	/**
	 * 分页查询评论信息
	 * @param page
	 * @param businessId
	 * @param businessType
	 * @param children 是否查询评论下的回复 true=查询 false不查询
	 * @return
	 */
	ListRsp<CommentBo> pageComment(Page page, String businessId, Integer businessType, boolean children);

	/**
	 * 获取子评论
	 * @param commentBos
	 */
	void setChildrenByFirst(List<CommentBo> commentBos);

	/**
	 * 点赞留言
	 * @param commentId
	 * @param isLike
	 * @return
	 */
	Integer commentIsLike(String commentId, Boolean isLike, String userId);
}
