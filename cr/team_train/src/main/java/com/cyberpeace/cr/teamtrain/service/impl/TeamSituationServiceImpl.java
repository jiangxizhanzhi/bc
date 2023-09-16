package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.context.role.CrRoleSimple;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.model.EntranceBo;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.MissionSituation;
import com.cyberpeace.cr.crbase.model.situation.SituationRouteBo;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionSituationService;
import com.cyberpeace.cr.crbase.service.context.statistics.TaskStatisticsInfo;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamSituationService;
import com.cyberpeace.cr.teamtrain.service.TeamTeacherService;
import com.cyberpeace.cr.teamtrain.service.bo.situation.SituationTaskProgress;
import com.cyberpeace.cr.teamtrain.service.bo.situation.StudentScoreVo;
import com.cyberpeace.cr.teamtrain.service.bo.situation.TeamSituationInfo;
import com.cyberpeace.cr.teamtrain.service.bo.situation.TeamSituationTaskStatistics;
import com.cyberpeace.integratation.service.RemoteUserService;
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

	@Autowired
	private MissionSituationService missionSituationService;

	@Override
	public EntranceBo ifShowSituation(String roomId) {
		TeamRoom room = teamRoomService.getById(roomId);
		EntranceBo res = new EntranceBo();
		res.setShow(StringUtils.isNotBlank(room.getAreaPath()));
		res.setUrl("/ts/industry/AviationOil/?businessId=" + roomId);
		res.setWsUrl(String.format("/%s/?businessId=%s&token=", WsPath.INDUSTRY_SITUATION, roomId));
		return res;
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
		TeamRoom room = teamRoomService.getById(roomId);
		List<String> route = JsonUtil.toJsonObjectArray(room.getAreaPath(), String.class);
		// submitted missions
		List<String> submittedMissionIds = taskList.stream().filter(task -> task.isSubmitted())
				.map(CrTask::getMissionTemplateId).distinct().collect(Collectors.toList());
		List<MissionSituation> situationList = missionSituationService.getBaseMapper()
				.selectBatchIds(submittedMissionIds);
		Set<String> lightedAreas = situationList.stream().map(MissionSituation::getArea)
				.collect(Collectors.toSet());

		List<SituationRouteBo> routeBoList = Lists.newArrayList();
		for (String areaName : route) {
			SituationRouteBo bo = new SituationRouteBo();
			bo.setNode(areaName);
			bo.setAttacked(lightedAreas.contains(areaName));
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
