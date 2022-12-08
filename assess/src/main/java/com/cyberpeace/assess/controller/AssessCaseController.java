package com.cyberpeace.assess.controller;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import com.cyberpeace.assess.controller.res.AssessCaseListRes;
import com.cyberpeace.assess.dao.entity.AssessMaterial;
import com.cyberpeace.assess.dao.entity.AssessMaterialConfig;
import com.cyberpeace.assess.dao.mapper.AssessMaterialConfigMapper;
import com.cyberpeace.assess.dao.mapper.AssessMaterialMapper;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.integratation.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Slf4j
@Api(tags = "评估案例")
@RestController
@RequestMapping("/assess/case")
@RequiresAuthentication
@RequiresPermissions(MenuConstant.CMS_ASSESS_EVALUATE_CASE_DATA_MANAGE)
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

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private AssessMaterialConfigMapper assessMaterialConfigMapper;

	@Autowired
	private RemoteUserService remoteUserService;


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

		AssessMaterialConfig assessMaterialConfig = new AssessMaterialConfig();
		List<String> objects = new ArrayList<>();
		String array = JsonUtil.arrayToString(objects);
		assessMaterialConfig.setMaterialId(array);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			public void doInTransactionWithoutResult(TransactionStatus status) {
				assessCaseMapper.insert(assessCase);
				assessMaterialConfig.setCaseId(assessCase.getResourceId());
				assessMaterialConfigMapper.insert(assessMaterialConfig);
			}
		});


		return new BaseRsp<>(assessCase);
	}

	@ApiOperation("查询list")
	@GetMapping("/list")
	public BaseRsp<List<AssessCaseListRes>>
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
		List<AssessCase> records = assessCases.getRecords();
		LambdaQueryWrapper<AssessMaterial> assessMaterialLambdaQueryWrapper = new LambdaQueryWrapper<>();
		List<AssessMaterial> assessMaterials = assessMaterialMapper.selectList(assessMaterialLambdaQueryWrapper);
		Map<String, List<AssessMaterial>> assessMaterialMap = assessMaterials.stream().collect(Collectors.groupingBy(AssessMaterial::getCaseId));

		List<AssessTag> assessTags = assessTagMapper.selectList(null);

		List<AssessCase> collect = records.stream().sorted(Comparator.comparing(AssessCase::getCreateTime).reversed()).collect(Collectors.toList());
		assessCases.setRecords(collect);
		assessCases.convert(assessCase -> {
			AssessCaseListRes assessCaseListRes = new AssessCaseListRes();
			BeanUtils.copyProperties(assessCase, assessCaseListRes);
			String tags = assessCase.getTag();
			String tagStr = assessCaseService.getTagContent(tags, assessTags);
			int materialCount = 0;
			assessCaseListRes.setTag(tagStr);
			if(!assessMaterialMap.isEmpty()){
				List<AssessMaterial> materials = assessMaterialMap.get(assessCase.getResourceId());
				if(null != materials){
					materialCount = materials.size();
				}
			}
			assessCaseListRes.setMaterialCount(materialCount);
			return assessCaseListRes;

		});
		return new BaseRsp(PageUtil.getListResult(assessCases));
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

		List<AssessTag> assessTags = assessTagMapper.selectList(null);
		String tagStr = assessCaseService.getTagContent(tags, assessTags);
		assessCaseDetailRes.setTagName(tagStr);

		LambdaQueryWrapper<AssessMaterial> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(AssessMaterial::getCaseId, resourceId);
		List<AssessMaterial> assessMaterials = assessMaterialMapper.selectList(queryWrapper);
		assessCaseDetailRes.setAttachments(assessMaterials);
		List<UserInfo> allUsers = remoteUserService.getAllUsers();
		Map<String, String> userMap = allUsers.stream().collect(Collectors.toMap(UserInfo::getUserId, UserInfo::getName));
		String createUserId = assessCase.getCreateUserId();
		if(!userMap.isEmpty()){
			String userName = userMap.get(createUserId);
			assessCaseDetailRes.setCreateUser(userName);
		}
		assessCaseDetailRes.setMaterialCount(0);
		if(!assessMaterials.isEmpty()){
			assessCaseDetailRes.setMaterialCount(assessMaterials.size());
		}
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
		assessCase.setUpdateTime(new Date());
		assessCaseMapper.updateById(assessCase);
		return new BaseRsp<>(assessCase);
	}

	@ApiOperation("导出评估案例")
	@GetMapping("/dump")
	public BaseRsp<String> dump(HttpServletResponse response, @ApiParam(name = "resource_id",
			value = "评估案例id", required = true) @RequestParam(value = "resource_id") @NotNull String resourceId) {
		LambdaQueryWrapper<AssessCase> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(AssessCase::getResourceId, resourceId);
		AssessCase assessCase = assessCaseMapper.selectOne(queryWrapper);
		if(null == assessCase){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		String assessCaseName = assessCase.getName();


		// 1. generate dump file
		String tempDir = AttachmentPath.ASSESS_DUMP.getDesc() + File.separator + assessCase.getResourceId();
		try {
			String zipPath = assessCaseService.makeDumpFile(resourceId, assessCase);
			// 2. generate file response
//			File zip = new File(zipPath);
			return new BaseRsp<>(zipPath);
		} catch (Exception e) {
			log.error("failed to dump business template", e);
			throw new BusinessException("dump material failed");
		}
	}

}