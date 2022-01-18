package com.cyberpeace.cr.singletrain.controller.web;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.cyberpeace.common.constant.HtmlTemplateEnum;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.HtmlToPdfUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

import javax.servlet.http.HttpServletResponse;

@Api(tags = "单兵训练评分")
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
		RoleQuery roleQuery = new RoleQuery(resource_id, CrRole.SingleRoleGroupCode.roleGroup);
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
		data.put("total_score", studentScoreRsp.getTrainingScore().getSumScoreObjective());

		data.put("create_time", new Date());
		data.put("resource_id", resource_id);
		data.put("score", studentScoreRsp.getTrainingScore().getGetScoreObjective());

		return new BaseRsp(data);
	}

	@ApiOperation("训练记录导出为PDF")
	@GetMapping("/train2pdf")
	public void dumpTrainRecord2PDF(HttpServletResponse httpServletResponse, @RequestParam("room_id") String roomId) {
		JSONObject data = getStudentScore(roomId).getData();
		data.put("username", UserInfoContext.getUserContext().getUserName());

		String destHtmlPath = String.format("%s%s%s.html", AttachmentPath.OTHER.getDesc(), roomId, UserInfoContext.getUserContext().getUserId());
		HtmlToPdfUtil.getHtmlTemplateInstance(data, HtmlTemplateEnum.INDIVIDUAL, destHtmlPath);
		String destPdfPath = destHtmlPath.replace("html", "pdf");
		HtmlToPdfUtil.convert(destHtmlPath, destPdfPath);

		if (new File(destHtmlPath).delete()) {
			FileUtil.setResponse(new File(destPdfPath), true, httpServletResponse);
		}
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
