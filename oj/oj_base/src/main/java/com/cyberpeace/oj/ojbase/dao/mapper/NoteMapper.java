package com.cyberpeace.oj.ojbase.dao.mapper;

import com.cyberpeace.oj.ojbase.service.bo.ClassroomNoteEvalBo;
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
 * @date 2024/11/14
 */
@Mapper
public interface NoteMapper extends BaseMapper<Note> {

	/**
	 * 查询由条件
	 *
	 * @param context 上下文
	 * @return {@code Note }
	 */
	Note selectByCondition(@Param("context") NoteUniqueContext context);

	/**
	 * 分页由用户id列表和内容
	 *
	 * @param page          分页
	 * @param noteListQuery 笔记列表查询
	 * @param isPublic      是否公开
	 * @param isSubmit      是否提交
	 * @return {@code IPage<Note> }
	 */
	IPage<Note> pageByUserIdsAndContent(Page<Note> page,
			@Param("noteListQuery") NoteListQuery noteListQuery,
			@Param("isPublic") Boolean isPublic,
			@Param("isSubmit") Boolean isSubmit);

	/**
	 * 更新由id
	 *
	 * @param note 笔记
	 * @return int
	 */
	@Override
	int updateById(Note note);

	/**
	 * 更新笔记
	 *
	 * @param note 笔记
	 * @return int
	 */
	int updateNote(Note note);

	/**
	 * 查询由上下文
	 *
	 * @param context 上下文
	 * @return {@code List<String> }
	 */
	List<String> selectByContext(@Param("context") NoteUniqueContext context);

	/**
	 * 查询由列表查询
	 *
	 * @param noteListQuery 笔记列表查询
	 * @param isPublic      是否公开
	 * @return {@code List<Note> }
	 */
	List<Note> selectByListQuery(@Param("noteListQuery") NoteListQuery noteListQuery,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 删除由业务
	 *
	 * @param businessId   业务id
	 * @param noteType     笔记类型
	 * @param resourceType 资源类型
	 */
	void deleteByBusiness(@Param("businessId") String businessId,
			@Param("noteType") Integer noteType, @Param("resourceType") Integer resourceType);

	/**
	 * 根据业务id批量删除
	 *
	 * @param businessIds  业务id
	 * @param noteType     笔记类型
	 */
	void deleteByBusinessIds(@Param("businessIds") List<String> businessIds,
						  @Param("noteType") Integer noteType);

	/**
	 * 查询由独特
	 *
	 * @param userId       用户id
	 * @param noteType     笔记类型
	 * @param businessType 业务类型
	 * @param businessId   业务id
	 * @return {@code Note }
	 */
	Note selectByUnique(@Param("userId") String userId, @Param("noteType") Integer noteType,
						@Param("businessType") Integer businessType, @Param("businessId") String businessId);

	/**
	 * 更新报告由id
	 *
	 * @param note 笔记
	 * @return int
	 */
	int updateReportByID(Note note);

	/**
	 * 计数由用户id
	 *
	 * @param userId      用户id
	 * @param noteType    笔记类型
	 * @param resourceIds 资源id列表
	 * @return int
	 */
	int countByUserId(@Param("userId") String userId, @Param("noteType") Integer noteType, @Param("resourceIds") List<String> resourceIds);

	List<ClassroomNoteEvalBo> selectByLesson(List<String> lessonIds, String userId);
}