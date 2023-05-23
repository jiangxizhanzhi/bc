package com.cyberpeace.cr.singletrain.controller.web;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.HtmlTemplateEnum;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.HtmlToPdfUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.CrScoreController;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.crbase.controller.rsp.TrainingRecordMissionRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.RadarLabel;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.TrainRecordService;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReport;
import com.cyberpeace.cr.crbase.service.context.mission.StageExport;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.singletrain.service.SingleRoomService;
import com.cyberpeace.cr.singletrain.service.bo.record.RecordDetail;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.integratation.service.impl.MpEnvService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "单兵训练评分")
@RestController
@RequestMapping("/cr/single_train/single_train")
@Slf4j
public class SingleRoomScoreController {

	@Autowired
	SingleRoomService singleRoomService;
	@Autowired
	TrainRecordService trainRecordService;
	@Autowired
	private CrRoleService crRoleService;
	@Autowired
	private CrScoreController crScoreController;
	@Autowired
	private MissionService missionService;

	@Autowired
	private MpEnvService mpEnvService;

	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 5.4弃用
	 * /single_train/single_train/record/detail 替代
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<JSONObject>}
	 */
	@ApiOperation("查询评分")
	@GetMapping("/history_detail")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<JSONObject> getHistoryDetail(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		RoleQuery roleQuery = new RoleQuery(resource_id, CrRole.SINGLE_ROLE_GROUP_CODE);
		List<CrRole> crRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);

		MissionReport missionReport = crScoreController.getTrainingMissionReport(resource_id,
				crRoleList);

		// 单兵训练仅有一个用户, 角色仅关联一个用户
		String user_hash = crRoleList.get(0).getUserIdList().get(0);

		List<Integer> radars = crRoleService.getRadar(resource_id, user_hash);

		List<MissionSchemeObject<TrainingRecordMissionRsp>> missionSchemeList = crScoreController
				.getTrainingRecord(resource_id, user_hash);

		StudentScoreRsp studentScoreRsp = crScoreController.getStudentScore(resource_id, user_hash);
		List<StageExport> StageExports = getStageExports(missionSchemeList);
		TrainRecord trainRecord = trainRecordService.getByRoomId(resource_id);

		JSONObject data = new JSONObject();
		data.put("task_count", gettaskCount(missionReport));
		data.put("radar", radars);
		data.put("stages", StageExports);
		data.put("total_score", studentScoreRsp.getTrainingSummary().getSumScoreObjective());
		data.put("create_time", trainRecord.getEndTime());
		data.put("title", crScoreController.getTitle(trainRecord.getRoomName()));
		data.put("resource_id", resource_id);
		data.put("score", studentScoreRsp.getTrainingSummary().getGetScoreObjective());

		// 用户信息
		UserInfo userInfo = remoteUserService.getById(user_hash);
		data.put("user_id", userInfo.getUserId());
		data.put("username", userInfo.getUserName());
		// 获取用户LOGO
		try {
			data.put("user_logo", mpEnvService.getMpHost() + userInfo.getAvatarUrl());
		} catch (Exception e) {
			throw new RemoteServiceException();
		}

		return new BaseRsp(data);
	}

	@ApiOperation("训练记录导出为PDF")
	@GetMapping("/train2pdf")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public void dumpTrainRecord2PDF(HttpServletResponse httpServletResponse,
			@RequestParam("room_id") String roomId) {
		JSONObject data = getHistoryDetail(roomId).getData();
		List<RadarLabel> radarLabels = missionService.getRadarLabel();
		data.put("radarLabels", radarLabels);
		data.put("locale", crScoreController.getTemplateI18n());

		Path destHtmlPathObj = Paths.get(AttachmentPath.SINGLE_HTML_TEMPLATE.getDesc(),
				String.format("%s-%s", roomId, data.getString("user_id")),
				String.format("%s.html", roomId));
		// 临时存放目录
		File directory = destHtmlPathObj.getParent().toFile();
		if (!directory.exists()) {
			boolean isCreated = directory.mkdirs();
			if (!isCreated) {
				log.error("failed_to_make_dir: Path = [{}]", directory.getPath());
				throw new BusinessException(ErrorCode.FAILED_TO_MAKE_DIR, directory.getPath());
			}
		}
		String destHtmlPath = destHtmlPathObj.toString();
		try {
			// 生成html
			HtmlToPdfUtil.getHtmlTemplateInstance(data, HtmlTemplateEnum.INDIVIDUAL, destHtmlPath);

			// 在同目录下生成pdf
			String destPdfPath = destHtmlPath.substring(0, destHtmlPath.lastIndexOf("html"))
					+ "pdf";
			HtmlToPdfUtil.convert(destHtmlPath, destPdfPath);

			FileUtil.setResponse(new File(destPdfPath), false, httpServletResponse);

		} catch (Exception e) {
			log.error("failed to export pdf: {}", destHtmlPath);
			throw e;
		} finally {
			if (!FileUtil.deleteDir(directory)) {
				log.error("failed to delete dir: {}", directory);
			}
		}

	}

	@ApiOperation("查询历史记录详情")
	@GetMapping("/record/detail")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<RecordDetail> getRecordDetail(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		if (StringUtils.isBlank(resource_id)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		RecordDetail detail = singleRoomService.getRecordDetail(resource_id);
		return new BaseRsp<>(detail);
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
