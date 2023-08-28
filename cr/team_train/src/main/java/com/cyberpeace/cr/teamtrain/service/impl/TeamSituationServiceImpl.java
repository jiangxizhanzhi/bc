package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.service.RemoteUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.context.role.CrRoleMissionSchemeStage;
import com.cyberpeace.common.context.role.CrRoleSimple;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.statistics.TaskStatisticsInfo;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamSituationService;
import com.cyberpeace.cr.teamtrain.service.TeamTeacherService;
import com.cyberpeace.cr.teamtrain.service.bo.situation.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeamSituationServiceImpl implements TeamSituationService {

	/**
	 * 只支持该名称的航油团队模板
	 */
	private static String HANGYOU_TEMPLATE_NAME = "输油储油工艺的渗透实战";

	/**
	 * 固定的攻击路径
	 */
	private static List<String> DEFAULT_ROUTE = Lists.newArrayList("航油系统网站侦察", "DMZ区数据库渗透",
			"内网OA服务器渗透", "企业办公网渗透", "核心信息服务区渗透", "安全管控区渗透", "工业控制网络渗透");

	@Autowired
	private TeamTeacherService teamTeacherService;

	@Autowired
	private TeamRoomService teamRoomService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public Boolean ifShowSituation(String roomId) {
		TeamRoom room = teamRoomService.getById(roomId);
		return room.getTeamTemplateName().equals(HANGYOU_TEMPLATE_NAME);
	}

	@Override
	public TeamSituationInfo getSituationInfo(String roomId) {
		TeamSituationInfo res = new TeamSituationInfo();
		TaskStatisticsInfo statisticsInfo = teamTeacherService.getTaskStatisticsInfo(roomId);
		// 获取该房间的角色信息
		List<CrRole> studentRoles = teamRoomService.getStudentsRoles(roomId);
		// parse mission and users
		List<CrRoleSimple> roles = studentRoles.stream().map(CrRoleSimple::new)
				.collect(Collectors.toList());
		// 获取该房间已下发的任务
		List<CrTask> taskList = crTaskService.getByRoomId(roomId);
		// count
		Integer released = taskList.size();
		Integer total = roles.stream().mapToInt(CrRoleSimple::getTaskCount).sum();
		// 任务下发进度
		SituationTaskProgress progress = new SituationTaskProgress();
		progress.setTotal(total);
		progress.setReleased(released);
		progress.setUnreleased(total - released);
		res.setProgress(progress);
		// 任务状态
		TeamSituationTaskStatistics statistics = new TeamSituationTaskStatistics();
		statistics.setSuccess(statisticsInfo.getSuccessMissionsCount());
		statistics.setFail(statisticsInfo.getFailMissionsCount());
		statistics.setWait(statisticsInfo.getWaitMissionsCount());
		statistics.setUnsubmitted(released - statisticsInfo.getSubmitMissionCount());
		res.setStatistics(statistics);

		// 攻击路径
		// stage:mission map
		Map<String, List<String>> stageMissionMap = Maps.newHashMap();
		for (CrRoleSimple role : roles) {
			for (CrRoleMissionSchemeStage stage : role.getMissionScheme()) {
				stageMissionMap.put(stage.getStage(), stage.getMissions());
				if (CollectionUtils.isNotEmpty(stage.getDynamic())) {
					stageMissionMap.get(stage.getStage()).addAll(stage.getDynamic());
				}
			}
		}
		// mission_id:task map
		Map<String, List<CrTask>> missionTaskMap = Maps.newHashMap();
		for (CrTask crTask : taskList) {
			if (!missionTaskMap.containsKey(crTask.getMissionId())) {
				missionTaskMap.put(crTask.getMissionId(), Lists.newArrayList());
			}
			missionTaskMap.get(crTask.getMissionId()).add(crTask);
		}
		List<SituationRouteBo> routeBoList = Lists.newArrayList();
		for (String stageName : DEFAULT_ROUTE) {
			SituationRouteBo bo = new SituationRouteBo();
			bo.setNode(stageName);
			bo.setAttacked(checkIfSubmitted(stageName, stageMissionMap, missionTaskMap));
			routeBoList.add(bo);
		}
		res.setRoute(routeBoList);
		return res;
	}

	@Override
	public List<StudentScoreVo> getTop5Score(String roomId) {
		// 获取该房间已下发的任务
		List<CrTask> taskList = crTaskService.getByRoomId(roomId);
		// sum
		Map<String, StudentScoreVo> scoreMap = Maps.newHashMap();
		for (CrTask crTask : taskList) {
			String userId = crTask.getUserId();
			Integer score = crTask.getScore();
			if (!scoreMap.containsKey(userId)) {
				StudentScoreVo vo = new StudentScoreVo();
				vo.setResourceId(userId);
				scoreMap.put(userId, vo);
			}
			if (null != score) {
				scoreMap.get(userId).addScore(score);
			}
		}
		// sort
		List<StudentScoreVo> res = scoreMap.values().stream()
				.sorted(Comparator.comparing(StudentScoreVo::getScore).reversed())
				.collect(Collectors.toList());
		if(res.size() > 5){
			res = res.subList(0, 5);
		}
		List<String> userIdList = res.stream().map(StudentScoreVo::getResourceId).collect(Collectors.toList());
		// user info
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIdList);
		for (StudentScoreVo vo : res) {
			String userId = vo.getResourceId();
			UserInfo userInfo = userInfoMap.get(userId);
			if(null != userInfo) {
				vo.setName(userInfo.getName());
				vo.setAvatar(userInfo.getAvatarUrl());
			}
		}
		return res;
	}

	/**
	 * 检查阶段内是否有已提交任务
	 *
	 * @param stageName       阶段名称
	 * @param stageMissionMap 阶段-任务
	 * @param missionTaskMap  mission-task
	 * @return boolean
	 */
	private boolean checkIfSubmitted(String stageName, Map<String, List<String>> stageMissionMap,
			Map<String, List<CrTask>> missionTaskMap) {
		for (String missionId : stageMissionMap.getOrDefault(stageName, Lists.newArrayList())) {
			for (CrTask crTask : missionTaskMap.getOrDefault(missionId, Lists.newArrayList())) {
				if (crTask.isSubmitted()) {
					return true;
				}
			}
		}
		return false;
	}
}
