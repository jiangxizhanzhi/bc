package com.cyberpeace.situation.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.cyberpeace.common.util.*;
import com.cyberpeace.situation.dao.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.integratation.model.situation.PlatformContext;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.MapSource;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.BatchDeleteReq;
import com.cyberpeace.situation.controller.req.DashboardBatchDeleteReq;
import com.cyberpeace.situation.controller.req.ImportMarkReq;
import com.cyberpeace.situation.controller.req.SituationGisReq;
import com.cyberpeace.situation.controller.res.TemplatePlatformRes;
import com.cyberpeace.situation.dao.entity.DashboardTemplate;
import com.cyberpeace.situation.dao.entity.GisMark;
import com.cyberpeace.situation.model.IntType;
import com.cyberpeace.situation.model.ViewAngle;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.DashboardTemplateService;
import com.cyberpeace.situation.service.SituationGisService;
import com.cyberpeace.situation.service.context.ConfigContext;
import com.cyberpeace.situation.service.context.DashboardTemplateContext;
import com.cyberpeace.situation.service.context.GisMarkContext;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;

@Api(tags = "GIS接口")
@RestController
@Slf4j
// @RequiresAuthentication
@RequestMapping("/situation/gis")
public class GisController {

	@Autowired
	private GisMarkMapper gisMarkMapper;

	@Autowired
	private DashboardTemplateRefService dashboardTemplateRefService;

	@Autowired
	private RemoteSituationService remoteSituationService;

	@Autowired
	private DashboardTemplateMapper dashboardTemplateMapper;

	@Autowired
	private SituationPublishInstanceMapper situationPublishInstanceMapper;

	@Autowired
	private DashboardTemplateService dashboardTemplateService;

	public static final String RESOURCE_FILE_NAME = "data.json";

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private DashboardTemplateRefMapper dashboardTemplateRefMapper;

	@Autowired
	private SituationGisMapper situationGisMapper;

	@Autowired
	private SituationGisService situationGisService;

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
		String dashboardName = situationPublishInstanceMapper.selectById(dashboardId).getName();
		String fileName = dashboardName + "_标绘数据_" + TimeUtil.format5(new Date());
		String tempDir = ZipUtil.generatePath(fileName);
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

	@ApiOperation("导入大屏标绘数据")
	@PostMapping("/mark/import")
	public BaseRsp<Boolean> importMark(@Validated ImportMarkReq req)
			throws IOException, ZipException {
		// 1. 临时存储导入文件
		File localFile = FileUtil.saveFile(req.getFile(), ZipUtil.generatePath());
		// 2. 解压文件
		String unZipPath = ZipUtil.unZipToTmpPath(localFile.getAbsolutePath());
		// 3. 导入标绘
		String dataJson = FileUtil
				.getContent(new File(unZipPath + File.separator + RESOURCE_FILE_NAME));
		List<GisMarkContext> gisMarkContexts = JsonUtil.toJsonObjectArray(dataJson,
				GisMarkContext.class);
		List<GisMark> gisMarks = Lists.newArrayList();
		for (GisMarkContext gisMarkContext : gisMarkContexts) {
			if (!req.getDashboardId().equals(gisMarkContext.getDashboardId())
					|| Boolean.TRUE.equals(gisMarkMapper.checkMark(req.getDashboardId(),
							gisMarkContext.getMarkId()))) {
				continue;
			} else {
				GisMark gisMark = new GisMark();
				gisMark.setResourceId(gisMarkContext.getMarkId());
				gisMark.setDashboardId(req.getDashboardId());
				gisMark.setConfig(JsonUtil.toJsonString(gisMarkContext.getMarkConfig()));
				gisMarks.add(gisMark);
			}
		}

		if (!gisMarks.isEmpty()) {
			gisMarkMapper.insertBatch(gisMarks);
		}
		// 4. 删除临时文件
		if (!FileUtil.deleteDir(new File(unZipPath))) {
			log.error("failed to delete temp dir");
		}
		return new BaseRsp(true);
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

		if (type.equals(1)) {
			DashboardTemplate dashboardTemplate = dashboardTemplateMapper.selectById(templateId);
			if (dashboardTemplate != null) {
				return new BaseRsp<>(new TemplatePlatformRes(
						JsonUtil.toJsonObject(dashboardTemplate.getViewAngle(), ViewAngle.class)));
			}
		}

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

	@ApiOperation("获取Gis配置")
	@GetMapping("/config/get/")
	public BaseRsp<ConfigContext>
			getSituationGisConfig(@RequestParam("resource_id") String resourceId) {
		return new BaseRsp<>(situationGisService.getSituationGisConfig(resourceId));
	}

	@ApiOperation("新增Gis配置")
	@PostMapping("/config/create/")
	public BaseRsp<ConfigContext> createSituationGisConfig(
			@RequestBody @Validated(Create.class) SituationGisReq situationGisReq) {
		return new BaseRsp<>(situationGisService.createSituationGisConfig(situationGisReq));
	}

	@ApiOperation("修改Gis配置")
	@PatchMapping("/config/update/")
	public BaseRsp<ConfigContext> updateSituationGisConfig(
			@RequestBody @Validated(Update.class) SituationGisReq situationGisReq) {
		return new BaseRsp<>(situationGisService.updateSituationGisConfig(situationGisReq));
	}

	@ApiOperation("删除Gis配置")
	@PatchMapping("/config/delete/")
	public BaseRsp<Integer>
			deleteSituationGisConfig(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		return new BaseRsp<>(situationGisService.deleteSituationGisConfig(batchDeleteReq.getIds()));
	}

	@ApiOperation("获取全量GIS配置列表")
	@GetMapping("/config/list/")
	public BaseRsp<List<ConfigContext>> getAllConfig() {
		return new BaseRsp<>(situationGisService.getAllConfig());
	}

	@ApiOperation("获取经纬度所属省份")
	@GetMapping("/province/")
	public BaseRsp<String> getProvince(
			@RequestParam("longitude") @ApiParam(required = true, name = "longitude",
					value = "经度") String longitude,
			@RequestParam("latitude") @ApiParam(required = true, name = "latitude",
					value = "维度") String latitude) {
		return new BaseRsp<>(
				situationGisService.getProvince(Float.valueOf(longitude), Float.valueOf(latitude)));
	}

	@ApiOperation("获取大屏地图源选项")
	@GetMapping("/map/source/list/")
	public BaseRsp<List<IntType>> getMapSourceList() {
		return new BaseRsp<>(MapSource.getList());
	}

	@ApiOperation("地图静态资源鉴权")
	@GetMapping("/map/resource/auth/")
	public BaseRsp<Boolean> authMapResource(HttpServletResponse response) {
		if (situationGisService.checkAuthorizationIp(CommonContext.getIp())) {
			return new BaseRsp<>(true);
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new BaseRsp<>(false);
		}
	}
}