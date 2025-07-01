package com.cyberpeace.oj.ojbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

	IPage<Comment> firstByResource(Page page, @Param("businessId") String resource,
			@Param("businessType") Integer businessType);

	List<Comment> selectByParent(@Param("parent") String parent);

	void updateComment(Comment comment);

	void deleteByBusinessId(@Param("businessId") String businessId);

	List<Comment> selectByReply(@Param("replyId") String replyId);

	List<String> selectIdsByParent(@Param("parents") List<String> parents);

	IPage<Comment> selectAllByParent(Page page, @Param("parentId") String parentId);

	IPage<Comment> selectParent(Page page,@Param("businessType") Integer businessType,@Param("content") String content);

	void addReadCount(@Param("commentIds") List<String> commentIds);
}
