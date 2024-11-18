package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.ExportTrainMDService;
import com.cyberpeace.cr.crbase.service.context.convert.BusinessTypeConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出训练md控制器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Api(tags = "训练资管导出")
@RestController
@RequestMapping("/export")
public class ExportTrainMDController {

	/**
	 * 导出训练md服务
	 *
	 * @see ExportTrainMDService
	 */
	@Qualifier("exportTrainMDServiceImpl")
	@Autowired
	private ExportTrainMDService exportTrainMDService;

	/**
	 * 日志
	 *
	 * @see Logger
	 */
	private static final Logger log = LoggerFactory.getLogger("resourceImport");

	/**
	 * 导出训练Markdown
	 *
	 * @param resourceId         资源id
	 * @param type               类型
	 * @param sceneNumber        场景数
	 * @param disableImageExport 禁用图像导出
	 * @return {@code BaseRsp<String> }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see String
	 * @see Boolean
	 * @see BaseRsp<String>
	 */
	@ApiOperation("训练资管导出")
	@GetMapping("/train")
	public BaseRsp<String> exportTrainMD(
			@ApiParam(name = "resource_id", value = "场景id", required = true) @RequestParam(name = "resource_id") String resourceId,
			@ApiParam(name = "type", value = "场景类型", required = true) @RequestParam(name = "type") Integer type,
			@ApiParam(name = "scene_number", value = "场景编号", required = false) @RequestParam(name = "scene_number", required = false) String sceneNumber,
			@ApiParam(name = "disable_image_export", value = "是否禁止导出镜像", required = false) @RequestParam(name = "disable_image_export", required = false) Boolean disableImageExport) {
		//判断type是否正确
		List<Integer> sceneTypeList = EnumUtils.getListCode(BusinessTypeConvert.class);
		if (!sceneTypeList.contains(type)) {
			String typeString = StringUtils.join(sceneTypeList, ",");
			log.error("Type is error,only sustain [{}]! type = [{}]", typeString, type);
			throw new BusinessException(CrBusinessErrorCode.EXPORT_TRAIN_ERROR_TYPE,typeString);
		}
		//导出路径
		String path;
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			path = ConfigUtil.getContextProperty("win.tmp.path");
		} else {
			path = CommonConstant.EXPORT_TRAIN_PATH;
		};
		//获取导出资源id
		List<String> resourceIds = new ArrayList<>();
		resourceIds.add(resourceId);

		if(resourceIds.size() == 0){
			File file = new File(path);
			file.mkdirs();
			return new BaseRsp<>(path);
		}

		try{
			exportTrainMDService.chooseTypeAndExportTrain(resourceIds, path, type, sceneNumber, disableImageExport);
		}catch (IOException e){
			log.error("export [{}] failed",StringUtils.join(resourceIds, ","));
			log.error("export error:", e);
		}

		return new BaseRsp<>(path);
	}
}
