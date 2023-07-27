package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.mission.RedBlueTaskReleaseProgress;
import com.cyberpeace.cr.crbase.service.context.mission.RoleTaskReleaseProgress;
import com.cyberpeace.cr.crbase.service.context.mission.StageTaskReleaseProgress;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleMissionSchemeStage;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleSimple;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.RbDirectorService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RbDirectorServiceImpl implements RbDirectorService {

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private CrTaskService crTaskService;

	@Override
	public RedBlueTaskReleaseProgress getTaskReleaseProgress(String roomId) {
		if (StringUtils.isBlank(roomId)) {
			log.error("room_id is null");
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		// get roles
		List<CrRole> studentRoles = rbRoomService.getStudentsRoles(roomId);
		// parse mission and users
		List<CrRoleSimple> roles = studentRoles.stream().map(CrRoleSimple::new)
				.collect(Collectors.toList());
		// get tasks
		List<CrTask> crTaskList = crTaskService.getByRoomId(roomId);

		RedBlueTaskReleaseProgress progress = new RedBlueTaskReleaseProgress();
		// count
		Integer released = crTaskList.size();
		Integer total = roles.stream().mapToInt(CrRoleSimple::getTaskCount).sum();
		Integer submitted = crTaskList.stream().mapToInt(task -> task.isSubmitted() ? 1 : 0).sum();
		long waiting = crTaskList.stream()
				.filter(task -> task.getStatus().equals(CrTask.TaskStatus.WAIT.getCode())).count();

		progress.setTotal(total);
		progress.setReleased(released);
		progress.setUnreleased(total - released);
		progress.setSubmitted(submitted);
		progress.setUnsubmitted(released - submitted);
		progress.setWaiting((int) waiting);

		// 循环各个角色和阶段
		for (CrRoleSimple role : roles) {
			RoleTaskReleaseProgress roleProgress = new RoleTaskReleaseProgress();
			roleProgress.setRoleId(role.getResourceId());
			roleProgress.setRoleName(role.getName());
			for (CrRoleMissionSchemeStage stage : role.getMissionScheme()) {
				StageTaskReleaseProgress stageProgress = new StageTaskReleaseProgress();
				stageProgress.setStageName(stage.getStage());
				// 计算该阶段内任务下发数和任务总数*人数
				Integer releasedTaskCount = crTaskList.stream()
						.mapToInt(task -> stage.getMissions().contains(task.getMissionId()) ? 1 : 0)
						.sum();
				Integer taskCount = stage.getMissions().size() * role.getUserIds().size();
				stageProgress.setTotal(taskCount);
				stageProgress.setReleased(releasedTaskCount);
				// 阶段加入角色之中
				roleProgress.getStage().add(stageProgress);
				roleProgress.addTotal(taskCount);
				roleProgress.addReleased(releasedTaskCount);
			}
			// red role
			if (role.getRoleGroup().equals(CrRole.RbRoleGroup.RED.getCode())) {
				progress.getRedRoles().getRole().add(roleProgress);
				progress.getRedRoles().addTotal(roleProgress.getTotal());
				progress.getRedRoles().addReleased(roleProgress.getReleased());
			} else {
				// blue role
				progress.getBlueRoles().getRole().add(roleProgress);
				progress.getBlueRoles().addTotal(roleProgress.getTotal());
				progress.getBlueRoles().addReleased(roleProgress.getReleased());
			}
		}
		return progress;
	}

	@Override
	public String getReplaySceneId(String roomId) {
		RbRoom room = rbRoomService.getOrExceptionById(roomId);
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
}
