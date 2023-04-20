package com.cyberpeace.situation.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.*;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.dao.entity.GisMark;
import com.cyberpeace.situation.dao.entity.SituationPlotPixel;
import com.cyberpeace.situation.dao.mapper.GisMarkMapper;
import com.cyberpeace.situation.dao.mapper.SituationPublishInstanceMapper;
import com.cyberpeace.situation.service.SituationPlotPixelService;
import com.cyberpeace.situation.service.context.GisMarkContext;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;

/**
 * @Author xuxz
 * @Date 17/12/2022 下午5:01
 */

@Api(tags = "嵌入服务-GIS标绘")
@Slf4j
@RestController
@RequiresAuthentication
@RequestMapping("/situation")
public class EmbeddedGisMarkController {

	@Autowired
	private GisMarkMapper gisMarkMapper;

	@Autowired
	private SituationPlotPixelService situationPlotPixelService;

	@Autowired
	private SituationPublishInstanceMapper situationPublishInstanceMapper;

	public static final String RESOURCE_FILE_NAME = "data.json";

	@Autowired
	private TransactionTemplate transactionTemplate;

	@ApiOperation("新增绘标图元")
	@PostMapping("/plot/pixel")
	public BaseRsp<String> createColorTheme(
			@RequestBody @Validated(Create.class) SituationPlotPixelReq situationPlotPixelReq) {
		return new BaseRsp<>(
				situationPlotPixelService.createSituationPlotPixel(situationPlotPixelReq));
	}

	@ApiOperation("修改绘标图元")
	@PatchMapping("/plot/pixel")
	public BaseRsp<String> updateSituationPlotPixel(
			@RequestBody @Validated(Update.class) SituationPlotPixelReq situationPlotPixelReq) {
		return new BaseRsp<>(
				situationPlotPixelService.updateSituationPlotPixel(situationPlotPixelReq));
	}

	@ApiOperation("获取改绘标图元详情")
	@GetMapping("/plot/pixel")
	public BaseRsp<SituationPlotPixel>
			getSituationPlotPixelInfo(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id", value = "api资源id") String resourceId) {
		return new BaseRsp<>(situationPlotPixelService.getSituationPlotPixelInfo(resourceId));
	}

	@ApiOperation("批量删除改绘标图元")
	@DeleteMapping("/plot/pixel")
	public BaseRsp<Integer> deleteSituationPlotPixel(
			@RequestBody @Validated SituationIconDelReq situationIconDelReq) {
		return new BaseRsp<>(situationPlotPixelService
				.deleteSituationPlotPixel(situationIconDelReq.getIdList()));
	}

	@ApiOperation("绘标图元分页查询")
	@GetMapping("/plot/pixel/list")
	public BaseRsp<ListRsp<SituationPlotPixel>>
			getSituationPlotPixelPage(SituationPlotPixelPageReq SituationPlotPixelPageReq) {
		return new BaseRsp<>(
				situationPlotPixelService.getSituationPlotPixelPage(SituationPlotPixelPageReq));
	}

	@ApiOperation("新增标绘配置")
	@PostMapping("/gis/mark/")
	public BaseRsp<GisMarkContext> setConfig(@RequestBody GisMarkContext gisMarkContext) {
		return new BaseRsp<>(addGisMark(gisMarkContext));
	}

	@ApiOperation("查询标绘配置")
	@GetMapping("/gis/mark/")
	public BaseRsp<GisMarkContext> getConfig(@RequestParam("mark_id") @ApiParam(required = true,
			name = "mark_id", value = "标绘ID") String markId) {
		GisMark gisMark = gisMarkMapper.selectById(markId);
		return new BaseRsp<>(BeanUtils.copy(gisMark, GisMarkContext.class));
	}

	@ApiOperation("更新标绘配置")
	@PatchMapping("/gis/mark/")
	public BaseRsp<GisMarkContext> updateConfig(@RequestBody GisMarkContext gisMarkContext) {

		// 软删除原标绘数据
		deleteMark(gisMarkContext.getMarkId());
		return new BaseRsp<>(addGisMark(gisMarkContext));
	}

	@ApiOperation("删除标绘配置")
	@DeleteMapping("/gis/mark/")
	public BaseRsp<Boolean> deleteConfig(@RequestParam("mark_id") @ApiParam(required = true,
			name = "mark_id", value = "标绘ID") String markId) {
		deleteMark(markId);
		return new BaseRsp<>(true);
	}

	@ApiOperation("查询大屏标绘配置")
	@GetMapping("/gis/mark/list/")
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
	@DeleteMapping("/gis/dashboard/mark/")
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
	@GetMapping("/gis/mark/export/")
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
	@PostMapping("/gis/mark/import")
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
		return new BaseRsp(true, "标绘文件导入成功");
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
}
