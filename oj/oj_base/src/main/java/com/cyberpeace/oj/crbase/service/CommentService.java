package com.cyberpeace.oj.crbase.service;

import com.cyberpeace.oj.crbase.dao.entity.Comment;

public interface CommentService {

	/**
	 * 新增评论
	 * @param comment
	 * @return
	 */
	Comment addNewComment(Comment comment);

}
