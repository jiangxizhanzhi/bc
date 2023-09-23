package com.cyberpeace.oj.ojbase.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.service.bo.CommentBo;

import java.util.List;

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
	ListRsp<CommentBo> pageComment(Page<CommentBo> page, String businessId, Integer businessType,
			boolean children);

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

	/**
	 * 删除留言
	 * @param commentIds
	 */
	void bathDeleteComment(List<String> commentIds);

	/**
	 *后台分页查询一级留言
	 * @param page
	 * @param businessType
	 * @param content
	 * @return
	 */
	ListRsp<CommentBo> pageParentComment(Page<CommentBo> page, Integer businessType, String content);

	/**
	 * 后台分页查询二级留言
	 * @param page
	 * @param resourceId  一级留言的id
	 * @return
	 */
	ListRsp<CommentBo> pageChildrenComment(Page<CommentBo> page, String resourceId);

	/**
	 * 新增评论
	 * @param comment
	 * @return
	 */
	Integer adminAddNewComment(Comment comment);
}
