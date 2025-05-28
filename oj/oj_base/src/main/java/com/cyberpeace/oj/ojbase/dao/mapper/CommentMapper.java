/**
 * 这段代码定义了一个名为 `CommentMapper` 的接口，继承自 `BaseMapper<Comment>`。这个接口主要用于数据库中与评论（`Comment`）相关的操作。下面是对代码的简要分析： 
 * ### 接口定义 
 * - **包名**: `com.cyberpeace.oj.ojbase.dao.mapper` 
 * - **接口名**: `CommentMapper` 
 * - **注解**: `@Mapper` 表明这是一个 MyBatis 的 Mapper 接口。 
 * - **继承**: `extends BaseMapper<Comment>`，这意味着该接口已经包含了基本的 CRUD 操作。 
 * ### 方法定义 
 * 1. **`firstByResource(Page page, @Param("businessId") String resource, @Param("businessType") Integer businessType)`** 
 * - **功能**: 按资源优先获取评论。 
 * - **参数**: 
 * - `page`: 分页对象。 
 * - `resource`: 资源ID。 
 * - `businessType`: 业务类型。 
 * - **返回值**: `IPage<Comment>`，分页后的评论列表。 
 * 2. **`selectByParent(@Param("parent") String parent)`** 
 * - **功能**: 根据父评论ID查询子评论。 
 * - **参数**: 
 * - `parent`: 父评论ID。 
 * - **返回值**: `List<Comment>`，子评论列表。 
 * 3. **`updateComment(Comment comment)`** 
 * - **功能**: 更新评论信息。 
 * - **参数**: 
 * - `comment`: 包含更新信息的评论对象。 
 * - **返回值**: 无。 
 * 4. **`deleteByBusinessId(@Param("businessId") String businessId)`** 
 * - **功能**: 根据业务ID删除评论。 
 * - **参数**: 
 * - `businessId`: 业务ID。 
 * - **返回值**: 无。 
 * 5. **`selectByReply(@Param("replyId") String replyId)`** 
 * - **功能**: 根据回复ID查询评论。 
 * - **参数**: 
 * - `replyId`: 回复ID。 
 * - **返回值**: `List<Comment>`，包含该回复的所有评论。 
 * 6. **`selectIdsByParent(@Param("parents") List<String> parents)`** 
 * - **功能**: 根据父评论ID列表查询所有子评论ID。 
 * - **参数**: 
 * - `parents`: 父评论ID列表。 
 * - **返回值**: `List<String>`，子评论ID列表。 
 * 7. **`selectAllByParent(Page page, @Param("parentId") String parentId)`** 
 * - **功能**: 根据父评论ID和分页信息查询所有子评论。 
 * - **参数**: 
 * - `page`: 分页对象。 
 * - `parentId`: 父评论ID。 
 * - **返回值**: `IPage<Comment>`，分页后的子评论列表。 
 * 8. **`selectParent(Page page, @Param("businessType") Integer businessType, @Param("content") String content)`** 
 * - **功能**: 根据业务类型、内容和分页信息查询父评论。 
 * - **参数**: 
 * - `page`: 分页对象。 
 * - `businessType`: 业务类型。 
 * - `content`: 评论内容。 
 * - **返回值**: `IPage<Comment>`，分页后的父评论列表。 
 * ### 总结 
 * - **功能**: 这个接口提供了多种查询和管理评论的功能，包括按资源优先排序、按父评论ID查询、更新和删除评论等。 
 * - **使用场景**: 适用于需要进行复杂查询和管理评论的应用场景，如在线论坛、问答平台等。 
 * - **优点**: 利用了 MyBatis 和 MyBatis Plus 提供的分页功能，提高了查询效率和用户体验。 
**/
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
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
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
	 * @throws
	 * @see Page
	 * @see String
	 * @see Integer
	 * @see IPage<Comment>
	 */
	IPage<Comment> firstByResource(Page page, @Param("businessId") String resource,
			@Param("businessType") Integer businessType);

	/**
	 * 查询由父母
	 *
	 * @param parent 父母
	 * @return {@code List<Comment> }
	 * @throws
	 * @see String
	 * @see List<Comment>
	 */
	List<Comment> selectByParent(@Param("parent") String parent);

	/**
	 * 更新评论
	 *
	 * @param comment 评论
	 * @throws
	 * @see Comment
	 */
	void updateComment(Comment comment);

	/**
	 * 删除由业务id
	 *
	 * @param businessId 业务id
	 * @throws
	 * @see String
	 */
	void deleteByBusinessId(@Param("businessId") String businessId);

	/**
	 * 查询由答
	 *
	 * @param replyId 答id
	 * @return {@code List<Comment> }
	 * @throws
	 * @see String
	 * @see List<Comment>
	 */
	List<Comment> selectByReply(@Param("replyId") String replyId);

	/**
	 * 查询id列表由父母
	 *
	 * @param parents 父母
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> selectIdsByParent(@Param("parents") List<String> parents);

	/**
	 * 查询都由父母
	 *
	 * @param page     分页
	 * @param parentId 父母id
	 * @return {@code IPage<Comment> }
	 * @throws
	 * @see Page
	 * @see String
	 * @see IPage<Comment>
	 */
	IPage<Comment> selectAllByParent(Page page, @Param("parentId") String parentId);

	/**
	 * 查询父母
	 *
	 * @param page         分页
	 * @param businessType 业务类型
	 * @param content      内容
	 * @return {@code IPage<Comment> }
	 * @throws
	 * @see Page
	 * @see Integer
	 * @see String
	 * @see IPage<Comment>
	 */
	IPage<Comment> selectParent(Page page,@Param("businessType") Integer businessType,@Param("content") String content);
}
