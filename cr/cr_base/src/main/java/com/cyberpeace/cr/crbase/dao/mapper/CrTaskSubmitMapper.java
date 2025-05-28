/**
 * 这段代码定义了一个名为 `CrTaskSubmitMapper` 的 MyBatis Mapper 接口，用于操作数据库中的 `CrTaskSubmit` 实体类。以下是代码的主要分析和总结： 
 * ### 主要功能： 
 * 该接口继承了 `BaseMapper<CrTaskSubmit>`，这意味着它已经具备了基本的 CRUD 操作功能（如 `insert`, `delete`, `update`, `select` 等），同时提供了自定义方法来满足特定业务需求。 
 * ### 自定义方法： 
 * 1. **`selectByRoomId(String roomId)`** 
 * 根据房间 ID 查询所有相关的任务提交记录。 
 * 2. **`selectLastByTaskId(String taskId)`** 
 * 根据任务 ID 查询最新的任务提交记录。 
 * 3. **`listCrTaskSubmit(Page page, MissionEvaluationQuery listQuery)`** 
 * 分页查询任务提交记录，并支持基于 `MissionEvaluationQuery` 的复杂条件筛选。 
 * 4. **`listRbCrTaskSubmit(Page page, MissionEvaluationQuery listQuery)`** 
 * 类似于 `listCrTaskSubmit`，但可能是针对某种特殊场景或角色的查询。 
 * 5. **`selectOrderedSubmitsByTask(String resourceId)`** 
 * 根据资源 ID 查询有序的任务提交记录。 
 * 6. **`selectByRoomIdAndUsers(String roomId, List<String> userIds)`** 
 * 根据房间 ID 和用户 ID 列表查询任务提交记录。 
 * 7. **`list(String roomId, String roleId, String userId, Integer evaluateStatus)`** 
 * 根据多个条件（房间 ID、岗位 ID、用户 ID 和评估状态）查询任务提交记录，并返回 `EvaluationListBo` 对象列表。 
 * 8. **`getMissionBoById(String taskSubmitId)`** 
 * 根据任务提交 ID 查询并返回 `EvaluationMissionBo` 对象。 
 * ### 特点： 
 * - 使用了 `@Mapper` 注解，表明这是一个 MyBatis Mapper 接口。 
 * - 使用了 `@Repository` 注解，表明这是一个 Spring 容器管理的持久层组件。 
 * - 方法参数中使用了 `@Param` 注解，确保传递的参数在 SQL 映射文件中有明确的命名。 
 * - 返回值类型包括基本集合（如 `List`）、分页对象（如 `IPage`）以及自定义业务对象（如 `EvaluationMissionBo`）。 
 * ### 总结： 
 * 该接口设计合理，既利用了 MyBatis 的基础功能，又通过扩展方法满足了复杂的业务需求。它主要服务于与任务提交相关的数据操作，适合用于靶场任务管理系统的后端开发。 
**/
package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationMissionBo;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListBo;

/**
 * 靶场任务提交Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface CrTaskSubmitMapper extends BaseMapper<CrTaskSubmit> {

	/**
	 * 查询由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code List<CrTaskSubmit> }
	 */
	List<CrTaskSubmit> selectByRoomId(String roomId);

	/**
	 * 查询最后由任务id
	 *
	 * @param taskId 任务id
	 * @return {@code CrTaskSubmit }
	 */
	CrTaskSubmit selectLastByTaskId(String taskId);

	/**
	 * 列表靶场任务提交
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<MissionEvaluationContext> }
	 */
	IPage<MissionEvaluationContext> listCrTaskSubmit(@Param("page") Page page,
			@Param("listQuery") MissionEvaluationQuery listQuery);

	/**
	 * 列表RB靶场任务提交
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<MissionEvaluationContext> }
	 */
	IPage<MissionEvaluationContext> listRbCrTaskSubmit(@Param("page") Page page,
			@Param("listQuery") MissionEvaluationQuery listQuery);

	/**
	 * 查询命令提交由任务
	 *
	 * @param resourceId 资源id
	 * @return {@code List<CrTaskSubmit> }
	 */
	List<CrTaskSubmit> selectOrderedSubmitsByTask(String resourceId);

	/**
	 * 查询由房间id和用户
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id列表
	 * @return {@code List<CrTaskSubmit> }
	 */
	List<CrTaskSubmit> selectByRoomIdAndUsers(String roomId, List<String> userIds);

	/**
	 * 列表
	 *
	 * @param roomId         房间id
	 * @param roleId         岗位id
	 * @param userId         用户id
	 * @param evaluateStatus 评估状态
	 * @return {@code List<EvaluationListBo> }
	 */
	List<EvaluationListBo> list(@Param("roomId") String roomId, @Param("roleId") String roleId,
			@Param("userId") String userId, @Param("evaluateStatus") Integer evaluateStatus);

	/**
	 * 获取任务博由id
	 *
	 * @param taskSubmitId 任务提交id
	 * @return {@code EvaluationMissionBo }
	 */
	EvaluationMissionBo getMissionBoById(@Param("taskSubmitId") String taskSubmitId);
}
