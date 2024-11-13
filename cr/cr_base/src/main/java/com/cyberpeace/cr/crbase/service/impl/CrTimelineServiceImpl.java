package com.cyberpeace.cr.crbase.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrMark;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.mark.EvidenceBo;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineEvent;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 靶场时间轴Service实现
 *
 * @author SN
 * @date 2024/11/13
 */
@Service
@Slf4j
public class CrTimelineServiceImpl implements CrTimelineService {

	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 靶场任务Service
	 */
	@Autowired
	private CrTaskService crTaskService;

	/**
	 * 任务Service
	 */
	@Autowired
	private MissionService missionService;

	/**
	 * 靶场岗位Service
	 */
	@Autowired
	private CrRoleService crRoleService;

	/**
	 * 靶场常用Service
	 */
	@Autowired
	private CrCommonService crCommonService;

	/**
	 * 获取复盘事件
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code List<TimeLineEvent> }
	 */
	@Override
	public List<TimeLineEvent> getReplayEvents(String roomId, String userId) {
		// user info
		UserInfo userInfo = remoteUserService.getById(userId);
		List<TimeLineEvent> eventList = Lists.newArrayList();
		if (null == userInfo) {
			// 用户不存在
			return eventList;
		}
		// 只展示学员自己做的任务
		List<CrTask> taskList = crTaskService.getByRoomIdAndUserId(roomId, userId);
		List<String> missionIds = taskList.stream().map(CrTask::getMissionId)
				.collect(Collectors.toList());
		Map<String, Mission> missionMap = missionService.getMissionMapByIds(missionIds);
		// get dynamic missions
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		List<String> dynamicMissionIds = CrCommonUtil
				.getDynamicMissionIdsByRoleMissionScheme(role.getMissionScheme());
		for (CrTask task : taskList) {
			TimeLineEvent timeLineEvent = new TimeLineEvent();

			Date lastSubmitTime = task.getLastSubmitTime();
			// 仅返回操作过的任务
			if (null != lastSubmitTime) {
				timeLineEvent.setTime(lastSubmitTime);
				Mission mission = missionMap.get(task.getMissionId());
				if (null != mission) {
					timeLineEvent.setTitle(mission.getTitle());
				}
				timeLineEvent.setUserName(userInfo.getName());
				timeLineEvent.setAvatarUrl(userInfo.getAvatarUrl());
				timeLineEvent.setStatus(task.getStatus());
				timeLineEvent.setIsDynamic(dynamicMissionIds.contains(task.getMissionId()));
				timeLineEvent.setEventType(TimeLineEvent.EventType.MISSION.getCode());
				timeLineEvent.setRoleGroup(role.getRoleGroup());

				eventList.add(timeLineEvent);
			}
		}
		//
		List<CrMark> crMarkList = crCommonService.getMarkByRoomIdAndUserId(roomId, userId);

		// 增加标记事件
		for (CrMark mark : crMarkList) {
			TimeLineEvent timeLineEvent = new TimeLineEvent();
			timeLineEvent.setTitle(mark.getName());
			timeLineEvent.setUserName(userInfo.getName());
			timeLineEvent.setAvatarUrl(userInfo.getAvatarUrl());
			timeLineEvent.setTime(mark.getCreateTime());
			timeLineEvent.setEventType(TimeLineEvent.EventType.MARK.getCode());
			timeLineEvent.setAttachments(
					JsonUtil.toJsonObjectArray(mark.getEvidence(), EvidenceBo.class));
			timeLineEvent.setRoleGroup(role.getRoleGroup());
			eventList.add(timeLineEvent);

		}

		return eventList;
	}

	/**
	 * 获取时间轴事件由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code List<TimeLineEvent> }
	 */
	@Override
	public List<TimeLineEvent> getTimelineEventsByRoomId(String roomId) {
		List<TimeLineEvent> res = Lists.newArrayList();
		// get tasks
		List<CrTask> taskList = crTaskService.getByRoomId(roomId);
		// get marks
		List<CrMark> markList = crCommonService.getMarkByRoomId(roomId);
		// get user info
		List<String> userIds = taskList.stream().map(CrTask::getUserId)
				.collect(Collectors.toList());
		userIds.addAll(markList.stream().map(CrMark::getUserId).collect(Collectors.toList()));
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);
		if (userInfoMap.isEmpty()) {
			return res;
		}
		// get role groups
		List<CrRole> roleList = crRoleService.getRolesByRoomId(roomId);
		List<String> dynamicMissionIds = Lists.newArrayList();
		// collect user role group
		Map<String, Integer> userRoleGroupMap = Maps.newHashMap();
		for (CrRole role : roleList) {
			if (role.isRvbStudent() || role.isTeamStudent()) {
				// collect dynamic missions
				dynamicMissionIds.addAll(CrCommonUtil
						.getDynamicMissionIdsByRoleMissionScheme(role.getMissionScheme()));
			}
			for (String userId : JsonUtil.toJsonObjectArray(role.getUserIds(), String.class)) {
				userRoleGroupMap.put(userId, role.getRoleGroup());
			}
			userRoleGroupMap.put(role.getResourceId(), role.getRoleGroup());
		}
		// missions
		List<String> missionIds = taskList.stream().map(CrTask::getMissionId)
				.collect(Collectors.toList());
		Map<String, Mission> missionMap = missionService.getMissionMapByIds(missionIds);

		// task events
		for (CrTask crTask : taskList) {
			// 仅返回操作过的任务
			if (null == crTask.getLastSubmitTime()) {
				continue;
			}
			UserInfo userInfo = userInfoMap.get(crTask.getUserId());
			if (null == userInfo) {
				continue;
			}
			TimeLineEvent timeLineEvent = new TimeLineEvent();
			timeLineEvent.setRoleGroup(userRoleGroupMap.get(crTask.getUserId()));
			Mission mission = missionMap.get(crTask.getMissionId());
			if (null != mission) {
				timeLineEvent.setTitle(mission.getTitle());
			}
			timeLineEvent.setTime(crTask.getLastSubmitTime());
			timeLineEvent.setUserName(userInfo.getName());
			timeLineEvent.setAvatarUrl(userInfo.getAvatarUrl());
			timeLineEvent.setIsDynamic(dynamicMissionIds.contains(crTask.getMissionId()));
			timeLineEvent.setEventType(TimeLineEvent.EventType.MISSION.getCode());

			timeLineEvent.setStatus(crTask.getStatus());
			res.add(timeLineEvent);
		}

		// mark
		for (CrMark mark : markList) {
			TimeLineEvent timeLineEvent = new TimeLineEvent();
			UserInfo userInfo = userInfoMap.get(mark.getUserId());
			if (null == userInfo) {
				continue;
			}
			timeLineEvent.setRoleGroup(userRoleGroupMap.get(mark.getUserId()));
			timeLineEvent.setTitle(mark.getName());
			timeLineEvent.setUserName(userInfo.getName());
			timeLineEvent.setAvatarUrl(userInfo.getAvatarUrl());
			timeLineEvent.setTime(mark.getCreateTime());
			timeLineEvent.setEventType(TimeLineEvent.EventType.MARK.getCode());
			timeLineEvent.setAttachments(
					JsonUtil.toJsonObjectArray(mark.getEvidence(), EvidenceBo.class));

			res.add(timeLineEvent);
		}

		return res;
	}
}
