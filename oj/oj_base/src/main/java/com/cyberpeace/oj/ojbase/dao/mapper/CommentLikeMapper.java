package com.cyberpeace.oj.ojbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.CommentLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论喜欢Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface CommentLikeMapper extends BaseMapper<CommentLike> {

	/**
	 * 查询点赞列表
	 *
	 * @param comment
	 * @return
	 * @throws
	 * @see String
	 * @see List<CommentLike>
	 */
	List<CommentLike> listByComment(@Param("commentId") String comment);

	/**
	 * 查询由评论和用户
	 *
	 * @param commentId 评论id
	 * @param userId    用户id
	 * @return {@code CommentLike }
	 * @throws
	 * @see String
	 * @see String
	 * @see CommentLike
	 */
	CommentLike selectByCommentAndUser(@Param("commentId") String commentId,
			@Param("userId") String userId);

	/**
	 * 删除由用户和id
	 *
	 * @param userId    用户id
	 * @param commentId 评论id
	 * @return int
	 * @throws
	 * @see String
	 * @see String
	 * @see int
	 */
	int deleteByUserAndId(@Param("userId") String userId, @Param("commentId") String commentId);

	/**
	 * 删除由评论id列表
	 *
	 * @param commentIds 评论id列表
	 * @throws
	 * @see List<String>
	 */
	void deleteByCommentIds(@Param("commentIds") List<String> commentIds);
}
