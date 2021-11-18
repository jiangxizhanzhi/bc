package com.cyberpeace.oj.ojbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.CommentLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentLikeMapper extends BaseMapper<CommentLike> {

	/**
	 * 查询点赞列表
	 * @param comment
	 * @return
	 */
	List<CommentLike> listByComment(@Param("commentId") String comment);

	CommentLike selectByCommentAndUser(@Param("commentId") String commentId,
			@Param("userId") String userId);

	int deleteByUserAndId(@Param("userId") String userId, @Param("commentId") String commentId);
}
