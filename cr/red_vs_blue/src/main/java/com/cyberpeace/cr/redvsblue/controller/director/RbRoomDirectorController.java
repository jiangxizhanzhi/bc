package com.cyberpeace.cr.redvsblue.controller.director;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.controller.req.WriteUpReq;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.redvsblue.controller.req.ExercisePaperReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.ExercisePaperRsp;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "红蓝对抗导演")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/director/red_vs_blue/red_vs_blue_templates/")
public class RbRoomDirectorController {

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private MissionService missionService;

	@ApiOperation("提交演习文书")
	@PostMapping("/add_exercise_paper")
	public BaseRsp<String>
			addExercisePaper(@RequestBody @Validated ExercisePaperReq exercisePaperReq) {
		RoleQuery roleQuery = new RoleQuery(exercisePaperReq.getResourceId(),
				exercisePaperReq.getGroupHash());

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);
		for (CrRole crRole : crRoles) {
			crRole.setExercisePaper(exercisePaperReq.getExercisePaper());
		}
		crRoleService.updateBatchById(crRoles);

		return new BaseRsp<>();
	}

	@ApiOperation("获取演习文书")
	@GetMapping("/get_mission_detail")
	public BaseRsp<ExercisePaperRsp>
			get_exercise_paper(@RequestBody @Validated ExercisePaperReq exercisePaperReq) {
		RoleQuery roleQuery = new RoleQuery(exercisePaperReq.getResourceId(),
				exercisePaperReq.getGroupHash());

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);
		ExercisePaperRsp exercisePaperRsp = new ExercisePaperRsp();
		if (crRoles.size() > 0) {
			exercisePaperRsp.setExercisePaper("");
		}
		return new BaseRsp(exercisePaperRsp);
	}

	/**
	 * 修改开放题解
	 * @return
	 */
	@ApiOperation("修改开放题解")
	@PostMapping("/update_show_write_up")
	public BaseRsp<String> updateShowWriteUp(@RequestBody @Validated WriteUpReq writeUpReq) {

		rbRoomService.checkDirectorPermission(writeUpReq.getTrainingTeam(),
				UserInfoContext.getUserContext().getUserId());
		rbRoomService.checkRoomOpening(writeUpReq.getTrainingTeam());

		Mission mission = missionService.getById(writeUpReq.getMissionResourceId());
		missionService.updateShowWriteup(writeUpReq.getMissionResourceId(),
				writeUpReq.getWriteUpShow());

		return new BaseRsp<>();
	}
}
