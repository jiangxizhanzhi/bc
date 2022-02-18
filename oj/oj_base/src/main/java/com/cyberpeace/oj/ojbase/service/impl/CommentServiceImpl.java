package com.cyberpeace.oj.ojbase.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.controller.res.CommentRes;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.dao.entity.CommentLike;
import com.cyberpeace.oj.ojbase.dao.mapper.CommentLikeMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.CommentMapper;
import com.cyberpeace.oj.ojbase.service.CommentService;
import com.cyberpeace.oj.ojbase.service.bo.CommentBo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private CommentLikeMapper commentLikeMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public Integer addNewComment(Comment comment) {

		return commentMapper.insert(comment);
	}

	@Override
	public ListRsp<CommentBo> pageComment(Page page, String businessId, Integer businessType,
			boolean children) {
		IPage<Comment> iPage = commentMapper.firstByResource(page, businessId, businessType);

		IPage<CommentBo> convert = iPage.convert(comment -> commentToBo(UserInfoContext.getUserContext().getUserId(), comment, null));

		// 是否查询评论下的回复
		if (children) {
			setChildrenByFirst(convert.getRecords());
		}

		return PageUtil.getListResult(convert);
	}

	@Override
	public void setChildrenByFirst(List<CommentBo> commentBos) {
		for (CommentBo commentBo : commentBos) {
			List<Comment> comments = commentMapper.selectByParent(commentBo.getResourceId());

			Map<String, Comment> commentUserMap = StreamApiUtils.toMap(comments,
					Comment::getResourceId, comment -> comment);

			List<CommentBo> boList = StreamApiUtils.map(comments, comment -> {

				// 获取不到说明是父节点
				Comment replyComment = commentUserMap.get(comment.getReplyId());
				if (null == replyComment) {
					return commentToBo(UserInfoContext.getUserContext().getUserId(), comment, commentBo);
				}

				CommentBo reply = commentToBo(UserInfoContext.getUserContext().getUserId(), replyComment, null);
				return commentToBo(UserInfoContext.getUserContext().getUserId(), comment, reply);
			});

			commentBo.setCommentReply(boList);
		}
	}

	/**
	 * 数据格式转换
	 * @param replyComment
	 * @param comment
	 * @return
	 */
	private CommentBo commentToBo(String userId, Comment comment, CommentBo replyComment) {
		CommentBo commentBo = BeanUtils.copy(comment, CommentBo.class);

		// 获取用户信息
		UserInfo userInfo = remoteUserService.getById(comment.getUserId());
		if (null != userInfo) {
			commentBo.setAvatar(userInfo.getAvatarUrl());
			commentBo.setUserName(userInfo.getName());
		}

		// 获取点赞数量
		List<CommentLike> commentLikes = commentLikeMapper.listByComment(comment.getResourceId());
		commentBo.setLikesCount(commentLikes.size());
		commentBo.setIsLike(null != commentLikeMapper.selectByCommentAndUser(comment.getResourceId(), userId));

		if (null != replyComment) {
			commentBo.setReplyUsername(replyComment.getUserName());
			commentBo.setReplyId(replyComment.getResourceId());
		}

		return commentBo;
	}

	@Override
	public Integer commentIsLike(String commentId, Boolean isLike, String userId) {

		CommentLike commentLike = new CommentLike();
		commentLike.setCommentId(commentId);
		commentLike.setUserId(userId);

		return isLike ? commentLikeMapper.insert(commentLike)
				: commentLikeMapper.deleteByUserAndId(userId, commentId);
	}
}
