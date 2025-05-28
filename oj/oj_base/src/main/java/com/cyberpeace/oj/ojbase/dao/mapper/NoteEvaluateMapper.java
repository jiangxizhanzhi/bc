/**
 * 这段代码定义了一个名为 `NoteEvaluateMapper` 的接口，该接口继承了 `BaseMapper<NoteEvaluate>`。`BaseMapper` 是 MyBatis-Plus 提供的一个基础接口，提供了基本的 CRUD 操作。这个自定义的 Mapper 接口用于处理与 `NoteEvaluate` 实体相关的数据库操作。 
 * ### 主要内容 
 * 1. **注解** 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 类。 
 * 2. **方法** 
 * - `NoteEvaluate selectByNote(@Param("noteId") String noteId);` 
 * - 根据给定的实验报告编号 (`noteId`) 查询对应的评估结果 (`NoteEvaluate`)。 
 * - `void updateByNote(NoteEvaluate noteEvaluate);` 
 * - 根据传入的 `NoteEvaluate` 对象中的信息更新数据库中对应的记录。 
 * - `List<NoteEvaluate> selectBatchNoteIds(@Param("noteIds") List<String> noteIds, @Param("isPublic") Boolean isPublic);` 
 * - 根据给定的实验报告编号列表 (`noteIds`) 和是否公开 (`isPublic`) 查询多个评估结果 (`List<NoteEvaluate>`)。 
 * ### 总结 
 * - `NoteEvaluateMapper` 接口扩展了 MyBatis-Plus 提供的 `BaseMapper`，因此它已经包含了基本的增删改查功能。 
 * - 这个接口提供了三个额外的方法，用于根据实验报告编号查询评估结果、更新评估信息以及批量查询评估信息。 
 * - 使用了 `@Param` 注解来指定方法参数的名称，这样可以在 MyBatis 的 XML 配置文件或注解方式的 SQL 语句中引用这些参数。 
**/
package com.cyberpeace.oj.ojbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.NoteEvaluate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 注意评估Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface NoteEvaluateMapper extends BaseMapper<NoteEvaluate> {

	/**
	 * 根据实验报告编号查询评估结果
	 *
	 * @param noteId
	 * @return
	 * @throws
	 * @see String
	 * @see NoteEvaluate
	 */
	NoteEvaluate selectByNote(@Param("noteId") String noteId);

	/**
	 * 根据报告编号更新数据
	 *
	 * @param noteEvaluate 注意评估
	 * @throws
	 * @see NoteEvaluate
	 */
	void updateByNote(NoteEvaluate noteEvaluate);

	/**
	 * 批量查询数据
	 *
	 * @param noteIds
	 * @param isPublic 是否公开
	 * @return
	 * @throws
	 * @see List<String>
	 * @see Boolean
	 * @see List<NoteEvaluate>
	 */
	List<NoteEvaluate> selectBatchNoteIds(@Param("noteIds") List<String> noteIds,
			@Param("isPublic") Boolean isPublic);
}
