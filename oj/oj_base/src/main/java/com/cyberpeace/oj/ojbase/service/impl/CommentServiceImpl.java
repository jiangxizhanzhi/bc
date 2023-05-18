package com.cyberpeace.oj.ojbase.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.dao.entity.CommentLike;
import com.cyberpeace.oj.ojbase.dao.mapper.CommentLikeMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.CommentMapper;
import com.cyberpeace.oj.ojbase.service.CommentService;
import com.cyberpeace.oj.ojbase.service.bo.CommentBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private CommentLikeMapper commentLikeMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public Integer addNewComment(Comment comment) {
		return commentMapper.insert(comment);
	}

	@Override
	public Integer adminAddNewComment(Comment comment) {
		String replyCommentId = comment.getReplyId() == null ? comment.getRootId()
				: comment.getReplyId();
		// 被回复的评论已被删除
		if (replyCommentId != null && commentMapper.selectById(replyCommentId) == null) {
			log.error("comment [{}] hasn't been deleted", replyCommentId);
			throw new BusinessException(OjBusinessErrorCode.COMMENT_NOT_EXIST);
		}
		return commentMapper.insert(comment);
	}

	@Override
	public ListRsp<CommentBo> pageComment(Page page, String businessId, Integer businessType,
			boolean children) {
		IPage<Comment> iPage = commentMapper.firstByResource(page, businessId, businessType);

		IPage<CommentBo> convert = iPage
				.convert(comment -> commentToBo(UserInfoContext.getUserContext().getUserId(),
						comment, null));

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
					return commentToBo(UserInfoContext.getUserContext().getUserId(), comment,
							commentBo);
				}

				CommentBo reply = commentToBo(UserInfoContext.getUserContext().getUserId(),
						replyComment, null);
				return commentToBo(UserInfoContext.getUserContext().getUserId(), comment, reply);
			});

			if (boList != null) {
				commentBo.setReplyCount(boList.size());
			} else {
				commentBo.setReplyCount(0);
			}
			commentBo.setCommentReply(boList);
		}
	}

	/**
	 * 数据格式转换
	 *
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
			commentBo.setUserId(userInfo.getUserName());

		}

		// 获取点赞数量
		List<CommentLike> commentLikes = commentLikeMapper.listByComment(comment.getResourceId());
		commentBo.setLikesCount(commentLikes.size());
		commentBo.setIsLike(
				null != commentLikeMapper.selectByCommentAndUser(comment.getResourceId(), userId));

		if (null != replyComment) {
			commentBo.setReplyUsername(replyComment.getUserName());

			commentBo.setReplyUserId(replyComment.getUserId());
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

	@Override
	public void bathDeleteComment(List<String> commentIds) {

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// 获取所有应删除的id
				List<String> childrenIds = commentMapper.selectIdsByParent(commentIds);
				while (childrenIds != null && childrenIds.size() > 0) {
					commentIds.addAll(childrenIds);
					childrenIds = commentMapper.selectIdsByParent(childrenIds);
				}

				commentMapper.deleteBatchIds(commentIds);
				commentLikeMapper.deleteByCommentIds(commentIds);
			}
		});
	}

	@Override
	public ListRsp<CommentBo> pageParentComment(Page page, Integer businessType, String content) {
		IPage<Comment> iPage = commentMapper.selectParent(page, businessType, content);
		IPage<CommentBo> convert = iPage.convert(comment -> commentToBo(null, comment, null));
		setChildrenByFirst(convert.getRecords());
		return PageUtil.getListResult(convert);
	}

	@Override
	public ListRsp<CommentBo> pageChildrenComment(Page page, String resourceId) {
		IPage<Comment> iPage = commentMapper.selectAllByParent(page, resourceId);
		if (commentMapper.selectById(resourceId) == null) {
			log.error("comment [{}] doesn't exists", resourceId);
			throw new BusinessException(OjBusinessErrorCode.COMMENT_NOT_EXIST);
		}

		Map<String, Comment> commentUserMap = StreamApiUtils.toMap(iPage.getRecords(),
				Comment::getResourceId, comment -> comment);
		// 获取一级留言
		CommentBo commentBo = commentToBo(null, commentMapper.selectById(resourceId), null);
		IPage<CommentBo> convert = iPage.convert(comment -> {
			// 获取不到说明是父节点
			Comment replyComment = commentUserMap.get(comment.getReplyId());
			if (null == replyComment) {
				return commentToBo(null, comment, commentBo);
			}
			CommentBo reply = commentToBo(UserInfoContext.getUserContext().getUserId(),
					replyComment, null);
			return commentToBo(UserInfoContext.getUserContext().getUserId(), comment, reply);
		});
		return PageUtil.getListResult(convert);
	}
}
