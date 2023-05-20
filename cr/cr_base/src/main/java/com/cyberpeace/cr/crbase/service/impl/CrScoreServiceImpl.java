package com.cyberpeace.cr.crbase.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.JsonUtil;
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
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Maps;

/**
 *
 */
@Service
public class CrScoreServiceImpl extends ServiceImpl<CrScoreMapper, CrScore> implements CrScoreService {

	@Autowired
	private CrScoreMapper crScoreMapper;

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public CrScore getByRoomIdAndUser(String roomId, String userId) {
		return crScoreMapper.getByRoomIdAndUser(roomId, userId);
	}

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

	@Override
	public List<CrScore> selectByRoomIdAndUserIds(String roomId, List<String> userIds) {
		return crScoreMapper.selectByRoomIdAndUserIds(roomId, userIds);
	}

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

	@Override
	public List<CrScore> getScoreByRoomId(String roomId) {
		return crScoreMapper.selectByRoomId(roomId);
	}

	@Override
	public List<StudentScoreListRsp> getScoreListRsp(String roomId, List<CrRole> roleList) {

		// get score
		List<CrScore> scoreList = getScoreByRoomId(roomId);
		// 按评分时间倒序
		scoreList.sort(Comparator.comparing(CrScore::getUpdateTime).reversed());
		Set<String> scoredUsers = scoreList.stream().map(CrScore::getUserId)
				.collect(Collectors.toSet());

		// get users
		List<StudentScoreListRsp> rspList = Lists.newArrayList();
		List<String> userIdList = Lists.newArrayList();
		for (CrRole role : roleList) {
			List<String> userIds = JsonUtil.toJsonObjectArray(role.getUserIds(), String.class);
			for (String userId : userIds) {
				StudentScoreListRsp rsp = new StudentScoreListRsp();
				rsp.setUserId(userId);
				rsp.setRoleId(role.getResourceId());
				rsp.setRoleName(role.getName());
				rsp.setRoleGroup(role.getRoleGroup());
				rspList.add(rsp);
				userIdList.add(userId);
			}
		}

		// set user info
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIdList);
		for (StudentScoreListRsp rsp : rspList) {
			UserInfo userInfo = userInfoMap.get(rsp.getUserId());
			if (null != userInfo) {
				rsp.setUserAvatar(userInfo.getAvatarUrl());
				rsp.setName(userInfo.getName());
			}
		}
		// 未评分的在前
		List<StudentScoreListRsp> res = Lists.newArrayList();
		for (StudentScoreListRsp rsp : rspList) {
			String userId = rsp.getUserId();
			if (!scoredUsers.contains(userId)) {
				rsp.setScored(false);
				res.add(rsp);
			}
		}
		Map<String, StudentScoreListRsp> rspMap = StreamApiUtils.toMap(rspList,
				StudentScoreListRsp::getUserId, rsp -> rsp);
		// 按排过序的crScore列表顺序插入结果
		for (CrScore score : scoreList) {
			StudentScoreListRsp rsp = rspMap.get(score.getUserId());
			if (null != rsp) {
				rsp.setScore(score.getFinalScore());
				rsp.setScoreTime(score.getUpdateTime());
				rsp.setScored(true);
				res.add(rsp);
			}
		}
		return res;
	}
}
