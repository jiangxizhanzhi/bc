package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.context.mission.CompletionStatus;
import com.cyberpeace.cr.crbase.service.context.mission.CrTaskScore;
import com.cyberpeace.cr.crbase.service.context.score.TaskScoreContext;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingPersonalStatistics;

/**
 * 靶场任务Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `CrTaskMapper` 的接口，它继承了 `BaseMapper<CrTask>` 接口，并使用了 MyBatis 和 MapStruct 注解。以下是代码的主要功能和特点： 
 * 1. **接口定义**： 
 * - `CrTaskMapper` 是一个数据访问对象（DAO）接口，用于与数据库中的 `CrTask` 表进行交互。 
 * - 它通过 MyBatis 的注解方式实现了对数据库的操作。 
 * 2. **继承关系**： 
 * - 继承了 `BaseMapper<CrTask>`，这意味着它可以使用 MyBatis-Plus 提供的基本 CRUD 操作。 
 * 3. **方法列表**： 
 * - 接口中定义了多个查询和计数方法，涵盖了基于不同条件的查询需求，例如用户 ID、任务 ID、房间 ID、状态列表等。 
 * - 这些方法主要用于从数据库中获取特定的 `CrTask` 对象或统计信息。 
 * 4. **注解使用**： 
 * - 使用了 `@Mapper` 注解表明这是一个 MyBatis 的 Mapper 接口。 
 * - 使用了 `@Repository` 注解表明这是一个 Spring 的数据访问组件。 
 * 5. **功能分类**： 
 * - **基本查询**：如按用户 ID 和任务 ID 查询任务，按房间 ID 查询任务等。 
 * - **统计功能**：如按房间 ID 和用户 ID 列表计数未报告的任务数量。 
 * - **复杂查询**：如按状态列表和业务类型查询任务，查询个人统计学信息等。 
 * 6. **参数传递**： 
 * - 多个方法使用了 `@Param` 注解来明确指定参数名称，便于在 SQL 映射文件中引用。 
 * 7. **返回类型**： 
 * - 返回类型多样，包括 `CrTask` 对象、`List<CrTask>`、`Integer`、`List<TaskScoreContext>` 等，以满足不同的业务需求。 
 * 总结来说，这个接口提供了丰富的数据库操作方法，支持多种复杂的查询和统计功能，适用于靶场任务管理系统的数据访问层。 
**/
@Mapper
@Repository
public interface CrTaskMapper extends BaseMapper<CrTask> {

	/**
	 * 查询由用户id和任务id
	 *
	 * @param userId    用户id
	 * @param missionId 任务id
	 * @return {@code CrTask }
	 */
	CrTask selectByUserIdAndMissionId(String userId, String missionId);

	/**
	 * 查询由房间id和用户id
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code List<CrTask> }
	 */
	List<CrTask> selectByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 查询由房间id和用户id列表
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id列表
	 * @return {@code List<CrTask> }
	 */
	List<CrTask> selectByRoomIdAndUserIds(String roomId, List<String> userIds);

	/**
	 * 计数由房间id和用户id
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code Integer }
	 */
	Integer countByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 查询由房间id和状态列表
	 *
	 * @param roomId     房间id
	 * @param statusList 状态列表
	 * @return {@code List<CrTask> }
	 */
	List<CrTask> selectByRoomIdAndStatusList(String roomId, List<Integer> statusList);

	/**
	 * 计数由房间id和状态列表
	 *
	 * @param roomId     房间id
	 * @param statusList 状态列表
	 * @return {@code Integer }
	 */
	Integer countByRoomIdAndStatusList(String roomId, List<Integer> statusList);

	/**
	 * 计数由房间id和用户id和状态列表
	 *
	 * @param roomId     房间id
	 * @param userId     用户id
	 * @param statusList 状态列表
	 * @return {@code Integer }
	 */
	Integer countByRoomIdAndUserIdAndStatusList(String roomId, String userId,
			List<Integer> statusList);

	/**
	 * 查询由房间id和用户id和状态列表
	 *
	 * @param roomId     房间id
	 * @param userId     用户id
	 * @param statusList 状态列表
	 * @return {@code List<CrTask> }
	 */
	List<CrTask> selectByRoomIdAndUserIdAndStatusList(String roomId, String userId,
			List<Integer> statusList);

	/**
	 * 查询由房间id和用户id列表和状态列表
	 *
	 * @param roomId     房间id
	 * @param userIds    用户id列表
	 * @param statusList 状态列表
	 * @return {@code List<CrTask> }
	 */
	List<CrTask> selectByRoomIdAndUserIdsAndStatusList(String roomId, List<String> userIds,
			List<Integer> statusList);

	/**
	 * 查询由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code List<CrTask> }
	 */
	List<CrTask> selectByRoomId(String roomId);

