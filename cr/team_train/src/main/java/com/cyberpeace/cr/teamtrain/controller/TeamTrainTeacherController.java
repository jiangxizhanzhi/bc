package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;

import com.cyberpeace.common.constant.MenuConstant;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.service.CrCommonService;
import com.cyberpeace.cr.crbase.service.context.scene.MonitorSession;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "团队训练实例")
@RestController
@RequestMapping("/cr/training_team/training_team_teacher")
public class TeamTrainTeacherController {

	@Autowired
	CrCommonService crCommonService;

	@Autowired
	private TeamRoomService teamRoomService;

	@ApiOperation("获取桌面监控")
	@GetMapping("/sessions")
	@RequiresPermissions(value = {MenuConstant.WEB_CR_RED_TRAIN, MenuConstant.WEB_CR_BLUE_TRAIN, MenuConstant.WEB_CR_DAILY_TRAIN}, logical = Logical.OR)
	public BaseRsp<List<MonitorSession>> getSessions(
			@ApiParam(name = "room_id", value = "训练实例id", required = true) String room_id,
			@ApiParam(name = "student_id", value = "学员id", required = false) String student_id) {
		TeamRoom room = teamRoomService.getById(room_id);
		// 1. 确认场景已开启
		if (room == null || StringUtils.isBlank(room.getLocalSceneId())) {
			return new BaseRsp<>(Lists.newArrayList());
		}
		return new BaseRsp<>(
				crCommonService.getMonitorSessions(room.getLocalSceneId(), student_id));
	}
}
