package com.cyberpeace.cr.crbase.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.ExportTrainMDReq;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.service.ExportTrainMDService;
import com.cyberpeace.cr.crbase.service.context.convert.BusinessTypeConvert;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 训练资管导出控制
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Api(tags = "训练资管导出")
@RestController
@RequestMapping("/export")
public class ExportTrainMDController {

	@Qualifier("exportTrainMDServiceImpl")
	@Autowired
	private ExportTrainMDService exportTrainMDService;

	@Autowired
	private BusinessTemplateMapper businessTemplateMapper;

	private static final Logger log = LoggerFactory.getLogger("resourceImport");

	/**
	 * 训练资管导出
	 *
	 * @param exportTrainMDReq 出口火车mdreq
	 * @return {@link BaseRsp}<{@link String}>
	 */
	@ApiOperation("训练资管导出")
	@PostMapping("/train")
	public BaseRsp<String>
			exportTrainMD(@RequestBody @Validated ExportTrainMDReq exportTrainMDReq) {

		// 判断type是否正确
		List<Integer> sceneTypeList = EnumUtils.getListCode(BusinessTypeConvert.class);
		if (!sceneTypeList.contains(exportTrainMDReq.getType())) {
			String typeString = StringUtils.join(sceneTypeList, ",");
			throw new BusinessException(CrBusinessErrorCode.EXPORT_TRAIN_ERROR_TYPE, typeString);
		}
		// 导出路径
		String path;
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			path = ConfigUtil.getContextProperty("win.tmp.path");
		} else {
			path = CommonConstant.EXPORT_TRAIN_PATH;
		} ;
		// 获取导出资源id
		List<String> resourceIds = new ArrayList<>();
		if (exportTrainMDReq.getResourceIds() != null
				&& exportTrainMDReq.getResourceIds().size() > 0) {
			resourceIds = exportTrainMDReq.getResourceIds();
		}
		if (exportTrainMDReq.getNames() != null && exportTrainMDReq.getNames().size() > 0) {
			List<String> idsByName = businessTemplateMapper
					.getBatchIdsByNames(exportTrainMDReq.getNames(), exportTrainMDReq.getType());
			resourceIds.addAll(idsByName);
			resourceIds = new ArrayList<>(new HashSet<>(resourceIds));
		}

		if (resourceIds.size() == 0) {
			File file = new File(path);
			file.mkdirs();
			return new BaseRsp<>(path);
		}

		try {
			exportTrainMDService.chooseTypeAndExportTrain(resourceIds, path,
					exportTrainMDReq.getType());
		} catch (IOException e) {
			log.error("export [{}] failed", StringUtils.join(resourceIds, ","));
			log.error("export error:", e);
		}

		log.info("本次导出结束");

		return new BaseRsp<>(path);
	}
}
