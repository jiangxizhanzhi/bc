package com.cyberpeace.assess.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.cyberpeace.assess.controller.req.*;
import com.cyberpeace.assess.controller.res.AssessMaterialExcelDatalRes;
import com.cyberpeace.assess.dao.entity.AssessMaterialConfig;
import com.cyberpeace.assess.dao.mapper.AssessMaterialConfigMapper;
import com.cyberpeace.assess.dao.mapper.AssessMaterialMapper;
import com.cyberpeace.assess.service.AssessMaterialConfigService;
import com.cyberpeace.assess.service.AssessMaterialService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.assess.dao.entity.AssessMaterial;
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
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "评估材料")
@RestController
@RequestMapping("/assess/material")
//@RequiresAuthentication
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

	@ApiOperation("新增")
	@PostMapping("")
	public BaseRsp<AssessMaterial>
			setAssessCase(AssessMaterialAddReq assessMaterialAddReq) {

		String name = assessMaterialAddReq.getName();
		MultipartFile attachment = assessMaterialAddReq.getAttachment();

		assessMaterialService.checkParams(name, attachment);

		AssessMaterial assessMaterial = BeanUtils.copy(assessMaterialAddReq,
				AssessMaterial.class);
		AuthContext auth = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);
		assessMaterial.setCreateUserId(auth.getUserId());
		assessMaterial = assessMaterialService.checkAssessMaterial(attachment, assessMaterial);

		String uuid = String.valueOf(UUID.randomUUID());
		assessMaterial.setResourceId(uuid);

		AssessMaterialConfig assessMaterialConfig = assessMaterialConfigService.setConfig(assessMaterial);

		// 执行事务存储
		AssessMaterial finalAssessMaterial = assessMaterial;
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				assessMaterialMapper.insert(finalAssessMaterial);
				assessMaterialConfigMapper.updateById(assessMaterialConfig);
			}
		});

		return new BaseRsp<>(assessMaterial);
	}

	@ApiOperation("查询list")
	@GetMapping("/list")
	public BaseRsp<List<AssessMaterial>>
			getAssessCases(AssessMateriaFilterReq assessMateriaFilterReq) {
		LambdaQueryWrapper<AssessMaterial> queryWrapper = new LambdaQueryWrapper<>();
		String name = assessMateriaFilterReq.getName();
		if (StringUtils.isNotBlank(name)){
			queryWrapper.like(AssessMaterial::getName, name);

		}
		List<AssessMaterial> assessCases = assessMaterialMapper.selectList(queryWrapper);
		return new BaseRsp<>(assessCases);
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
	public BaseRsp<AssessMaterial> getAssessCase(@ApiParam(name = "resource_id",
			value = "评估案例id", required = true) @RequestParam(value = "resource_id") @NotNull String resourceId) {
		AssessMaterial assessMaterial = assessMaterialMapper.selectById(resourceId);
		if(null == assessMaterial){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(assessMaterial);
	}

	@ApiOperation("修改")
	@PatchMapping("")
	public BaseRsp<AssessMaterial> updateAssessCase(AssessMateriaUpdateReq assessMateriaUpdateReq) {
		String resourceId = assessMateriaUpdateReq.getResourceId();
		AssessMaterial assessMaterial = assessMaterialMapper.selectById(resourceId);
		if(null == assessMaterial){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		String name = assessMateriaUpdateReq.getName();
		MultipartFile attachment = assessMateriaUpdateReq.getAttachment();

		assessMaterialService.checkParams(name, attachment);

		BeanUtils.copyProperties(assessMateriaUpdateReq, assessMaterial);
		assessMaterial = assessMaterialService.checkAssessMaterial(assessMateriaUpdateReq.getAttachment(), assessMaterial);

		assessMaterialMapper.updateById(assessMaterial);
		return new BaseRsp<>(assessMaterial);
	}

	@ApiOperation("前台表格详情")
	@GetMapping("detail")
	public BaseRsp<AssessMaterial> getAssessCaseDetail(@ApiParam(name = "resource_id",
			value = "评估案例id", required = true) @RequestParam(value = "resource_id") @NotNull String resourceId) {
		AssessMaterial assessMaterial = assessMaterialMapper.selectById(resourceId);
		if(null == assessMaterial){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		String attachment = assessMaterial.getAttachment();
		List<AssessMaterialExcelDatalRes> res = assessMaterialService.getExcelData(attachment);

		return new BaseRsp<>(assessMaterial);
	}

}