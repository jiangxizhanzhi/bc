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
 * @date 2024/11/14
 */
@Mapper
public interface CommentLikeMapper extends BaseMapper<CommentLike> {

	/**
	 * 查询点赞列表
	 *
	 * @param comment
	 * @return
	 */
	List<CommentLike> listByComment(@Param("commentId") String comment);

	/**
	 * 查询由评论和用户
	 *
	 * @param commentId 评论id
	 * @param userId    用户id
	 * @return {@code CommentLike }
	 */
	CommentLike selectByCommentAndUser(@Param("commentId") String commentId,
			@Param("userId") String userId);

	/**
	 * 删除由用户和id
	 *
	 * @param userId    用户id
	 * @param commentId 评论id
	 * @return int
	 */
	int deleteByUserAndId(@Param("userId") String userId, @Param("commentId") String commentId);

	/**
	 * 删除由评论id列表
	 *
	 * @param commentIds 评论id列表
	 */
	void deleteByCommentIds(@Param("commentIds") List<String> commentIds);
}
