package com.cyberpeace.cr.singletrain.controller.web;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.DateUtil;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.HtmlToPdfUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.CrScoreController;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreRsp;
import com.cyberpeace.cr.crbase.controller.rsp.TrainingRecordMissionRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.RadarLabel;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
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

	@Autowired
	private CrConstantsService crConstantsService;

	/**
	 * 5.4弃用
	 * /single_train/single_train/record/detail 替代
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<JSONObject>}
	 */
	@ApiOperation("查询评分")
	@GetMapping("/history_detail")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<JSONObject> getHistoryDetail(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resourceId) {
		RoleQuery roleQuery = new RoleQuery(resourceId, CrRole.SINGLE_ROLE_GROUP_CODE);
		List<CrRole> crRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);

		MissionReport missionReport = crScoreController.getTrainingMissionReport(resourceId,
				crRoleList);

		// 单兵训练仅有一个用户, 角色仅关联一个用户
		String userHash = crRoleList.get(0).getUserIdList().get(0);

		List<Integer> radars = crRoleService.getRadar(resourceId, userHash);

		List<MissionSchemeObject<TrainingRecordMissionRsp>> missionSchemeList = crScoreController
				.getTrainingRecord(resourceId, userHash);

		StudentScoreRsp studentScoreRsp = crScoreController.getStudentScore(resourceId, userHash);
		List<StageExport> stageExports = getStageExports(missionSchemeList);
		TrainRecord trainRecord = trainRecordService.getByRoomId(resourceId);

		JSONObject data = new JSONObject();
		data.put("task_count", gettaskCount(missionReport));
		data.put("radar", radars);
		data.put("stages", stageExports);
		data.put("total_score", studentScoreRsp.getTrainingSummary().getSumScoreObjective());
		data.put("create_time", trainRecord.getEndTime());
		data.put("title", crScoreController.getTitle(trainRecord.getRoomName()));
		data.put("resource_id", resourceId);
		data.put("score", studentScoreRsp.getTrainingSummary().getGetScoreObjective());

		// 用户信息
		UserInfo userInfo = remoteUserService.getById(userHash);
		data.put("user_id", userInfo.getUserId());
		data.put("username", userInfo.getUserName());
		// 获取用户LOGO
		try {
			data.put("user_logo", mpEnvService.getMpHost() + userInfo.getAvatarUrl());
		} catch (Exception e) {
			throw new RemoteServiceException();
		}

		return new BaseRsp<>(data);
	}

	@ApiOperation("训练记录导出为PDF")
	@GetMapping("/train2pdf")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public void dumpTrainRecord2PDF(HttpServletResponse httpServletResponse,
			@RequestParam("room_id") String roomId, @RequestParam("index") Integer index) {
		RecordDetail data = getRecordDetail(roomId).getData();
		data.setIndex(index);
		List<RadarLabel> radarLabels = missionService.getRadarLabel();
		data.setTrainingTimeString(
				DateUtil.DateToString(data.getTrainingTime(), "yyyy-MM-dd HH:mm:ss"));
		data.setSubTypeString(crConstantsService.geCnNameByTypeAndCode(
				CrConstantsConfig.ConstantsType.TRAINING_CATEGORY.getCode(), data.getSubType()));
		data.setDifficultyString(crConstantsService.geCnNameByTypeAndCode(
				CrConstantsConfig.ConstantsType.DIFFICULTY.getCode(), data.getDifficulty()));
		data.setRadarsString(data.getRadars().stream().map(a -> Integer.toString(a))
				.collect(Collectors.joining(",")));

		data.setRadarNamesString(
				radarLabels.stream().map(a -> "{ name: '" + a.getLabel() + "', max: 100 }")
						.collect(Collectors.joining(",")));
		Path destHtmlPathObj = Paths.get(AttachmentPath.SINGLE_HTML_TEMPLATE.getDesc(),
				String.format("%s-%s", roomId, UserInfoContext.getUserContext().getUserId()),
				String.format("%s.html", roomId));
		FileUtil.copyDirectory(HtmlTemplateEnum.SINGLE.getTemplatePath() + "/img",
				AttachmentPath.SINGLE_HTML_TEMPLATE.getDesc(), true);
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
			HtmlToPdfUtil.getHtmlTemplateInstance(data, HtmlTemplateEnum.SINGLE, destHtmlPath);

			// 在同目录下生成pdf
			String destPdfPath = destHtmlPath.substring(0, destHtmlPath.lastIndexOf("html"))
					+ "pdf";
			HtmlToPdfUtil.convert(destHtmlPath, destPdfPath);

			FileUtil.setResponse(new File(destPdfPath), false, httpServletResponse);

		} catch (Exception e) {
			log.error("failed to export pdf: {}", destHtmlPath);
			throw e;
		} finally {
			if (Boolean.FALSE.equals(FileUtil.deleteDir(directory))) {
				log.error("failed to delete dir: {}", directory);
			}
		}

	}

	@ApiOperation("查询历史记录详情")
	@GetMapping("/record/detail")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<RecordDetail> getRecordDetail(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		RecordDetail detail = singleRoomService.getRecordDetail(resourceId);
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

		List<StageExport> stageExports = Lists.newArrayList();

		for (MissionSchemeObject<TrainingRecordMissionRsp> missionSchemeObject : missionSchemeList) {
			StageExport stageExport = new StageExport(missionSchemeObject);

			stageExports.add(stageExport);
		}

		return stageExports;
	}

}
