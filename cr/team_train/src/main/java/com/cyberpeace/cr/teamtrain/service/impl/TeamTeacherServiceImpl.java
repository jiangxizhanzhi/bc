package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.dao.entity.CrBadge;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineData;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.cr.crbase.service.context.statistics.TaskStatisticsInfo;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTeacherService;
import com.cyberpeace.cs.service.context.IdNameType;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 团队教员Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see TeamTeacherService
 * @since 1.0.0
 */
@Service
@Slf4j
public class TeamTeacherServiceImpl implements TeamTeacherService {

	/**
	 * 团队房间Service
	 *
	 * @see TeamRoomService
	 */
	@Autowired
	private TeamRoomService teamRoomService;

	/**
	 * 靶场得分Service
	 *
	 * @see CrScoreService
	 */
	@Autowired
	private CrScoreService crScoreService;

	/**
	 * 靶场任务Service
	 *
	 * @see CrTaskService
	 */
	@Autowired
	private CrTaskService crTaskService;

	/**
	 * 靶场徽章Service
	 *
	 * @see CrBadgeService
	 */
	@Autowired
	private CrBadgeService crBadgeService;

	/**
	 * 任务Service
	 *
	 * @see MissionService
	 */
	@Autowired
	private MissionService missionService;

	/**
	 * 靶场角色Service
	 *
	 * @see CrRoleService
	 */
	@Autowired
	private CrRoleService crRoleService;

	/**
	 * 获取得分列表
	 *
	 * @param roomId 房间id
	 * @return {@code List<StudentScoreListRsp> }
	 * @throws
	 * @see String
	 * @see List<StudentScoreListRsp>
	 */
	@Override
	public List<StudentScoreListRsp> getScoreList(String roomId) {
		// get roles
		List<CrRole> roleList = teamRoomService.getStudentsRoles(roomId);

		return crScoreService.getScoreListRsp(roomId, roleList);
	}

	/**
	 * 获取角色列表
	 *
	 * @param roomId 房间id
	 * @return {@code List<IdNameType> }
	 * @throws
	 * @see String
	 * @see List<IdNameType>
	 */
	@Override
	public List<IdNameType> getRoleList(String roomId) {
		List<CrRole> roles = teamRoomService.getStudentsRoles(roomId);
		List<IdNameType> res = Lists.newArrayList();
		for (CrRole role : roles) {
			IdNameType teamRole = new IdNameType();
			teamRole.setId(role.getResourceId());
			teamRole.setName(role.getName());
			teamRole.setType(role.getRoleGroup());

			res.add(teamRole);
		}
		return res;
	}

	/**
	 * 获取复盘时间轴
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code TimeLineData }
	 * @throws
	 * @see String
	 * @see String
	 * @see TimeLineData
	 */
	@Override
	public TimeLineData getReplayTimeline(String roomId, String userId) {
		return teamRoomService.getTimeLine(roomId, userId);
	}

	/**
	 * 获取任务统计学信息
	 *
	 * @param roomId 房间id
	 * @return {@code TaskStatisticsInfo }
	 * @throws
	 * @see String
	 * @see TaskStatisticsInfo
	 */
	@Override
	public TaskStatisticsInfo getTaskStatisticsInfo(String roomId) {
		return crTaskService.getTaskStatisticsInfoByRoomId(roomId);
	}

	/**
	 * 获取复盘场景id
	 *
	 * @param roomId 房间id
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 */
	@Override
	public String getReplaySceneId(String roomId) {
		TeamRoom room = teamRoomService.getById(roomId);
		if (!room.isEnded()) {
			log.info("training not ended: {}", roomId);
			throw new BusinessException(CrBusinessErrorCode.PLZ_REPLAY_AFTER_TRAINING_ENDS);
		}
		if (StringUtils.isNotBlank(room.getLocalSceneId())) {
			log.info("scene not stopped: {}", roomId);
			throw new BusinessException(CrBusinessErrorCode.PLZ_REPLAY_AFTER_SCENE_IS_STOPPED);
		}
		String res = room.getReplaySceneId();
		if (StringUtils.isBlank(res)) {
			log.info("This training [{}] hasn't start a scene, no scene to replay.", roomId);
			throw new BusinessException(CrBusinessErrorCode.REPLAY_SCENE_NOT_EXIST);
		}
		return res;
	}

	/**
	 * 通知提交
	 *
	 * @param missionId 任务id
	 * @throws
	 * @see String
	 */
	@Override
	public void notifySubmission(String missionId) {
		// get mission
		Mission mission = missionService.getById(missionId);
		// get roomId
		String roomId = mission.getRoomId();
		// get teacher
		List<String> teacherIds = this.getTeacherIdsByRoomId(roomId);
		crBadgeService.createBatch(roomId, CrBadge.BadgeType.NEW_SUBMIT.getCode(), teacherIds);
		crTaskService.pushMissionWsToUsers(roomId, teacherIds, MissionWsCode.MISSION_SUBMISSION);
	}

	/**
	 * 获取教员id列表由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	private List<String> getTeacherIdsByRoomId(String roomId) {
		List<CrRole> roles = crRoleService.getRolesByRoomAndGroups(roomId,
				Lists.newArrayList(CrRole.TeamRoleGroup.TEACHER.getCode()));
		if (roles.isEmpty()) {
			return Lists.newArrayList();
		}
		return roles.get(0).getUserIdList();
	}
}