	/**
	 * 查询由岗位
	 *
	 * @param roleId 岗位id
	 * @return {@code List<CrTask> }
	 */
	List<CrTask> selectByRole(String roleId);

	/**
	 * 查询由角色
	 *
	 * @param roleIds 岗位id列表
	 * @return {@code List<CrTask> }
	 */
	List<CrTask> selectByRoles(List<String> roleIds);

	/**
	 * 查询由房间id和任务id列表
	 *
	 * @param roomId     房间id
	 * @param missionIds 任务id列表
	 * @return {@code List<CrTask> }
	 */
	List<CrTask> selectByRoomIdAndMissionIds(String roomId, List<String> missionIds);

	/**
	 * 查询报道由用户id列表和任务id列表
	 *
	 * @param roomId     房间id
	 * @param userIds    用户id列表
	 * @param missionIds 任务id列表
	 * @return {@code List<CrTask> }
	 */
	List<CrTask> selectReportedByUserIdsAndMissionIds(String roomId, List<String> userIds,
			List<String> missionIds);

	/**
	 * 计数未报告由房间id和用户id列表
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id列表
	 * @return {@code Integer }
	 */
	Integer countUnreportedByRoomIdAndUserIds(String roomId, List<String> userIds);

	/**
	 * 查询由任务id
	 *
	 * @param missionId 任务id
	 * @return {@code List<CrTask> }
	 */
	List<CrTask> selectByMissionId(String missionId);

	/**
	 * 查询id列表由房间id和用户id列表
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id列表
	 * @return {@code List<String> }
	 */
	List<String> selectIdsByRoomIdAndUserIds(String roomId, List<String> userIds);

	/**
	 * 查询id列表由房间id和用户id
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code List<String> }
	 */
	List<String> selectIdsByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 批量插入
	 *
	 * @param tasks 任务
	 */
	void batchInsert(List<CrTask> tasks);

	/**
	 * 计数由房间id和岗位id列表和状态列表
	 *
	 * @param roomId     房间id
	 * @param crRoleIds  靶场岗位id列表
	 * @param statusList 状态列表
	 * @return {@code Integer }
	 */
	Integer countByRoomIdAndRoleIdsAndStatusList(String roomId, List<String> crRoleIds,
			List<Integer> statusList);

	/**
	 * 计数由房间id和岗位id列表
	 *
	 * @param roomId    房间id
	 * @param crRoleIds 靶场岗位id列表
	 * @return {@code Integer }
	 */
	Integer countByRoomIdAndRoleIds(String roomId, List<String> crRoleIds);

	/**
	 * RTKS-读取任务得分（单兵取最新）
	 *
	 * @param userId
	 * @param missionTemplateIds
	 * @return
	 */
	List<CrTaskScore> getScoreByMissionTemplateIdAndUserId(@Param("userId") String userId,
			@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 读取任务完成状态： 用户提交答案即视为完成
	 *
	 * @param userId
	 * @param missionTemplateIds
	 * @return
	 */
	List<CompletionStatus> getCompletionStatusByUserIdAndMissionTemplateIds(
			@Param("userId") String userId,
			@Param("missionTemplateIds") Set<String> missionTemplateIds);

	/**
	 * 查询由状态列表和类型
	 *
	 * @param statusList   状态列表
	 * @param businessType 业务类型
	 * @return {@code List<CrTask> }
	 */
	List<CrTask> selectByStatusListAndType(List<Integer> statusList, Integer businessType);

	/**
	 * 查询个人统计学
	 *
	 * @param statusList   状态列表
	 * @param businessType 业务类型
	 * @param limit        限制
	 * @param user_ids     用户id列表
	 * @return {@code List<TrainingPersonalStatistics> }
	 */
	List<TrainingPersonalStatistics> selectPersonalStatistics(List<Integer> statusList,
			Integer businessType, Integer limit, List<String> user_ids);

	/**
	 * 查询都个人统计学
	 *
	 * @param statusList   状态列表
	 * @param businessType 业务类型
	 * @param limit        限制
	 * @return {@code List<TrainingPersonalStatistics> }
	 */
	List<TrainingPersonalStatistics> selectAllPersonalStatistics(List<Integer> statusList,
			Integer businessType, Integer limit);

	/**
	 * 查询由任务id列表
	 *
	 * @param missionIds 任务id列表
	 * @return {@code List<CrTask> }
	 */
	List<CrTask> selectByMissionIds(List<String> missionIds);

	/**
	 * 查询得分上下文由房间id和用户id
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code List<TaskScoreContext> }
	 */
	List<TaskScoreContext> selectScoreContextByRoomIdAndUserId(@Param("roomId") String roomId,
			@Param("userId") String userId);
}

