package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.mapper.CrScoreMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrScoreService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.mission.MissionCategory;
import com.cyberpeace.cr.crbase.service.context.mission.MissionsStatistics;
import com.google.common.collect.Maps;

/**
 * cr分数服务impl
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Service
public class CrScoreServiceImpl extends ServiceImpl<CrScoreMapper, CrScore> implements CrScoreService {

	@Autowired
	CrScoreMapper crScoreMapper;

	@Autowired
	MissionService missionService;

	@Autowired
	CrRoleService crRoleService;

	@Autowired
	CrTaskService crTaskService;

	/**
	 * 通过房间id和用户获取
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@link CrScore}
	 */
	@Override
	public CrScore getByRoomIdAndUser(String roomId, String userId) {
		return crScoreMapper.getByRoomIdAndUser(roomId, userId);
	}

	/**
	 * 获取任务统计数据
	 *
	 * @param roomId     房间id
	 * @param crRoleList cr角色列表
	 * @return {@link MissionsStatistics}
	 */
	@Override
	public MissionsStatistics getMissionsStatistics(String roomId, List<CrRole> crRoleList) {
		MissionsStatistics missionsStatistics = new MissionsStatistics();

		Integer missionCount = 0;
		for (CrRole crRole : crRoleList) {
			Integer roleMissionCount = crRoleService.getMissionCount(crRole);
			List<String> userList = crRole.getUserIdList();
			missionCount += roleMissionCount * userList.size();
		}
		List<String> crRoleIds = StreamApiUtils.map(crRoleList, CrRole::getResourceId);

		missionsStatistics.setMissionCount(missionCount);
		missionsStatistics.setUnpublishedProportion(
				missionCount - crTaskService.countByRoomIdAndRoleIds(roomId, crRoleIds));
		missionsStatistics.setNotSubmitProportion(
				crTaskService.countUnsubmittedByRoomIdAndRoleIds(roomId, crRoleIds));
		missionsStatistics.setNotFinishProportion(
				crTaskService.countUnfinishedByRoomIdAndRoleIds(roomId, crRoleIds));
		missionsStatistics.setFinishProportion(
				crTaskService.countFinishedByRoomIdAndRoleIds(roomId, crRoleIds));
		return missionsStatistics;
	}

	/**
	 * 通过房间id和用户id获取
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id
	 * @return {@link List}<{@link CrScore}>
	 */
	@Override
	public List<CrScore> selectByRoomIdAndUserIds(String roomId, List<String> userIds) {
		return crScoreMapper.selectByRoomIdAndUserIds(roomId, userIds);
	}

	/**
	 * 获取任务类别
	 *
	 * @param roomId     房间id
	 * @param crRoleList cr角色列表
	 * @return {@link Map}<{@link String}, {@link Integer}>
	 */
	@Override
	public Map<String, Integer> getMissionsCategory(String roomId, List<CrRole> crRoleList) {
		Map<String, Integer> missionsCategory = Maps.newHashMap();

		List<Mission> missionList = missionService.batchGetByRoomAndCrRoles(roomId, crRoleList);

		List<MissionCategory> missionCategories = missionService.listCategories();

		Map<String, String> missionCategoryMap = missionCategories.stream()
				.collect(Collectors.toMap(MissionCategory::getResourceId, bo -> bo.getName()));

		for (Mission mission : missionList) {
			Integer count = missionsCategory
					.getOrDefault(missionCategoryMap.get(mission.getCategoryId()), 0) + 1;
			missionsCategory.put(missionCategoryMap.get(mission.getCategoryId()), count);
		}

		return missionsCategory;
	}
}
