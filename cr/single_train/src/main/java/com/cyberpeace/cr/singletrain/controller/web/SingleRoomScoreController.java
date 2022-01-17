package com.cyberpeace.cr.singletrain.controller.web;

import java.util.Date;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.controller.CrScoreController;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.crbase.controller.rsp.TrainingRecordMissionRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReport;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.singletrain.service.SingleRoomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "团队训练评分")
@RestController
@RequestMapping("/cr/single_train/single_train")
@Slf4j
public class SingleRoomScoreController {

	@Autowired
	SingleRoomService singleRoomService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private CrScoreController crScoreController;

	@ApiOperation("查询评分")
	@GetMapping("/history_detail")
	public BaseRsp<JSONObject> getStudentScore(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		RoleQuery roleQuery = new RoleQuery(resource_id,
				CrRole.TeamRoleGroupCode.STUDENT.getCode());
		List<CrRole> crRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);

		MissionReport missionReport = crScoreController.getTrainingMissionReport(resource_id,
				crRoleList);

		String user_hash = UserInfoContext.getUserContext().getUserId();

		List<Integer> radars = crRoleService.getRadar(resource_id, user_hash);

		List<MissionSchemeObject<TrainingRecordMissionRsp>> missionSchemeList = crScoreController
				.getTrainingRecord(resource_id, user_hash);

		StudentScoreRsp studentScoreRsp = crScoreController.getStudentScore(resource_id, user_hash);
		List<StageRsp> stageRsps = getStageRsps(missionSchemeList);

		JSONObject data = new JSONObject();
		data.put("task_count", gettaskCount(missionReport));
		data.put("radar", radars);
		data.put("stages", stageRsps);
		data.put("total_score", 5);

		data.put("create_time", new Date());
		data.put("resource_id", resource_id);
		data.put("score", 3);

		return new BaseRsp(data);
	}

	private Object gettaskCount(MissionReport missionReport) {
		List<Integer> taskCount = Lists.newArrayList();

		taskCount.add(missionReport.getMissionsStatistics().getFinishProportion());
		taskCount.add(missionReport.getMissionsStatistics().getNotSubmitProportion());
		taskCount.add(0);

		return taskCount;
	}

	private List<StageRsp>
			getStageRsps(List<MissionSchemeObject<TrainingRecordMissionRsp>> missionSchemeList) {

		List<StageRsp> stageRsps = Lists.newArrayList();

		for (MissionSchemeObject<TrainingRecordMissionRsp> missionSchemeObject : missionSchemeList) {
			StageRsp stageRsp = new StageRsp(missionSchemeObject);

			stageRsps.add(stageRsp);
		}

		return stageRsps;
	}

}
