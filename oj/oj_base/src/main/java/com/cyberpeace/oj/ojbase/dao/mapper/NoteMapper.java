package com.cyberpeace.oj.ojbase.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.ojbase.dao.context.NoteListQuery;
import com.cyberpeace.oj.ojbase.dao.context.NoteUniqueContext;
import com.cyberpeace.oj.ojbase.dao.entity.Note;

import java.util.List;

/**
 * 笔记Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface NoteMapper extends BaseMapper<Note> {

	/**
	 * 查询由条件
	 *
	 * @param context 上下文
	 * @return {@code Note }
	 * @throws
	 * @see NoteUniqueContext
	 * @see Note
	 */
	Note selectByCondition(@Param("context") NoteUniqueContext context);

	/**
	 * 分页由用户id列表和内容
	 *
	 * @param page          分页
	 * @param noteListQuery 注释列表查询
	 * @param isPublic      是否公开
	 * @param isSubmit      是提交
	 * @return {@code IPage<Note> }
	 * @throws
	 * @see Page
	 * @see NoteListQuery
	 * @see Boolean
	 * @see Boolean
	 * @see IPage<Note>
	 */
	IPage<Note> pageByUserIdsAndContent(Page page,
			@Param("noteListQuery") NoteListQuery noteListQuery,
			@Param("isPublic") Boolean isPublic,
			@Param("isSubmit") Boolean isSubmit);

	/**
	 * 更新由id
	 *
	 * @param note 注意
	 * @return int
	 * @throws
	 * @see Note
	 * @see int
	 */
	@Override
	int updateById(Note note);

	/**
	 * 更新说明
	 *
	 * @param note 注意
	 * @return int
	 * @throws
	 * @see Note
	 * @see int
	 */
	int updateNote(Note note);

	/**
	 * 查询由上下文
	 *
	 * @param context 上下文
	 * @return {@code List<String> }
	 * @throws
	 * @see NoteUniqueContext
	 * @see List<String>
	 */
	List<String> selectByContext(@Param("context") NoteUniqueContext context);

	/**
	 * 查询由列表查询
	 *
	 * @param noteListQuery 注释列表查询
	 * @param isPublic      是否公开
	 * @return {@code List<Note> }
	 * @throws
	 * @see NoteListQuery
	 * @see Boolean
	 * @see List<Note>
	 */
	List<Note> selectByListQuery(@Param("noteListQuery") NoteListQuery noteListQuery,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 删除由业务
	 *
	 * @param businessId   业务id
	 * @param noteType     注释类型
	 * @param resourceType 资源类型
	 * @throws
	 * @see String
	 * @see Integer
	 * @see Integer
	 */
	void deleteByBusiness(@Param("businessId") String businessId,
			@Param("noteType") Integer noteType, @Param("resourceType") Integer resourceType);

	/**
	 * 查询由独特
	 *
	 * @param userId       用户id
	 * @param noteType     注释类型
	 * @param businessType 业务类型
	 * @param businessId   业务id
	 * @return {@code Note }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see Integer
	 * @see String
	 * @see Note
	 */
	Note selectByUnique(@Param("userId") String userId, @Param("noteType") Integer noteType,
						@Param("businessType") Integer businessType, @Param("businessId") String businessId);

	/**
	 * 更新报告由id
	 *
	 * @param note 注意
	 * @return int
	 * @throws
	 * @see Note
	 * @see int
	 */
	int updateReportByID(Note note);

	/**
	 * 计数由用户id
	 *
	 * @param userId      用户id
	 * @param noteType    注释类型
	 * @param resourceIds 资源id列表
	 * @return int
	 * @throws
	 * @see String
	 * @see Integer
	 * @see List<String>
	 * @see int
	 */
	int countByUserId(@Param("userId") String userId, @Param("noteType") Integer noteType, @Param("resourceIds") List<String> resourceIds);
}