package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.ExportTrainMDReq;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.service.ExportTrainMDService;
import com.cyberpeace.cr.crbase.service.context.convert.BusinessTypeConvert;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/export")
public class ExportTrainMDController {

	@Qualifier("exportTrainMDServiceImpl")
	@Autowired
	private ExportTrainMDService exportTrainMDService;

	@Autowired
	private BusinessTemplateMapper businessTemplateMapper;

	@ApiOperation("训练资管导出")
	@PostMapping("/train")
	public BaseRsp<String> exportTrainMD(@RequestBody @Validated ExportTrainMDReq exportTrainMDReq) {

		if (!Arrays.stream(BusinessTypeConvert.values())
				   .map(BusinessTypeConvert::to)
				   .collect(Collectors.toList())
				   .contains(exportTrainMDReq.getType())) {
			throw new BusinessException(CrBusinessErrorCode.EXPORT_TRAIN_ERROR_TYPE);
		}

		String path;
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			path = ConfigUtil.getContextProperty("win.tmp.path");
		} else {
			path = ConfigUtil.getContextProperty("linux.export.train.path");
		}
		path = path + IDGeneratorUtil.getID();

		List<String> resourceIds = new ArrayList<>();
		if (exportTrainMDReq.getResourceIds() != null && exportTrainMDReq.getResourceIds().size() > 0) {
			resourceIds = exportTrainMDReq.getResourceIds();
		}
		if (exportTrainMDReq.getNames() != null && exportTrainMDReq.getNames().size() > 0) {
			List<String> idsByName = businessTemplateMapper.getBatchIdsByNames(exportTrainMDReq.getNames(),
					exportTrainMDReq.getType());
			resourceIds.addAll(idsByName);
			resourceIds = new ArrayList<>(new HashSet<>(resourceIds));
		}

		exportTrainMDService.chooseType(resourceIds, path, exportTrainMDReq.getType());

		return new BaseRsp<>(path);
	}
}
