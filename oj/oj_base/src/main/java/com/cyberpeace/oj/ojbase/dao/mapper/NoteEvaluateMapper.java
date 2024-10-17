package com.cyberpeace.oj.ojbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.NoteEvaluate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoteEvaluateMapper extends BaseMapper<NoteEvaluate> {

	/**
	 * 根据实验报告编号查询评估结果
	 * @param noteId
	 * @return
	 */
	NoteEvaluate selectByNote(@Param("noteId") String noteId);

	/**
	 * 根据报告编号更新数据
	 * @param noteEvaluate
	 */
	void updateByNote(NoteEvaluate noteEvaluate);

	/**
	 * 批量查询数据
	 * @param noteIds
	 * @return
	 */
	List<NoteEvaluate> selectBatchNoteIds(@Param("noteIds") List<String> noteIds,
			@Param("isPublic") Boolean isPublic);

	List<NoteEvaluate> selectBatchByNoteIds(@Param("noteIds") List<String> noteIds);
}
