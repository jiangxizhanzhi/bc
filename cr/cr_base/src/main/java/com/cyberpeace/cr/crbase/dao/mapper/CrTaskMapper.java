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
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingPersonalStatistics;

/**
 * cr任务映射器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Mapper
@Repository
public interface CrTaskMapper extends BaseMapper<CrTask> {

	/**
	 * 通过用户id和任务id获取
	 *
	 * @param userId    用户id
	 * @param missionId 任务id
	 * @return {@link CrTask}
	 */
	CrTask selectByUserIdAndMissionId(String userId, String missionId);

	/**
	 * 通过房间id和用户id获取
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@link List}<{@link CrTask}>
	 */
	List<CrTask> selectByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 通过房间id和用户id获取
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id
	 * @return {@link List}<{@link CrTask}>
	 */
	List<CrTask> selectByRoomIdAndUserIds(String roomId, List<String> userIds);

	/**
	 * 房间id和用户id计算数量
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@link Integer}
	 */
	Integer countByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 通过房间id和状态列表获取
	 *
	 * @param roomId     房间id
	 * @param statusList 状态列表
	 * @return {@link List}<{@link CrTask}>
	 */
	List<CrTask> selectByRoomIdAndStatusList(String roomId, List<Integer> statusList);

	/**
	 * 通过房间id和状态列表计算数量
	 *
	 * @param roomId     房间id
	 * @param statusList 状态列表
	 * @return {@link Integer}
	 */
	Integer countByRoomIdAndStatusList(String roomId, List<Integer> statusList);

	/**
	 * 通过房间id和用户id和状态列表计算数量
	 *
	 * @param roomId     房间id
	 * @param userId     用户id
	 * @param statusList 状态列表
	 * @return {@link Integer}
	 */
	Integer countByRoomIdAndUserIdAndStatusList(String roomId, String userId,
			List<Integer> statusList);

	/**
	 * 通过房间id和用户id和状态列表获取
	 *
	 * @param roomId     房间id
	 * @param userId     用户id
	 * @param statusList 状态列表
	 * @return {@link List}<{@link CrTask}>
	 */
	List<CrTask> selectByRoomIdAndUserIdAndStatusList(String roomId, String userId,
			List<Integer> statusList);

	/**
	 * 通过房间id和用户id和状态列表获取
	 *
	 * @param roomId     房间id
	 * @param userIds    用户id
	 * @param statusList 状态列表
	 * @return {@link List}<{@link CrTask}>
	 */
	List<CrTask> selectByRoomIdAndUserIdsAndStatusList(String roomId, List<String> userIds,
			List<Integer> statusList);

	/**
	 * 通过房间id获取
	 *
	 * @param roomId 房间id
	 * @return {@link List}<{@link CrTask}>
	 */
	List<CrTask> selectByRoomId(String roomId);

	/**
	 * 通过角色获取
	 *
	 * @param roleId 角色id
	 * @return {@link List}<{@link CrTask}>
	 */
	List<CrTask> selectByRole(String roleId);

	/**
	 * 通过角色获取
	 *
	 * @param roleIds 角色id
	 * @return {@link List}<{@link CrTask}>
	 */
	List<CrTask> selectByRoles(List<String> roleIds);

	/**
	 * 通过房间id和任务id获取
	 *
	 * @param roomId     房间id
	 * @param missionIds 任务id
	 * @return {@link List}<{@link CrTask}>
	 */
	List<CrTask> selectByRoomIdAndMissionIds(String roomId, List<String> missionIds);

	/**
	 * 获取报告通过用户id和任务id
	 *
	 * @param roomId     房间id
	 * @param userIds    用户id
	 * @param missionIds 任务id
	 * @return {@link List}<{@link CrTask}>
	 */
	List<CrTask> selectReportedByUserIdsAndMissionIds(String roomId, List<String> userIds,
			List<String> missionIds);

	/**
	 * 未报告房间id和用户id计算数量
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id
	 * @return {@link Integer}
	 */
	Integer countUnreportedByRoomIdAndUserIds(String roomId, List<String> userIds);

	/**
	 * 获取通过任务id
	 *
	 * @param missionId 任务id
	 * @return {@link List}<{@link CrTask}>
	 */
	List<CrTask> selectByMissionId(String missionId);

	/**
	 * 获取id通过房间id和用户id
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id
	 * @return {@link List}<{@link String}>
	 */
	List<String> selectIdsByRoomIdAndUserIds(String roomId, List<String> userIds);

	/**
	 * 获取id通过房间id和用户id
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@link List}<{@link String}>
	 */
	List<String> selectIdsByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 批量插入
	 *
	 * @param tasks 任务
	 */
	void batchInsert(List<CrTask> tasks);

	/**
	 * 通过房间id和角色id和状态列表计算数量
	 *
	 * @param roomId     房间id
	 * @param crRoleIds  cr角色id
	 * @param statusList 状态列表
	 * @return {@link Integer}
	 */
	Integer countByRoomIdAndRoleIdsAndStatusList(String roomId, List<String> crRoleIds,
			List<Integer> statusList);

	/**
	 * 通过房间id和角色id计算数量
	 *
	 * @param roomId    房间id
	 * @param crRoleIds cr角色id
	 * @return {@link Integer}
	 */
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

	/**
	 * 获取通过状态列表和类型
	 *
	 * @param statusList   状态列表
	 * @param businessType 业务类型
	 * @return {@link List}<{@link CrTask}>
	 */
	List<CrTask> selectByStatusListAndType(List<Integer> statusList, Integer businessType);

	/**
	 * 获取个人统计数据
	 *
	 * @param statusList   状态列表
	 * @param businessType 业务类型
	 * @param limit        限制
	 * @param user_ids     用户id
	 * @return {@link List}<{@link TrainingPersonalStatistics}>
	 */
	List<TrainingPersonalStatistics> selectPersonalStatistics(List<Integer> statusList,
			Integer businessType, Integer limit, List<String> user_ids);

	/**
	 * 获取所有个人统计数据
	 *
	 * @param statusList   状态列表
	 * @param businessType 业务类型
	 * @param limit        限制
	 * @return {@link List}<{@link TrainingPersonalStatistics}>
	 */
	List<TrainingPersonalStatistics> selectAllPersonalStatistics(List<Integer> statusList,
			Integer businessType, Integer limit);
}
