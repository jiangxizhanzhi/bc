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
			@Param("isPublic") Boolean isPublic);

	@Override
	int updateById(Note note);

	int updateNote(Note note);

	List<String> selectByContext(@Param("context") NoteUniqueContext context);

	List<Note> selectByListQuery(@Param("noteListQuery") NoteListQuery noteListQuery,
								 @Param("isPublic") Boolean isPublic);
}