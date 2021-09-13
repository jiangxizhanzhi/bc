package com.cyberpeace.oj.crbase.service.impl;

import com.cyberpeace.oj.crbase.dao.entity.Comment;
import com.cyberpeace.oj.crbase.dao.mapper.CommentMapper;
import com.cyberpeace.oj.crbase.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;

	@Override
	public Comment addNewComment(Comment comment) {
		commentMapper.insert(comment);
		return comment;
	}
}
