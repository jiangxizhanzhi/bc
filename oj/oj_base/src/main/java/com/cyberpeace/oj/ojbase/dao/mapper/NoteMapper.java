/**
 * 这段代码定义了一个名为 `NoteMapper` 的接口，它继承自 `BaseMapper<Note>`，并且使用了 MyBatis 的 `@Mapper` 注解。这个接口用于与数据库进行交互，执行各种针对 `Note` 实体的操作。以下是对该接口方法的简要分析： 
 * 1. **继承关系**: 
 * - `NoteMapper` 继承自 `com.baomidou.mybatisplus.core.mapper.BaseMapper<Note>`，这表明它是一个 MyBatis Plus 的基本映射器，提供了常见的 CRUD 操作。 
 * 2. **方法概述**: 
 * - `selectByCondition`: 根据 `NoteUniqueContext` 上下文查询单个 `Note`。 
 * - `pageByUserIdsAndContent`: 根据分页参数、查询条件、是否公开和是否提交来分页查询 `Note` 列表。 
 * - `updateById`: 更新指定 ID 的 `Note` 记录。 
 * - `updateNote`: 更新 `Note` 记录（具体更新哪些字段没有明确指出）。 
 * - `selectByContext`: 根据 `NoteUniqueContext` 上下文查询 `Note` 列表。 
 * - `selectByListQuery`: 根据 `NoteListQuery` 查询条件和是否公开查询 `Note` 列表。 
 * - `deleteByBusiness`: 根据业务 ID、注释类型和资源类型删除记录。 
 * - `selectByUnique`: 根据多个参数（如用户 ID、注释类型、业务类型和业务 ID）查询唯一的 `Note`。 
 * - `updateReportByID`: 根据 `Note` 更新报告记录。 
 * - `countByUserId`: 根据用户 ID、注释类型和资源 ID 列表计算记录数量。 
 * 3. **注解使用**: 
 * - 使用了 `@Param` 注解来指定方法参数的名称，以便在 MyBatis 的 XML 配置文件中引用这些参数。 
 * - 使用了 `@Mapper` 注解来标识这是一个 MyBatis 的 Mapper 接口。 
 * 4. **数据类型**: 
 * - 方法返回值包括 `Note`, `IPage<Note>`, `List<String>`, `List<Note>`, `int`, `void` 等。 
 * - 参数类型包括 `NoteUniqueContext`, `NoteListQuery`, `Boolean`, `String`, `Integer`, `List<String>` 等。 
 * 5. **文档注释**: 
 * - 每个方法都有详细的文档注释，描述了方法的功能、输入参数和返回值，这有助于其他开发者理解和使用这些方法。 
 * 总体来说，`NoteMapper` 接口提供了一套丰富的功能来操作 `Note` 数据实体，涵盖了增删改查等各种常见需求，并且通过 MyBatis 和 MyBatis Plus 提供了灵活的数据库访问方式。 
**/
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