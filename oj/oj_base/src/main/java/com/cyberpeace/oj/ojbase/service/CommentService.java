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
	Comment addNewComment(Comment comment);

	/**
	 * 分页查询评论信息
	 * @param page
	 * @param resource
	 * @return
	 */
	ListRsp<CommentBo> pageComment(Page page, String resource, boolean children);

	/**
	 * 获取子评论
	 * @param commentBos
	 */
	void setChildrenByFirst(List<CommentBo> commentBos);

	/**
	 * 点赞留言
	 * @param comment
	 * @param isLike
	 * @return
	 */
	String commentIsLike(String comment, Boolean isLike, String userId);
}
