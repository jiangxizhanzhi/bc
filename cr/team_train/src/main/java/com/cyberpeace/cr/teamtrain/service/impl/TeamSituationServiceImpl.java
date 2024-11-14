package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.context.role.CrRoleMissionSchemeStage;
import com.cyberpeace.common.context.role.CrRoleSimple;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.model.EntranceBo;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.SituationRouteStatus;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.model.situation.SituationRouteBo;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
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

/**
 * 团队态势Service实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Service
@Slf4j
public class TeamSituationServiceImpl implements TeamSituationService {

	/**
	 * 任务Service
	 */
	@Autowired
	private MissionService missionService;

	/**
	 * 团队教员Service
	 */
	@Autowired
	private TeamTeacherService teamTeacherService;

	/**
	 * 团队房间Service
	 */
	@Autowired
	private TeamRoomService teamRoomService;

	/**
	 * 靶场任务Service
	 */
	@Autowired
	private CrTaskService crTaskService;

	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 任务态势Service
	 */
	@Autowired
	private MissionSituationService missionSituationService;

	/**
	 * 如果显示态势
	 *
	 * @param roomId 房间id
	 * @return {@code EntranceBo }
	 */
	@Override
	public EntranceBo ifShowSituation(String roomId) {
		TeamRoom room = teamRoomService.getById(roomId);
		EntranceBo res = new EntranceBo();
		res.setShow(StringUtils.isNotBlank(room.getAreaPath()));
		res.setUrl("/ts/industry/AviationOil?businessId=" + roomId);
		res.setWsUrl(String.format("%s?business_id=%s&token=", WsPath.INDUSTRY_SITUATION, roomId));
		return res;
	}

	/**
	 * 获取态势信息
	 *
	 * @param roomId 房间id
	 * @return {@code TeamSituationInfo }
	 */
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
		// submitted mission templates
		Set<String> submittedMissionIds = taskList.stream().filter(CrTask::isSubmitted)
				.map(CrTask::getMissionId).collect(Collectors.toSet());
		// successful mission templates
		Set<String> successfulMissionIds = taskList.stream().filter(CrTask::isSuccess)
				.map(CrTask::getMissionId).collect(Collectors.toSet());
		// stage:mt
		Map<String, List<String>> stageMissionMap = this.getStageMissionMap(roles);

		List<SituationRouteBo> routeBoList = Lists.newArrayList();
		for (String nodeName : route) {
			SituationRouteBo bo = new SituationRouteBo();
			bo.setNode(nodeName);
			bo.setStatus(this.getRouteNodeStatus(nodeName, stageMissionMap, submittedMissionIds,
					successfulMissionIds));
			routeBoList.add(bo);
		}
		res.setRoute(routeBoList);
		return res;
	}


	/**
	 * 获取top5得分
	 *
	 * @param roomId 房间id
	 * @return {@code List<StudentScoreVo> }
	 */
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
	 * 获取路线节点状态
	 *
	 * @param nodeName             节点名称
	 * @param stageMap             阶段映射
	 * @param submittedMissionIds  提交任务id列表
	 * @param successfulMissionIds 成功任务id列表
	 * @return {@code Integer }
	 */
	private Integer getRouteNodeStatus(String nodeName, Map<String, List<String>> stageMap,
			Set<String> submittedMissionIds, Set<String> successfulMissionIds) {
		List<String> missionIds = stageMap.get(nodeName);
		if (null == missionIds || missionIds.isEmpty()) {
			return SituationRouteStatus.NOT_ATTACKED;
		}
		if (successfulMissionIds.containsAll(missionIds)) {
			return SituationRouteStatus.CONQUERED;
		}
		for (String missionId : missionIds) {
			if (submittedMissionIds.contains(missionId)) {
				return SituationRouteStatus.UNDER_ATTACK;
			}
		}
		return SituationRouteStatus.NOT_ATTACKED;
	}

	/**
	 * 获取stage和mission templateId 的对应关系
	 *
	 * @param roles
	 * @return {@code Map<String, List<String>>}
	 */
	private Map<String, List<String>> getStageMissionMap(List<CrRoleSimple> roles) {
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
		return stageMissionMap;
	}
}
