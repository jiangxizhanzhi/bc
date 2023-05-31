package com.cyberpeace.cr.crbase.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.CrMark;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.mark.EvidenceBo;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineEvent;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CrReplayServiceImpl implements CrReplayService {

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private CrCommonService crCommonService;

	@Override
	public List<TimeLineEvent> getReplayEvents(String roomId, String userId) {
		// user info
		UserInfo userInfo = remoteUserService.getById(userId);
		List<TimeLineEvent> eventList = Lists.newArrayList();
		// 只展示学员自己做的任务
		List<CrTask> taskList = crTaskService.getByRoomIdAndUserId(roomId, userId);
		List<String> missionIds = taskList.stream().map(CrTask::getMissionId)
				.collect(Collectors.toList());
		List<Mission> missions = missionService.batchGet(missionIds);
		Map<String, Mission> missionMap = StreamApiUtils.toMap(missions, Mission::getResourceId,
				mission -> mission);
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
				if (null != userInfo) {
					timeLineEvent.setUserName(userInfo.getName());
					timeLineEvent.setAvatarUrl(userInfo.getAvatarUrl());
				}
				timeLineEvent.setStatus(task.getStatus());
				timeLineEvent.setIsDynamic(dynamicMissionIds.contains(task.getMissionId()));
				timeLineEvent.setType(TimeLineEvent.EventType.MISSION.getCode());

				eventList.add(timeLineEvent);
			}
		}
		//
		List<CrMark> crMarkList = crCommonService.getMarkByRoomIdAndUserId(roomId, userId);

		// 增加标记事件
		for (CrMark mark : crMarkList) {
			TimeLineEvent timeLineEvent = new TimeLineEvent();
			timeLineEvent.setTitle(mark.getName());
			if (null != userInfo) {
				timeLineEvent.setUserName(userInfo.getName());
				timeLineEvent.setAvatarUrl(userInfo.getAvatarUrl());
			}
			timeLineEvent.setTime(mark.getCreateTime());
			timeLineEvent.setType(TimeLineEvent.EventType.MARK.getCode());
			timeLineEvent.setAttachments(
					JsonUtil.toJsonObjectArray(mark.getEvidence(), EvidenceBo.class));
			eventList.add(timeLineEvent);
		}

		return eventList;
	}
}
