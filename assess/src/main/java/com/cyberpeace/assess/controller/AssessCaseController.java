package com.cyberpeace.assess.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.cyberpeace.assess.dao.entity.AssessMaterial;
import com.cyberpeace.assess.dao.mapper.AssessMaterialMapper;
import com.cyberpeace.common.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.assess.controller.req.AssessCaseAddReq;
import com.cyberpeace.assess.controller.req.AssessCaseFilterReq;
import com.cyberpeace.assess.controller.req.AssessCaseUpdateReq;
import com.cyberpeace.assess.controller.req.BatchDeleteReq;
import com.cyberpeace.assess.controller.res.AssessCaseDetailRes;
import com.cyberpeace.assess.dao.entity.AssessCase;
import com.cyberpeace.assess.dao.entity.AssessTag;
import com.cyberpeace.assess.dao.mapper.AssessCaseMapper;
import com.cyberpeace.assess.dao.mapper.AssessTagMapper;
import com.cyberpeace.assess.service.AssessCaseService;
import com.cyberpeace.assess.service.AssessTagService;
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
import com.cyberpeace.common.util.JsonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "评估案例")
@RestController
@RequestMapping("/assess/case")
@RequiresAuthentication
public class AssessCaseController {

	@Autowired
	private AssessCaseMapper assessCaseMapper;

	@Autowired
	private AssessCaseService assessCaseService;

	@Autowired
	private AssessTagService assessTagService;

	@Autowired
	private AssessTagMapper assessTagMapper;

	@Autowired
	private AssessMaterialMapper assessMaterialMapper;


	@ApiOperation("新增")
	@PostMapping("")
	public BaseRsp<AssessCase>
			setAssessCase(@RequestBody @Validated(Create.class) AssessCaseAddReq assessCaseAddReq) {

		AssessCase assessCase = BeanUtils.copy(assessCaseAddReq,
				AssessCase.class);

		List<String> tags = assessCaseAddReq.getTag();
		tags = tags.stream().distinct().collect(Collectors.toList());
		if(!tags.isEmpty()){
			assessTagService.checkTagIds(tags);
		}

		AuthContext auth = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);
		assessCase.setCreateUserId(auth.getUserId());
		assessCase.setTag(JsonUtil.arrayToString(tags));

		assessCaseService.checkNameRepeat(assessCaseAddReq.getName(), null);
		assessCaseMapper.insert(assessCase);

		return new BaseRsp<>(assessCase);
	}

	@ApiOperation("查询list")
	@GetMapping("/list")
	public BaseRsp<List<AssessCase>>
			getAssessCases(AssessCaseFilterReq assessCaseFilterReq) {
		LambdaQueryWrapper<AssessCase> queryWrapper = new LambdaQueryWrapper<>();
		String name = assessCaseFilterReq.getName();
		if (StringUtils.isNotBlank(name)){
			queryWrapper.like(AssessCase::getName, name);

		}

		String tag = assessCaseFilterReq.getTag();
		if (StringUtils.isNotBlank(tag)){
			queryWrapper.like(AssessCase::getTag, tag);

		}
		Integer page = assessCaseFilterReq.getPage();
		Integer perPage = assessCaseFilterReq.getPerPage();
		IPage<AssessCase> assessCases = assessCaseMapper.selectPage(new Page<>(page, perPage),queryWrapper);
		LambdaQueryWrapper<AssessTag> assessTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
		List<AssessTag> assessTags = assessTagMapper.selectList(assessTagLambdaQueryWrapper);
		IPage<AssessCase> res = assessCases;

		assessCases.getRecords().forEach(assessCase -> {
			String tags = assessCase.getTag();
			String tagStr = assessCaseService.getTagContent(tags, assessTags);
			assessCase.setTag(tagStr);
		});
		res.setRecords(assessCases.getRecords().stream().sorted(Comparator.comparing(AssessCase::getCreateTime).reversed()).collect(Collectors.toList()));
		return new BaseRsp(PageUtil.getListResult(res));
	}

	@ApiOperation("删除")
	@DeleteMapping("")
	public BaseRsp deleteAssessCase(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		List<String> ids = batchDeleteReq.getIds();
		ids = ids.stream().distinct().collect(Collectors.toList());
		List<AssessCase> assessCases = assessCaseMapper.selectBatchIds(ids);
		if (assessCases.isEmpty() || ids.size() > assessCases.size()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		assessCaseMapper.deleteBatchIds(ids);
		return new BaseRsp<>();
	}

	@ApiOperation("详情")
	@GetMapping("")
	public BaseRsp<AssessCaseDetailRes> getAssessCase(@ApiParam(name = "resource_id",
			value = "评估案例id", required = true) @RequestParam(value = "resource_id") @NotNull String resourceId) {
		AssessCase assessCase = assessCaseMapper.selectById(resourceId);
		if(null == assessCase){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		AssessCaseDetailRes assessCaseDetailRes = new AssessCaseDetailRes();
		BeanUtils.copyProperties(assessCase, assessCaseDetailRes);
		String tags = assessCase.getTag();
		List<String> tagList = JSONObject.parseArray(tags, String.class);
		assessCaseDetailRes.setTag(tagList);
		LambdaQueryWrapper<AssessMaterial> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(AssessMaterial::getCaseId, resourceId);
		List<AssessMaterial> assessMaterials = assessMaterialMapper.selectList(queryWrapper);
		assessCaseDetailRes.setAttachments(assessMaterials);
		return new BaseRsp<>(assessCaseDetailRes);
	}

	@ApiOperation("修改")
	@PatchMapping("")
	public BaseRsp<AssessCase> updateAssessCase(@RequestBody @Validated(Update.class) AssessCaseUpdateReq assessCaseUpdateReq) {
		String resourceId = assessCaseUpdateReq.getResourceId();

		AssessCase assessCase =assessCaseService.checkCaseId(resourceId);

		List<String> tags = assessCaseUpdateReq.getTag();
		tags = tags.stream().distinct().collect(Collectors.toList());
		assessTagService.checkTagIds(tags);
		String name = assessCaseUpdateReq.getName();
		assessCaseService.checkNameRepeat(name, assessCaseUpdateReq.getResourceId());

		BeanUtils.copyProperties(assessCaseUpdateReq, assessCase);
		assessCase.setTag(JsonUtil.arrayToString(tags));
		assessCaseMapper.updateById(assessCase);
		return new BaseRsp<>(assessCase);
	}
}