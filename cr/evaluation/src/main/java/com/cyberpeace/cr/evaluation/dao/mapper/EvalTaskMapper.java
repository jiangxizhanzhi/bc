/**
 * 这段代码定义了一个名为 `EvalTaskMapper` 的接口，该接口继承自 MyBatis-Plus 的 `BaseMapper`。这个接口主要用于数据访问层（DAO），用于执行与 `EvalTask` 实体对象相关的数据库操作。以下是详细的分析： 
 * ### 包与导入 
 * - **包声明**：`com.cyberpeace.cr.evaluation.dao.mapper` 
 * - **导入的类**： 
 * - `java.util.List`: 用于返回多个结果。 
 * - `org.apache.ibatis.annotations.Mapper`: 注解表明这是一个 MyBatis 的 Mapper 接口。 
 * - `org.apache.ibatis.annotations.Param`: 用于标记方法参数，以便在 XML 配置文件中引用。 
 * - `org.springframework.stereotype.Repository`: 表明这是一个 Spring 的仓库组件。 
 * - `com.baomidou.mybatisplus.core.mapper.BaseMapper`: MyBatis-Plus 提供的通用数据访问接口。 
 * - `com.cyberpeace.cr.evaluation.dao.entity.EvalTask`: 实体类。 
 * - `com.cyberpeace.cr.evaluation.service.context.EvalTaskSlimContext`: 一个轻量级的任务上下文对象。 
 * ### 类声明 
 * - `@Mapper`: 将该接口标识为 MyBatis 的 Mapper。 
 * - `@Repository`: 将该接口标识为 Spring 的 Repository，便于依赖注入。 
 * - `public interface EvalTaskMapper extends BaseMapper<EvalTask>`: 继承了 MyBatis-Plus 的 `BaseMapper`，从而获得了一些基本的 CRUD 操作方法。 
 * ### 方法 
 * 1. `selectSlimTaskListByTemplateIdAndStatus(String templateId, Integer status)`: 根据模板ID和状态查询苗条任务列表。 
 * 2. `selectIdsByTemplateIdAndStatus(String templateId, Integer status)`: 根据模板ID和状态查询任务ID列表。 
 * 3. `selectByIdAndStatus(String taskId, Integer status)`: 根据任务ID和状态查询任务信息。 
 * 4. `getTemplateIdByTaskId(String taskId)`: 根据任务ID获取模板ID。 
 * 5. `isNotFinished(String taskId)`: 判断指定任务是否未完成。 
 * 6. `existByNameAndTemplateId(String taskName, String templateId, String taskId)`: 检查任务名称、模板ID和任务ID是否存在重复。 
 * ### 总结 
 * - 这段代码主要定义了一个与 `EvalTask` 实体相关的数据访问接口。 
 * - 它包含了多种查询方法，用于根据不同的条件查询任务信息。 
 * - 通过继承 `BaseMapper`，已经具备了基本的 CRUD 操作功能。 
 * - 使用了 MyBatis 和 Spring 的相关注解，使其能够被 Spring 容器管理，并且与 MyBatis 框架集成。 
 * 这些方法覆盖了常见的数据查询需求，使得业务逻辑层可以方便地调用这些方法来获取所需的数据。 
**/
package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskSlimContext;

/**
 * 评估任务Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface EvalTaskMapper extends BaseMapper<EvalTask> {

	/**
	 * 查询苗条任务列表由模板id和状态
	 *
	 * @param templateId 模板id
	 * @param status     状态
	 * @return {@code List<EvalTaskSlimContext> }
	 */
	List<EvalTaskSlimContext> selectSlimTaskListByTemplateIdAndStatus(String templateId,
			Integer status);

	/**
	 * 查询id列表由模板id和状态
	 *
	 * @param templateId 模板id
	 * @param status     状态
	 * @return {@code List<String> }
	 */
	List<String> selectIdsByTemplateIdAndStatus(String templateId, Integer status);

	/**
	 * 查询由id和状态
	 *
	 * @param taskId 任务id
	 * @param status 状态
	 * @return {@code EvalTask }
	 */
	EvalTask selectByIdAndStatus(String taskId, Integer status);

	/**
	 * 获取模板id由任务id
	 *
	 * @param taskId 任务id
	 * @return {@code String }
	 */
	String getTemplateIdByTaskId(String taskId);

	/**
	 * 未完成
	 *
	 * @param taskId 任务id
	 * @return {@code Boolean }
	 */
	Boolean isNotFinished(String taskId);

	/**
	 * 校验任务名称
	 *
	 * @param taskName   任务名称
	 * @param templateId 模板id
	 * @param taskId     任务id
	 * @return {@code Boolean }
	 */
	Boolean existByNameAndTemplateId(@Param("taskName") String taskName,
			@Param("templateId") String templateId, @Param("taskId") String taskId);

}
