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
/**
 * 这段代码定义了一个名为 `CommentLikeMapper` 的接口，继承自 MyBatis-Plus 的 `BaseMapper`。这个接口主要用于操作 `CommentLike` 实体类的数据。以下是对其主要部分的分析： 
 * ### 包声明和导入 
 * ```java 
 * package com.cyberpeace.oj.ojbase.dao.mapper; 
 * import com.baomidou.mybatisplus.core.mapper.BaseMapper; 
 * import com.cyberpeace.oj.ojbase.dao.entity.CommentLike; 
 * import org.apache.ibatis.annotations.Mapper; 
 * import org.apache.ibatis.annotations.Param; 
 * import java.util.List; 
 * ``` 
 * - 导入了必要的包，包括 MyBatis-Plus 的 `BaseMapper` 和 `@Mapper` 注解，以及用于参数注解的 `@Param`。 
 * ### 类定义 
 * ```java 
 * @Mapper 
 * public interface CommentLikeMapper extends BaseMapper<CommentLike> { 
 * ``` 
 * - 使用 `@Mapper` 注解标记这是一个 MyBatis 的 Mapper 接口。 
 * - 继承自 `BaseMapper<CommentLike>`，这意味着它自动拥有了基本的 CRUD 操作方法。 
 * ### 方法定义 
 * #### 1. 查询点赞列表 
 * ```java 
 * List<CommentLike> listByComment(@Param("commentId") String comment); 
 * ``` 
 * - 根据给定的评论ID查询所有的点赞记录。 
 * - 参数：`commentId`（字符串类型）。 
 * #### 2. 查询特定用户的点赞记录 
 * ```java 
 * CommentLike selectByCommentAndUser(@Param("commentId") String commentId, 
 * @Param("userId") String userId); 
 * ``` 
 * - 根据评论ID和用户ID查询特定的点赞记录。 
 * - 参数：`commentId`（字符串类型），`userId`（字符串类型）。 
 * - 返回值：如果存在则返回一个 `CommentLike` 对象，否则返回 `null`。 
 * #### 3. 删除特定用户的点赞记录 
 * ```java 
 * int deleteByUserAndId(@Param("userId") String userId, 
 * @Param("commentId") String commentId); 
 * ``` 
 * - 根据用户ID和评论ID删除一个点赞记录。 
 * - 参数：`userId`（字符串类型），`commentId`（字符串类型）。 
 * - 返回值：被删除的记录数量（通常为 0 或 1）。 
 * #### 4. 根据评论ID列表批量删除点赞记录 
 * ```java 
 * void deleteByCommentIds(@Param("commentIds") List<String> commentIds); 
 * ``` 
 * - 根据一个评论ID列表批量删除点赞记录。 
 * - 参数：`commentIds`（字符串列表）。 
 * - 返回值：无。 
 * ### 总结 
 * 该接口提供了针对 `CommentLike` 实体类的基本 CRUD 操作，并额外增加了根据评论ID查询所有点赞记录、根据评论ID和用户ID查询特定点赞记录、根据用户ID和评论ID删除特定点赞记录以及根据评论ID列表批量删除点赞记录的功能。这些方法使得对于点赞记录的操作更加灵活和方便。 
**/
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

