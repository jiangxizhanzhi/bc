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
import com.cyberpeace.integratation.model.manage.ServiceLogContext;
import com.cyberpeace.situation.annotation.AuditLogger;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.dao.entity.GisMark;
import com.cyberpeace.situation.dao.entity.SituationPlotPixel;
import com.cyberpeace.situation.dao.mapper.GisMarkMapper;
import com.cyberpeace.situation.dao.mapper.SituationPublishInstanceMapper;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.SituationPlotPixelService;
import com.cyberpeace.situation.service.context.GisMarkContext;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;

/**
 * 嵌入服务-GIS标绘
 *
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

	@Autowired
	private SituationHandlerService situationHandlerService;

	public static final String RESOURCE_FILE_NAME = "data.json";

	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 新增标绘图元
	 *
	 * @param situationPlotPixelReq 标绘图元请求
	 * @return {@code BaseRsp<String>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.BHTY, type = ServiceLogContext.Type.XZ)
	@ApiOperation("标绘图元-新增")
	@PostMapping("/plot/pixel")
	public BaseRsp<String> createPlotPixel(
			@RequestBody @Validated(Create.class) SituationPlotPixelReq situationPlotPixelReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(situationPlotPixelReq.getName(), null);

		return new BaseRsp<>(
				situationPlotPixelService.createSituationPlotPixel(situationPlotPixelReq));
	}

	/**
	 * 修改标绘图元
	 *
	 * @param situationPlotPixelReq 标绘图元请求
	 * @return {@code BaseRsp<String>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.BHTY, type = ServiceLogContext.Type.XG)
	@ApiOperation("标绘图元-修改")
	@PatchMapping("/plot/pixel")
	public BaseRsp<String> updateSituationPlotPixel(
			@RequestBody @Validated(Update.class) SituationPlotPixelReq situationPlotPixelReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(situationPlotPixelReq.getName(), null);

		return new BaseRsp<>(
				situationPlotPixelService.updateSituationPlotPixel(situationPlotPixelReq));
	}

	/**
	 * 查询标绘图元
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<SituationPlotPixel>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.BHTY, type = ServiceLogContext.Type.CX)
	@ApiOperation("标绘图元-单个查询")
	@GetMapping("/plot/pixel")
	public BaseRsp<SituationPlotPixel>
			getSituationPlotPixelInfo(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id", value = "api资源id") String resourceId) {
		return new BaseRsp<>(situationPlotPixelService.getSituationPlotPixelInfo(resourceId));
	}

	/**
	 * 删除标绘图元
	 *
	 * @param situationIconDelReq 标绘图元删除请求
	 * @return {@code BaseRsp<Integer>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.BHTY, type = ServiceLogContext.Type.SC)
	@ApiOperation("标绘图元-批量删除")
	@DeleteMapping("/plot/pixel")
	public BaseRsp<Integer> deleteSituationPlotPixel(
			@RequestBody @Validated SituationIconDelReq situationIconDelReq) {
		return new BaseRsp<>(situationPlotPixelService
				.deleteSituationPlotPixel(situationIconDelReq.getIdList()));
	}

	/**
	 * 标绘图元-列表查询
	 *
	 * @param SituationPlotPixelPageReq 标绘图元分页请求
	 * @return {@code BaseRsp<ListRsp<SituationPlotPixel>>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.BHTY, type = ServiceLogContext.Type.CX)
	@ApiOperation("标绘图元-列表查询")
	@GetMapping("/plot/pixel/list")
	public BaseRsp<ListRsp<SituationPlotPixel>>
			getSituationPlotPixelPage(SituationPlotPixelPageReq SituationPlotPixelPageReq) {
		return new BaseRsp<>(
				situationPlotPixelService.getSituationPlotPixelPage(SituationPlotPixelPageReq));
	}

	/**
	 * 新增标绘数据
	 *
	 * @param gisMarkContext 标绘数据
	 * @return {@code BaseRsp<GisMarkContext>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.TSBH, type = ServiceLogContext.Type.XZ)
	@ApiOperation("标绘数据-新增")
	@PostMapping("/gis/mark/")
	public BaseRsp<GisMarkContext> setConfig(@RequestBody GisMarkContext gisMarkContext) {
		return new BaseRsp<>(addGisMark(gisMarkContext));
	}

	/**
	 * 查询标绘数据
	 *
	 * @param markId 标绘ID
	 * @return {@code BaseRsp<GisMarkContext>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.TSBH, type = ServiceLogContext.Type.CX)
	@ApiOperation("标绘数据-单个查询")
	@GetMapping("/gis/mark/")
	public BaseRsp<GisMarkContext> getConfig(@RequestParam("mark_id") @ApiParam(required = true,
			name = "mark_id", value = "标绘ID") String markId) {
		GisMark gisMark = gisMarkMapper.selectById(markId);
		return new BaseRsp<>(BeanUtils.copy(gisMark, GisMarkContext.class));
	}

	/**
	 * 修改标绘数据
	 *
	 * @param gisMarkContext 标绘数据
	 * @return {@code BaseRsp<GisMarkContext>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.TSBH, type = ServiceLogContext.Type.XG)
	@ApiOperation("标绘数据-修改")
	@PatchMapping("/gis/mark/")
	public BaseRsp<GisMarkContext> updateConfig(@RequestBody GisMarkContext gisMarkContext) {

		// 软删除原标绘数据
		deleteMark(gisMarkContext.getMarkId());
		return new BaseRsp<>(addGisMark(gisMarkContext));
	}

	/**
	 * 删除标绘数据
	 *
	 * @param markId 标绘ID
	 * @return {@code BaseRsp<Boolean>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.TSBH, type = ServiceLogContext.Type.SC)
	@ApiOperation("标绘数据-删除")
	@DeleteMapping("/gis/mark/")
	public BaseRsp<Boolean> deleteConfig(@RequestParam("mark_id") @ApiParam(required = true,
			name = "mark_id", value = "标绘ID") String markId) {
		deleteMark(markId);
		return new BaseRsp<>(true);
	}

	/**
	 * 标绘数据-列表查询
	 *
	 * @param dashboardId 大屏ID
	 * @param endTime     结束时间
	 * @return {@code BaseRsp<List<GisMarkContext>>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.TSBH, type = ServiceLogContext.Type.CX)
	@ApiOperation("标绘数据-列表查询")
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

	/**
	 * 清空大屏标绘数据
	 *
	 * @param ids 大屏ID列表
	 * @return {@code BaseRsp<Boolean>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.TSBH, type = ServiceLogContext.Type.SC)
	@ApiOperation("标绘数据-清空")
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

	/**
	 * 导出标绘数据
	 *
	 * @param dashboardId 大屏ID
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.BHWJ, type = ServiceLogContext.Type.DC)
	@ApiOperation("标绘数据-导出")
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

	/**
	 * 导入标绘数据
	 *
	 * @param req {@code ImportMarkReq}
	 * @return {@code BaseRsp<Boolean>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.BHWJ, type = ServiceLogContext.Type.DR)
	@ApiOperation("标绘数据-导入")
	@PostMapping("/gis/mark/import")
	public BaseRsp<Boolean> importMark(@Validated ImportMarkReq req)
			throws IOException, ZipException {

		// 1. 临时存储导入上传的文件
		File markFile = FileUtil.saveFile(req.getFile(), ZipUtil.generatePath());

		// 2. 解压文件
		String unZipPath = ZipUtil.unZipToTmpPath(markFile.getAbsolutePath());

		// 3. 导入标绘
		// 3.1 读取导入文件
		String dataJson = FileUtil
				.getContent(new File(unZipPath + File.separator + RESOURCE_FILE_NAME));

		// 3.2 提取标绘数据
		List<GisMarkContext> inputMarks = JsonUtil.toJsonObjectArray(dataJson,
				GisMarkContext.class);
		List<GisMark> insertMarks = Lists.newArrayList();

		// 3.3 如果当前大屏数据下不存在<拥有相同标绘ID的且未被软删除的记录>，插入标绘数据
		for (GisMarkContext gisMarkContext : inputMarks) {
			if (Boolean.FALSE.equals(
					gisMarkMapper.checkMark(req.getDashboardId(), gisMarkContext.getMarkId()))) {
				GisMark gisMark = new GisMark();
				gisMark.setResourceId(IDGeneratorUtil.getID());
				gisMark.setDashboardId(req.getDashboardId());
				gisMark.setConfig(JsonUtil.toJsonString(gisMarkContext.getMarkConfig()));
				insertMarks.add(gisMark);
			}
		}

		// 4、批量插入标绘数据
		if (!insertMarks.isEmpty()) {
			gisMarkMapper.insertBatch(insertMarks);
		}

		// 5. 删除临时文件
		if (!FileUtil.deleteDir(new File(unZipPath))) {
			log.error("failed to delete temp dir");
		}

		// 6、构造导入结果统计
		String message = String.format("共检测到%d个标绘数据，成功导入%d个", inputMarks.size(),
				insertMarks.size());
		return new BaseRsp(true, message);
	}

	/**
	 * 添加标绘
	 *
	 * @param gisMarkContext 标绘数据
	 * @return {@code GisMarkContext}
	 */
	public GisMarkContext addGisMark(GisMarkContext gisMarkContext) {
		GisMark gisMark = new GisMark();
		gisMark.setDashboardId(gisMarkContext.getDashboardId());
		gisMark.setConfig(JsonUtil.toJsonString(gisMarkContext.getMarkConfig()));
		gisMarkMapper.insert(gisMark);
		return BeanUtils.copy(gisMark, GisMarkContext.class);
	}

	/**
	 * 删除标绘
	 *
	 * @param markId 标绘ID
	 */
	public void deleteMark(String markId) {
		GisMark gisMark = gisMarkMapper.selectById(markId);
		gisMark.setDeleteTime(new Date());
		gisMarkMapper.updateById(gisMark);
	}
}
