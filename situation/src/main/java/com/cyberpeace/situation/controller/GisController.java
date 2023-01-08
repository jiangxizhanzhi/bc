package com.cyberpeace.situation.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.ZipUtil;
import com.cyberpeace.integratation.model.situation.PlatformContext;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.DashboardBatchDeleteReq;
import com.cyberpeace.situation.controller.res.TemplatePlatformRes;
import com.cyberpeace.situation.dao.entity.GisMark;
import com.cyberpeace.situation.dao.mapper.DashboardTemplateRefMapper;
import com.cyberpeace.situation.dao.mapper.GisMarkMapper;
import com.cyberpeace.situation.model.ViewAngle;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.context.DashboardTemplateContext;
import com.cyberpeace.situation.service.context.GisMarkContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "GIS接口")
@RestController
@Slf4j
@RequestMapping("/situation/gis")
public class GisController {

	@Autowired
	private GisMarkMapper gisMarkMapper;

	@Autowired
	private DashboardTemplateRefService dashboardTemplateRefService;

	@Autowired
	private RemoteSituationService remoteSituationService;

	public static final String RESOURCE_FILE_NAME = "data.json";

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private DashboardTemplateRefMapper dashboardTemplateRefMapper;

	@ApiOperation("新增标绘配置")
	@PostMapping("/mark/")
	public BaseRsp<GisMarkContext> setConfig(@RequestBody GisMarkContext gisMarkContext) {
		return new BaseRsp<>(addGisMark(gisMarkContext));
	}

	@ApiOperation("查询标绘配置")
	@GetMapping("/mark/")
	public BaseRsp<GisMarkContext> getConfig(@RequestParam("mark_id") @ApiParam(required = true,
			name = "mark_id", value = "标绘ID") String markId) {
		GisMark gisMark = gisMarkMapper.selectById(markId);
		return new BaseRsp<>(BeanUtils.copy(gisMark, GisMarkContext.class));
	}

	@ApiOperation("更新标绘配置")
	@PatchMapping("/mark/")
	public BaseRsp<GisMarkContext> updateConfig(@RequestBody GisMarkContext gisMarkContext) {

		// 软删除原标绘数据
		deleteMark(gisMarkContext.getMarkId());
		return new BaseRsp<>(addGisMark(gisMarkContext));
	}

	@ApiOperation("删除标绘配置")
	@DeleteMapping("/mark/")
	public BaseRsp<Boolean> deleteConfig(@RequestParam("mark_id") @ApiParam(required = true,
			name = "mark_id", value = "标绘ID") String markId) {
		deleteMark(markId);
		return new BaseRsp<>(true);
	}

	@ApiOperation("查询大屏标绘配置")
	@GetMapping("/mark/list/")
	public BaseRsp<List<GisMarkContext>> getConfigList(
			@RequestParam("dashboard_id") @ApiParam(required = true, name = "dashboard_id",
					value = "大屏ID") String dashboardId,
			@RequestParam(value = "end_time", required = false) @ApiParam(required = false,
					name = "end_time",
					value = "结束时间") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
		List<GisMarkContext> gisMarkList = gisMarkMapper.getGisMarkList(dashboardId, endTime);
		return new BaseRsp<>(gisMarkList);
	}

	@ApiOperation("清空大屏标绘数据")
	@DeleteMapping("/dashboard/mark/")
	public BaseRsp<Boolean>
			deleteDashboardMark(@RequestBody @Validated DashboardBatchDeleteReq ids) {
		List<String> dashboardIds = ids.getIds();
		List<String> markIds = gisMarkMapper.getMarkIdsByDashboardIds(dashboardIds);
		if (markIds.size() == 0) {
			return new BaseRsp<>(true);
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				gisMarkMapper.deleteBatchIds(markIds);
			}
		});

		return new BaseRsp<>(true);
	}

	@ApiOperation("导出大屏标绘数据")
	@GetMapping("/mark/export/")
	public void exportConfig(HttpServletResponse response,
			@RequestParam("dashboard_id") @ApiParam(required = true, name = "dashboard_id",
					value = "大屏ID") String dashboardId) {
		List<GisMarkContext> gisMarkList = gisMarkMapper.getGisMarkList(dashboardId, null);
		String tempDir = ZipUtil.generatePath();
		try {
			FileUtil.mkdirs(tempDir);
			String data = JSONObject.toJSONString(gisMarkList);
			String jsonPath = tempDir + File.separator + RESOURCE_FILE_NAME;
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(jsonPath),
					StandardCharsets.UTF_8);
			FileUtil.writeString(data, osw);
			String zipPath = ZipUtil.toZip(tempDir);
			File zip = new File(zipPath);
			FileUtil.setResponse(zip, false, response);
		} catch (Exception e) {
			log.error("failed to dump combat techs", e);
			throw new BusinessException(SituationErrorCode.FAILED_TO_EXPORT_DYNAMIC_SCHEME);
		} finally {
			if (!FileUtil.deleteDir(new File(tempDir))) {
				log.error("failed to remove temp dir :{}", tempDir);
			}
		}
	}

	public GisMarkContext addGisMark(GisMarkContext gisMarkContext) {
		GisMark gisMark = new GisMark();
		gisMark.setDashboardId(gisMarkContext.getDashboardId());
		gisMark.setConfig(JsonUtil.toJsonString(gisMarkContext.getMarkConfig()));
		gisMarkMapper.insert(gisMark);
		return BeanUtils.copy(gisMark, GisMarkContext.class);
	}

	public void deleteMark(String markId) {
		GisMark gisMark = gisMarkMapper.selectById(markId);
		gisMark.setDeleteTime(new Date());
		gisMarkMapper.updateById(gisMark);
	}

	@ApiOperation("通过模板ID获取平台列表")
	@GetMapping("/template/platform/")
	public BaseRsp<TemplatePlatformRes> getPlatformByTemplateId(
			@RequestParam("template_id") @ApiParam(required = true, name = "template_id",
					value = "模板ID") String templateId,
			@RequestParam("type") @ApiParam(required = true, name = "type",
					value = "类型 1-模板 2-实例") Integer type) {

		DashboardTemplateContext dashboardTemplateContext = dashboardTemplateRefService
				.getDashboardTemplateContext(templateId);

		if (dashboardTemplateContext == null) {
			return new BaseRsp<>(new TemplatePlatformRes());
		}
		List<PlatformContext> platformContexts = remoteSituationService.getPlatformList(
				dashboardTemplateContext.getTaskId(), dashboardTemplateContext.getSubjectId(),
				dashboardTemplateContext.getSubRangeId(), dashboardTemplateContext.getSchemeId());

		TemplatePlatformRes templatePlatformRes = new TemplatePlatformRes();
		templatePlatformRes.setPlatformList(platformContexts);
		templatePlatformRes.setViewAngle(
				JsonUtil.toJsonObject(dashboardTemplateContext.getViewAngle(), ViewAngle.class));
		return new BaseRsp<>(templatePlatformRes);
	}

}