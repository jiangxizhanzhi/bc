package com.cyberpeace.cr.singletrain.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.HtmlTemplateEnum;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.HtmlToPdfUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.controller.CrScoreController;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.crbase.controller.rsp.TrainingRecordMissionRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.RadarLabel;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.TrainRecordService;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReport;
import com.cyberpeace.cr.crbase.service.context.mission.StageExport;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.singletrain.service.SingleRoomService;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.integratation.service.impl.MpEnvService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

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

	@Autowired
	TrainRecordService trainRecordService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private MpEnvService mpEnvService;

	@Autowired
	private RemoteUserService remoteUserService;

	@ApiOperation("查询评分")
	@GetMapping("/history_detail")
	public BaseRsp<JSONObject> getHistoryDetail(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		RoleQuery roleQuery = new RoleQuery(resource_id, CrRole.SingleRoleGroupCode.roleGroup);
		List<CrRole> crRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);

		MissionReport missionReport = crScoreController.getTrainingMissionReport(resource_id,
				crRoleList);

		// 单兵训练仅有一个用户, 角色仅关联一个用户
		String user_hash = JsonUtil.stringToArray(crRoleList.get(0).getUserIds()).toJavaList(String.class).get(0);

		List<Integer> radars = crRoleService.getRadar(resource_id, user_hash);

		List<MissionSchemeObject<TrainingRecordMissionRsp>> missionSchemeList = crScoreController
				.getTrainingRecord(resource_id, user_hash);

		StudentScoreRsp studentScoreRsp = crScoreController.getStudentScore(resource_id, user_hash);
		List<StageExport> StageExports = getStageExports(missionSchemeList);

		JSONObject data = new JSONObject();
		data.put("task_count", gettaskCount(missionReport));
		data.put("radar", radars);
		data.put("stages", StageExports);
		data.put("total_score", studentScoreRsp.getTrainingScore().getSumScoreObjective());

		data.put("create_time", trainRecordService.getByRoomId(resource_id).getEndTime());
		data.put("resource_id", resource_id);
		data.put("score", studentScoreRsp.getTrainingScore().getGetScoreObjective());

		// 用户信息
		UserInfo userInfo = remoteUserService.getById(user_hash);
		data.put("user_id", userInfo.getUserId());
		data.put("title", crScoreController.getTitle(userInfo.getUserName()));
		// 获取用户LOGO todo 自定义logo获取方式待定
		try {
			data.put("user_logo",
					mpEnvService.getMpHost() + userInfo.getAvatarUrl());
		} catch (Exception e) {
			throw new RemoteServiceException();
		}


		return new BaseRsp(data);
	}

	@ApiOperation("训练记录导出为PDF")
	@GetMapping("/train2pdf")
	public void dumpTrainRecord2PDF(HttpServletResponse httpServletResponse,
			@RequestParam("room_id") String roomId) {
		JSONObject data = getHistoryDetail(roomId).getData();
		List<RadarLabel> radarLabels = missionService.getRadarLabel();
		data.put("radarLabels", radarLabels);
		data.put("locale", crScoreController.getTemplateI18n());

		String destHtmlPath = String.format("%s%s-%s.html",
				AttachmentPath.SINGLE_HTML_TEMPLATE.getDesc(), roomId,
				data.getString("user_id"));
		HtmlToPdfUtil.getHtmlTemplateInstance(data, HtmlTemplateEnum.INDIVIDUAL, destHtmlPath);
		String destPdfPath = destHtmlPath.substring(0, destHtmlPath.lastIndexOf("html")) + "pdf";
		// todo 文件命名
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

	private List<StageExport>
			getStageExports(List<MissionSchemeObject<TrainingRecordMissionRsp>> missionSchemeList) {

		List<StageExport> StageExports = Lists.newArrayList();

		for (MissionSchemeObject<TrainingRecordMissionRsp> missionSchemeObject : missionSchemeList) {
			StageExport StageExport = new StageExport(missionSchemeObject);

			StageExports.add(StageExport);
		}

		return StageExports;
	}

}
