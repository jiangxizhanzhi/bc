package com.cyberpeace.cr.crbase.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.MissionCompletion;
import com.cyberpeace.cr.crbase.service.context.MissionContext;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.MissionStageScheme;
import com.cyberpeace.cr.crbase.service.context.role.*;
import com.cyberpeace.integratation.model.UserPageContext;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 *
 */
@Service
public class CrRoleServiceImpl extends ServiceImpl<CrRoleMapper, CrRole> implements CrRoleService {

	@Autowired
	private CrRoleMapper crRoleMapper;

	@Autowired
	private MissionService missionService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CrTaskService crTaskService;

	@Override
	public CrRoleContext getRoles(String roomId) {
		CrRoleContext crRoleContext = new CrRoleContext();
		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(roomId);
		roleQuery.setRoleGroup(CrRole.RoleGroupCodeInteger.STUDENT.getValue());

		List<CrRole> crRoles = crRoleMapper.selectCrRoleByRoomId(roleQuery);
		if (crRoles == null) {
			throw new ResourceNotFoundException(roomId);
		}

		List<RoleData> roleResDataList = Lists.newArrayList();
		for (CrRole crRole : crRoles) {

			List<MissionSchemeObject> missionSchemeObjectList = getBaseMissionByRole(crRole);

			RoleData roleResData = new RoleData();
			roleResData.setName(crRole.getName());
			roleResData.setResourceId(crRole.getResourceId());
			roleResData.setMissionsList(missionSchemeObjectList);
			roleResData.setUsers(getUserByRoleUsers(crRole.getUserIds()));
			roleResDataList.add(roleResData);
		}
		crRoleContext.setRoles(roleResDataList);

		Map<String, MissionCompletion> missionCompletionMapping = missionService
				.getMissionStatusData(roomId);

		List<Mission> missionList = missionService.batchGetByRoom(roomId);
		ShowWriteup showWriteup = new ShowWriteup();
		showWriteup.setShowWriteup(true);
		List<Map<String, ShowWriteup>> showWriteupMapping = Lists.newArrayList();

		missionList.forEach(mission -> {
			if (mission.getShowWriteup()) {
				Map<String, ShowWriteup> map = Maps.newHashMap();
				map.put(mission.getResourceId(), showWriteup);
				showWriteupMapping.add(map);
			}
		});

		crRoleContext.setShowWriteupMapping(showWriteupMapping);
		crRoleContext.setMissionMapping(missionCompletionMapping);

		return crRoleContext;
	}

	@Override
	public List<MissionSchemeObject> getBaseMissionByRole(CrRole crRole) {
		List<MissionSchemeObject> missionSchemeObjectList = Lists.newArrayList();
		List<MissionStageScheme> missionStageSchemeList = JsonUtil
				.toJsonObjectArray(crRole.getMissionScheme(), MissionStageScheme.class);

		for (MissionStageScheme missionStageScheme : missionStageSchemeList) {
			List<Mission> missionList = missionService.batchGet(missionStageScheme.getMissions());

			missionList.forEach(mission -> {
				mission.setAnswer("");
			});
			List<MissionContext> missionContextList = BeanUtils.deepCopyList(missionList,
					MissionContext.class);
			MissionSchemeObject missionSchemeObject = BeanUtils.copy(missionStageScheme,
					MissionSchemeObject.class);
			missionSchemeObject.setMissions(missionContextList);
			missionSchemeObjectList.add(missionSchemeObject);
		}
		return missionSchemeObjectList;
	}

	@Override
	public List<String> getRoomIdsByUserId(String userId) {
		return crRoleMapper.getRoomIdsByUserId(userId);

	}

	@Override
	public List<CrRole> selectCrRoleByRoomId(RoleQuery roleQuery) {
		return crRoleMapper.selectCrRoleByRoomId(roleQuery);
	}

	@Override
	public CrRole getCrRoleByRoomIdAndUserId(String roomId, String userId) {

		CrRole crRole = crRoleMapper.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (crRole == null) {
			throw new ResourceNotFoundException(roomId);
		}
		return crRole;
	}

	@Override
	public List<MissionSchemeObject> getCrRoleMissionByRoomIdAndUserId(String roomId,
			String userId) {

		CrRole crRole = getCrRoleByRoomIdAndUserId(roomId, userId);

		List<MissionSchemeObject> missionSchemeObjectList = getBaseMissionByRole(crRole);

		missionSchemeObjectList.forEach(missionSchemeObject -> {

			List<MissionContext> missionContextList = new ArrayList<>();
			missionSchemeObject.getMissions().forEach(mission -> {
				MissionContext newMission = new MissionContext();
				newMission.setResourceId(mission.getResourceId());
				newMission.setTitle(mission.getTitle());
				newMission.setCategoryId(mission.getCategoryId());
				newMission.setDescription(mission.getDescription());
				newMission.setState(0);
				missionContextList.add(newMission);

			});
			missionSchemeObject.setMissions(missionContextList);

		});
		return missionSchemeObjectList;
	}

	@Override
	public Integer getMissionCount(CrRole crRole) {
		List<MissionStageScheme> missionStageSchemeList = JsonUtil
				.toJsonObjectArray(crRole.getMissionScheme(), MissionStageScheme.class);
		Integer count = 0;
		for (MissionStageScheme missionStageScheme : missionStageSchemeList) {
			count += missionStageScheme.getMissions().size();
		}
		return count;
	}

	private List<NameElement> getUserByRoleUsers(String userIds) {

		List<NameElement> userElements = Lists.newArrayList();
		List<String> userList = JsonUtil.toJsonObjectArray(userIds, String.class);

		UserPageContext userPageContext = new UserPageContext();
		userPageContext.setIds(String.join(",", userList));
		List<UserInfo> userInfoList = remoteUserService.getLazyUserList(userPageContext);
		userInfoList.forEach(userInfo -> {
			if (userList.contains(userInfo.getUserId())) {
				NameElement nameElement = new NameElement();
				nameElement.setName(userInfo.getUserName());
				userElements.add(nameElement);

			}
		});

		return userElements;
	}
}
