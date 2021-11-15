package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;

@Mapper
@Repository
public interface CrTaskMapper extends BaseMapper<CrTask> {

	CrTask selectByUserIdAndMissionId(String userId, String missionId);

	List<CrTask> selectByRoomIdAndUserId(String roomId, String userId);

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
}
