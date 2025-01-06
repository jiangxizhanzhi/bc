package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.context.mission.CompletionStatus;
import com.cyberpeace.cr.crbase.service.context.mission.CrTaskScore;
import com.cyberpeace.cr.crbase.service.context.score.TaskScoreContext;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingPersonalStatistics;

@Mapper
@Repository
public interface CrTaskMapper extends BaseMapper<CrTask> {

	CrTask selectByUserIdAndMissionId(String userId, String missionId);

	List<CrTask> selectByRoomIdAndUserId(String roomId, String userId);

	List<CrTask> selectByRoomIdAndUserIds(String roomId, List<String> userIds);

	Integer countByRoomIdAndUserId(String roomId, String userId);

	List<CrTask> selectByRoomIdAndStatusList(String roomId, List<Integer> statusList);

	Integer countByRoomIdAndStatusList(String roomId, List<Integer> statusList);

	Integer countByRoomIdAndUserIdAndStatusList(String roomId, String userId,
			List<Integer> statusList);

	List<CrTask> selectByRoomIdAndUserIdAndStatusList(String roomId, String userId,
			List<Integer> statusList);

	List<CrTask> selectByRoomIdAndUserIdsAndStatusList(String roomId, List<String> userIds,
			List<Integer> statusList);

	List<CrTask> selectByRoomId(String roomId);

	List<CrTask> selectByRole(String roleId);

	List<CrTask> selectByRoles(List<String> roleIds);

	List<CrTask> selectByRoomIdAndMissionIds(String roomId, List<String> missionIds);

	List<CrTask> selectReportedByUserIdsAndMissionIds(String roomId, List<String> userIds,
			List<String> missionIds);

	Integer countUnreportedByRoomIdAndUserIds(String roomId, List<String> userIds);

	List<CrTask> selectByMissionId(String missionId);

	List<String> selectIdsByRoomIdAndUserIds(String roomId, List<String> userIds);

	List<String> selectIdsByRoomIdAndUserId(String roomId, String userId);

	void batchInsert(List<CrTask> tasks);

	Integer countByRoomIdAndRoleIdsAndStatusList(String roomId, List<String> crRoleIds,
			List<Integer> statusList);

	Integer countByRoomIdAndRoleIds(String roomId, List<String> crRoleIds);

	/**
	 * RTKS-读取任务得分（单兵取最新）
	 * @param userId
	 * @param missionTemplateIds
	 * @return
	 */
	List<CrTaskScore> getScoreByMissionTemplateIdAndUserId(@Param("userId") String userId,
			@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 读取任务完成状态： 用户提交答案即视为完成
	 * @param userId 
	 * @param missionTemplateIds
	 * @return
	 */
	List<CompletionStatus> getCompletionStatusByUserIdAndMissionTemplateIds(
			@Param("userId") String userId,
			@Param("missionTemplateIds") Set<String> missionTemplateIds);

	List<CrTask> selectByStatusListAndType(List<Integer> statusList, Integer businessType);

	List<TrainingPersonalStatistics> selectPersonalStatistics(List<Integer> statusList,
			Integer businessType, Integer limit, List<String> user_ids);

	List<TrainingPersonalStatistics> selectAllPersonalStatistics(List<Integer> statusList,
			Integer businessType, Integer limit);

	List<CrTask> selectByMissionIds(List<String> missionIds);

	List<TaskScoreContext> selectScoreContextByRoomIdAndUserId(@Param("roomId") String roomId,
			@Param("userId") String userId);
}
