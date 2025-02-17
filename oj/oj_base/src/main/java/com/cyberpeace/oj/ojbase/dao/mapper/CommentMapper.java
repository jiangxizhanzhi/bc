package com.cyberpeace.oj.ojbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

	/**
	 * 按资源优先
	 *
	 * @param page         分页
	 * @param resource     资源
	 * @param businessType 业务类型
	 * @return {@code IPage<Comment> }
	 */
	IPage<Comment> firstByResource(Page<Comment> page, @Param("businessId") String resource,
			@Param("businessType") Integer businessType);

	/**
	 * 查询由父母
	 *
	 * @param parent 父母
	 * @return {@code List<Comment> }
	 */
	List<Comment> selectByParent(@Param("parent") String parent);

	/**
	 * 更新评论
	 *
	 * @param comment 评论
	 */
	void updateComment(Comment comment);

	/**
	 * 删除由业务id
	 *
	 * @param businessId 业务id
	 */
	void deleteByBusinessId(@Param("businessId") String businessId);

	/**
	 * 删除由业务id
	 *
	 * @param businessIds 业务id
	 */
	void deleteByBusinessIds(@Param("businessIds") List<String> businessIds);

	/**
	 * 查询由答
	 *
	 * @param replyId 答id
	 * @return {@code List<Comment> }
	 */
	List<Comment> selectByReply(@Param("replyId") String replyId);

	/**
	 * 查询id列表由父母
	 *
	 * @param parents 父母
	 * @return {@code List<String> }
	 */
	List<String> selectIdsByParent(@Param("parents") List<String> parents);

	/**
	 * 查询全部由父母
	 *
	 * @param page     分页
	 * @param parentId 父母id
	 * @return {@code IPage<Comment> }
	 */
	IPage<Comment> selectAllByParent(Page<Comment> page, @Param("parentId") String parentId);

	/**
	 * 查询父母
	 *
	 * @param page         分页
	 * @param businessType 业务类型
	 * @param content      内容
	 * @return {@code IPage<Comment> }
	 */
	IPage<Comment> selectParent(Page<Comment> page,@Param("businessType") Integer businessType,@Param("content") String content);
}
