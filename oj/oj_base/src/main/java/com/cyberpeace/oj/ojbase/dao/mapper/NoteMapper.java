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

@Mapper
public interface NoteMapper extends BaseMapper<Note> {

	Note selectByCondition(@Param("context") NoteUniqueContext context);

	IPage<Note> pageByUserIdsAndContent(Page page,
			@Param("noteListQuery") NoteListQuery noteListQuery,
			@Param("isPublic") Boolean isPublic,
			@Param("isSubmit") Boolean isSubmit);

	@Override
	int updateById(Note note);

	int updateNote(Note note);

	List<String> selectByContext(@Param("context") NoteUniqueContext context);

	List<Note> selectByListQuery(@Param("noteListQuery") NoteListQuery noteListQuery,
			@Param("isPublic") Boolean isPublic);

	void deleteByBusiness(@Param("businessId") String businessId,
			@Param("noteType") Integer noteType, @Param("resourceType") Integer resourceType);

	Note selectByUnique(@Param("userId") String userId, @Param("noteType") Integer noteType,
						@Param("businessType") Integer businessType, @Param("businessId") String businessId);

	int updateReportByID(Note note);

	int countByUserId(@Param("userId") String userId, @Param("noteType") Integer noteType, @Param("resourceIds") List<String> resourceIds);
}