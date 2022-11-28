package com.cyberpeace.assess.controller;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.assess.service.AssessCaseService;
import com.cyberpeace.assess.service.AssessFileDataService;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.assess.controller.req.AssessMateriaFilterReq;
import com.cyberpeace.assess.controller.req.AssessMateriaUpdateReq;
import com.cyberpeace.assess.controller.req.AssessMaterialAddReq;
import com.cyberpeace.assess.controller.req.BatchDeleteReq;
import com.cyberpeace.assess.controller.res.AssessMaterialExcelDatalRes;
import com.cyberpeace.assess.dao.entity.AssessMaterial;
import com.cyberpeace.assess.dao.entity.AssessMaterialConfig;
import com.cyberpeace.assess.dao.mapper.AssessMaterialConfigMapper;
import com.cyberpeace.assess.dao.mapper.AssessMaterialMapper;
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
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "评估材料")
@RestController
@RequestMapping("/assess/material")
@RequiresAuthentication
public class AssessMaterialController {

	@Autowired
	private AssessMaterialMapper assessMaterialMapper;

	@Autowired
	private AssessMaterialConfigMapper assessMaterialConfigMapper;

	@Autowired
	private AssessMaterialService assessMaterialService;

	@Autowired
	private AssessMaterialConfigService assessMaterialConfigService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private AssessFileDataService assessFileDataService;
	@Autowired
	private AssessCaseService assessCaseService;


	@ApiOperation("新增")
	@PostMapping("")
	public BaseRsp<AssessMaterial>
			setAssessCase(@RequestBody @Validated(Create.class) AssessMaterialAddReq assessMaterialAddReq) {

		assessMaterialService.checkNameRepeat(assessMaterialAddReq.getName(), null);
		assessCaseService.checkCaseId(assessMaterialAddReq.getCaseId());

		AssessMaterial assessMaterial = BeanUtils.copy(assessMaterialAddReq,
				AssessMaterial.class);
		assessMaterialService.uploadHandout(assessMaterial, assessMaterialAddReq.getAttachment());
		AuthContext auth = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);
		assessMaterial.setCreateUserId(auth.getUserId());

		String uuid = String.valueOf(UUID.randomUUID());
		assessMaterial.setResourceId(uuid);

		assessMaterialConfigService.setConfig(assessMaterial);

		if(assessMaterialAddReq.getMaterialType().equals(AssessMaterial.MaterialType.EXCEL.getCode())){
			assessFileDataService.parseModelFile(assessMaterial.getResourceId(), 2, assessMaterial.getAttachment());
		}
		assessMaterialMapper.insert(assessMaterial);

		return new BaseRsp<>(assessMaterial);
	}

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

		IPage<AssessMaterial> assessMaterials = assessMaterialMapper.selectPage(new Page<>(page, perPage), queryWrapper);
		IPage<AssessMaterial> res = assessMaterials;
		res.setRecords(assessMaterials.getRecords().stream().sorted(Comparator.comparing(AssessMaterial::getCreateTime).reversed()).collect(Collectors.toList()));

		return new BaseRsp(PageUtil.getListResult(res));
	}

	@ApiOperation("删除")
	@DeleteMapping("")
	public BaseRsp deleteAssessCase(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		List<String> ids = batchDeleteReq.getIds();
		List<AssessMaterial> assessCases = assessMaterialMapper.selectBatchIds(ids);
		if (!assessCases.isEmpty()) {
			assessMaterialMapper.deleteBatchIds(ids);
		}
		return new BaseRsp<>();
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
		return new BaseRsp<>(assessMaterialExcelDatalRes);
	}

	@ApiOperation("修改")
	@PatchMapping("")
	public BaseRsp<AssessMaterial> updateAssessCase(@RequestBody @Validated(Update.class) AssessMateriaUpdateReq assessMateriaUpdateReq) {
		String resourceId = assessMateriaUpdateReq.getResourceId();

		assessCaseService.checkCaseId(assessMateriaUpdateReq.getCaseId());
		assessMaterialService.checkNameRepeat(assessMateriaUpdateReq.getName(), resourceId);
		AssessMaterial assessMaterial = assessMaterialMapper.selectById(resourceId);
		if(null == assessMaterial){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		assessMaterialConfigService.setConfig(assessMaterial);
		if(assessMateriaUpdateReq.getMaterialType().equals(AssessMaterial.MaterialType.EXCEL)) {
			assessFileDataService.parseModelFile(assessMaterial.getResourceId(), 2, assessMaterial.getAttachment());
		}
		assessMaterialMapper.updateById(assessMaterial);
		return new BaseRsp<>(assessMaterial);
	}

}