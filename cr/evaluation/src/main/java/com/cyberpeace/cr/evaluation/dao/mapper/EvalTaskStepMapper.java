package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTaskStep;

/**
 * 评估任务步Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTaskStepMapper` 的接口，该接口继承自 MyBatis-Plus 的 `BaseMapper` 接口，并且使用了 MyBatis 的 `@Mapper` 注解以及 Spring 的 `@Repository` 注解。这个接口主要用于数据访问层（DAO），用于操作与评估任务步骤相关的数据库表。 
 * ### 主要组成部分： 
 * 1. **注解**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 类。 
 * - `@Repository`：表明这是一个数据访问组件，通常用于在 Spring 应用中定义 DAO 层的 Bean。 
 * 2. **泛型参数**： 
 * - `EvalTaskStep`：表示该 Mapper 操作的数据实体类是 `EvalTaskStep`。 
 * 3. **方法**： 
 * - `selectStepIdsByTaskId`：根据给定的任务 ID 查询步骤 ID 列表。 
 * - `selectStepsByTaskId`：根据给定的任务 ID 查询所有步骤对象。 
 * - `isNotFinished`：判断指定任务 ID 和阶段是否还有未完成的步骤。 
 * ### 方法详细说明： 
 * - **`selectStepIdsByTaskId`**： 
 * - 参数：`String taskId` 
 * - 返回值：`List<String>`，表示步骤 ID 列表。 
 * - **`selectStepsByTaskId`**： 
 * - 参数：`String taskId` 
 * - 返回值：`List<EvalTaskStep>`，表示步骤对象列表。 
 * - **`isNotFinished`**： 
 * - 参数：`String taskId`，`Integer stage` 
 * - 返回值：`Boolean`，表示是否还有未完成的步骤。 
 * - 这个方法目前是未实现的，注释为“未完成”。 
 * ### 总结： 
 * 该接口主要提供了三个方法来处理与评估任务步骤相关的数据库操作。其中两个方法已经实现了具体的功能，而第三个方法目前还处于未实现的状态。这些方法通过 MyBatis-Plus 提供的基础功能简化了数据库操作的编写。 
**/
@Mapper
@Repository
public interface EvalTaskStepMapper extends BaseMapper<EvalTaskStep> {

	/**
	 * 查询步id列表由任务id
	 *
	 * @param taskId 任务id
	 * @return {@code List<String> }
	 */
	List<String> selectStepIdsByTaskId(@Param("taskId") String taskId);

	/**
	 * 查询步骤由任务id
	 *
	 * @param taskId 任务id
	 * @return {@code List<EvalTaskStep> }
	 */
	List<EvalTaskStep> selectStepsByTaskId(@Param("taskId") String taskId);

	/**
	 * 未完成
	 *
	 * @param taskId 任务id
	 * @param stage  阶段
	 * @return {@code Boolean }
	 */
	Boolean isNotFinished(String taskId, Integer stage);

}

