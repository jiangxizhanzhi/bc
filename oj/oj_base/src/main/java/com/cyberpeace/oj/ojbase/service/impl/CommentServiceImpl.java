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
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;

/**
 * 评论Service实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

	/**
	 * 评论Mapper
	 */
	@Autowired
	private CommentMapper commentMapper;

	/**
	 * 评论喜欢Mapper
	 */
	@Autowired
	private CommentLikeMapper commentLikeMapper;

	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 新增新增功能评论
	 *
	 * @param comment 评论
	 * @return {@code Integer }
	 */
	@Override
	public Integer addNewComment(Comment comment) {
		return commentMapper.insert(comment);
	}

	/**
	 * 管理新增新增功能评论
	 *
	 * @param comment 评论
	 * @return {@code Integer }
	 */
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

	/**
	 * 分页评论
	 *
	 * @param page         分页
	 * @param businessId   业务id
	 * @param businessType 业务类型
	 * @param children     子级
	 * @return {@code ListRsp<CommentBo> }
	 */
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

	/**
	 * 设置子级由第一
	 *
	 * @param commentBos 评论BOS
	 */
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
	 * @param userId       用户id
	 * @param comment
	 * @param replyComment
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

	/**
	 * 评论是否喜欢
	 *
	 * @param commentId 评论id
	 * @param isLike    是否喜欢
	 * @param userId    用户id
	 * @return {@code Integer }
	 */
	@Override
	public Integer commentIsLike(String commentId, Boolean isLike, String userId) {

		CommentLike commentLike = new CommentLike();
		commentLike.setCommentId(commentId);
		commentLike.setUserId(userId);

		return Boolean.TRUE.equals(isLike) ? commentLikeMapper.insert(commentLike)
				: commentLikeMapper.deleteByUserAndId(userId, commentId);
	}

	/**
	 * bath delete 评论
	 *
	 * @param commentIds 评论id列表
	 */
	@Override
	public void bathDeleteComment(List<String> commentIds) {

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// 获取所有应删除的id
				List<String> childrenIds = commentMapper.selectIdsByParent(commentIds);
				while (CollectionUtils.isNotEmpty(childrenIds)) {
					commentIds.addAll(childrenIds);
					childrenIds = commentMapper.selectIdsByParent(childrenIds);
				}

				commentMapper.deleteBatchIds(commentIds);
				commentLikeMapper.deleteByCommentIds(commentIds);
			}
		});
	}

	/**
	 * 分页父母评论
	 *
	 * @param page         分页
	 * @param businessType 业务类型
	 * @param content      内容
	 * @return {@code ListRsp<CommentBo> }
	 */
	@Override
	public ListRsp<CommentBo> pageParentComment(Page page, Integer businessType, String content) {
		IPage<Comment> iPage = commentMapper.selectParent(page, businessType, content);
		IPage<CommentBo> convert = iPage.convert(comment -> commentToBo(null, comment, null));
		setChildrenByFirst(convert.getRecords());
		return PageUtil.getListResult(convert);
	}

	/**
	 * 分页子级评论
	 *
	 * @param page       分页
	 * @param resourceId 资源id
	 * @return {@code ListRsp<CommentBo> }
	 */
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
