package com.cyberpeace.oj.ojbase.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.controller.res.CommentRes;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.dao.entity.CommentLike;
import com.cyberpeace.oj.ojbase.dao.mapper.CommentLikeMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.CommentMapper;
import com.cyberpeace.oj.ojbase.service.CommentService;
import com.cyberpeace.oj.ojbase.service.bo.CommentBo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private CommentLikeMapper commentLikeMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public Comment addNewComment(Comment comment) {
		commentMapper.insert(comment);
		return comment;
	}

	@Override
	public ListRsp<CommentBo> pageComment(Page page, String resource, boolean children) {
		IPage<Comment> iPage = commentMapper.firstByResource(page, resource);
		iPage.convert(comment -> commentToBo(comment));

		return PageUtil.getListResult(iPage);
	}

	@Override
	public void setChildrenByFirst(List<CommentBo> commentBos) {
		for (CommentBo commentBo : commentBos) {
			List<Comment> comments = commentMapper.selectByParent(commentBo.getResourceId());
			List<CommentBo> boList = comments.stream().map(comment -> commentToBo(comment))
					.collect(Collectors.toList());
			commentBo.setCommentReply(boList);
		}
	}

	/**
	 * 数据格式转换
	 * @param comment
	 * @return
	 */
	private CommentBo commentToBo(Comment comment) {
		CommentBo commentBo = BeanUtils.copy(comment, CommentBo.class);

		// 获取用户信息
		UserInfo userInfo = remoteUserService.getById(comment.getUserId());
		commentBo.setAvatar(userInfo.getAvatarUrl());
		commentBo.setUserName(userInfo.getUserName());

		// 获取点赞数量
		List<CommentLike> commentLikes = commentLikeMapper.listByComment(comment.getResourceId(),
				true);
		commentBo.setLikesCount(commentLikes.size());
		commentBo.setIsLike(CollectionUtils.isNotEmpty(commentLikes));

		return commentBo;
	}

	@Override
	public String commentIsLike(String comment, Boolean isLike, String userId) {
		CommentLike commentLike = commentLikeMapper.selectByCommentAndUser(comment, userId);

		if (commentLike == null) {
			CommentLike like = new CommentLike();
			like.setIsLike(isLike);
			like.setComment(comment);
			like.setUser(userId);
			commentLikeMapper.insert(like);

		} else {
			if (commentLike.getIsLike().booleanValue() != isLike.booleanValue()) {
				commentLike.setIsLike(isLike);
				commentLikeMapper.updateById(commentLike);
			}
		}

		return comment;
	}
}
