package com.cyberpeace.assess.controller.web;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.assess.controller.req.AssessMateriaFilterReq;
import com.cyberpeace.assess.controller.req.AssessMateriaUpdateReq;
import com.cyberpeace.assess.controller.req.AssessMaterialAddReq;
import com.cyberpeace.assess.controller.req.BatchDeleteReq;
import com.cyberpeace.assess.controller.res.AssessMaterialExcelDatalRes;
import com.cyberpeace.assess.dao.entity.AssessMaterial;
import com.cyberpeace.assess.dao.mapper.AssessMaterialConfigMapper;
import com.cyberpeace.assess.dao.mapper.AssessMaterialMapper;
import com.cyberpeace.assess.service.AssessCaseService;
import com.cyberpeace.assess.service.AssessFileDataService;
import com.cyberpeace.assess.service.AssessMaterialConfigService;
import com.cyberpeace.assess.service.AssessMaterialService;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cs.service.UtilService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "评估材料web")
@RestController
@RequestMapping("/web/assess/material")
@RequiresAuthentication
@RequiresPermissions(MenuConstant.WEB_ASSESS_EVALUATE_CASE_DATA_MANAGE)
public class AssessMaterialWebController {

	@Autowired
	private AssessMaterialMapper assessMaterialMapper;

	@Autowired
	private AssessFileDataService assessFileDataService;
	@Autowired
	private AssessCaseService assessCaseService;

	@Autowired
	private UtilService utilService;


	@ApiOperation("查询list")
	@GetMapping("/list")
	public BaseRsp<List<AssessMaterial>>
			getAssessCases(AssessMateriaFilterReq assessMateriaFilterReq) {
		LambdaQueryWrapper<AssessMaterial> queryWrapper = new LambdaQueryWrapper<>();
		String caseId = assessMateriaFilterReq.getCaseId();
		assessCaseService.checkCaseId(caseId);

		queryWrapper.eq(AssessMaterial::getCaseId, caseId);
		String name = assessMateriaFilterReq.getName();
		if (StringUtils.isNotBlank(name)){
			queryWrapper.like(AssessMaterial::getName, name);

		}
		Integer page = assessMateriaFilterReq.getPage();
		Integer perPage = assessMateriaFilterReq.getPerPage();
		queryWrapper.orderByDesc(AssessMaterial::getCreateTime);
		IPage<AssessMaterial> assessMaterials = assessMaterialMapper.selectPage(new Page<>(page, perPage), queryWrapper);

		return new BaseRsp(assessMaterials);
	}


	@ApiOperation("详情")
	@GetMapping("")
	public BaseRsp<AssessMaterialExcelDatalRes> getAssessCase(@ApiParam(name = "resource_id",
			value = "评估材料id", required = true) @RequestParam(value = "resource_id") @NotNull String resourceId) {
		AssessMaterial assessMaterial = assessMaterialMapper.selectById(resourceId);
		if(null == assessMaterial){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		AssessMaterialExcelDatalRes assessMaterialExcelDatalRes = new AssessMaterialExcelDatalRes();
		BeanUtils.copyProperties(assessMaterial, assessMaterialExcelDatalRes);

		if(assessMaterial.getMaterialType().equals(AssessMaterial.MaterialType.EXCEL.getCode())){
			assessMaterialExcelDatalRes.setData(JsonUtil.toJsonObject(assessFileDataService.getDataBaseTableByRelatedId(
				assessMaterial.getResourceId()), JSONObject.class));
		}

		String attachment = assessMaterial.getAttachment();
		assessMaterialExcelDatalRes.setAttachmentUrl(attachment);
		String fileSuffix = FileUtil.getFileSuffix(attachment);
		if (assessMaterial.getMaterialType().equals(AssessMaterial.MaterialType.TEXT.getCode()) && (fileSuffix.equals(FileSunffixConstant.DOC.getDesc())
				|| fileSuffix.equals(FileSunffixConstant.DOCX.getDesc())) ){
			assessMaterialExcelDatalRes.setAttachmentUrl(utilService.officeMaterialUrl(attachment, assessMaterial.getResourceId()));
		}

		return new BaseRsp<>(assessMaterialExcelDatalRes);
	}


}