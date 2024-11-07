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
 * 靶场任务映射
 *
 * @author SN
 * @date 2024/11/07
 */
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
	 * 查询由角色
	 *
	 * @param roleId 角色id
	 * @return {@code List<CrTask> }
	 */
	List<CrTask> selectByRole(String roleId);

	/**
	 * 查询由角色
	 *
	 * @param roleIds 角色id列表
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
	 * 计数由房间id和角色id列表和状态列表
	 *
	 * @param roomId     房间id
	 * @param crRoleIds  靶场角色id列表
	 * @param statusList 状态列表
	 * @return {@code Integer }
	 */
	Integer countByRoomIdAndRoleIdsAndStatusList(String roomId, List<String> crRoleIds,
			List<Integer> statusList);

	/**
	 * 计数由房间id和角色id列表
	 *
	 * @param roomId    房间id
	 * @param crRoleIds 靶场角色id列表
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
