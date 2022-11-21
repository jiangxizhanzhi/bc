package com.cyberpeace.assess.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.assess.controller.req.AssessCaseAddReq;
import com.cyberpeace.assess.controller.req.AssessCaseFilterReq;
import com.cyberpeace.assess.controller.req.AssessCaseUpdateReq;
import com.cyberpeace.assess.controller.req.BatchDeleteReq;
import com.cyberpeace.assess.dao.entity.AssessCase;
import com.cyberpeace.assess.dao.mapper.AssessCaseMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "评估案例")
@RestController
@RequestMapping("/assess/case")
public class AssessCaseController {

	@Autowired
	private AssessCaseMapper assessCaseMapper;

	@ApiOperation("新增")
	@PostMapping("")
	public BaseRsp<AssessCase>
			setAssessCase(@RequestBody AssessCaseAddReq assessCaseAddReq) {

		AssessCase assessCase = BeanUtils.copy(assessCaseAddReq,
				AssessCase.class);
		AuthContext auth = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);
		assessCase.setCreateUserId(auth.getUserId());
		assessCaseMapper.insert(assessCase);
		return new BaseRsp<>(assessCase);
	}

	@ApiOperation("查询list")
	@GetMapping("/list")
	public BaseRsp<List<AssessCase>>
			getAssessCases(AssessCaseFilterReq assessCaseFilterReq) {
		LambdaQueryWrapper<AssessCase> queryWrapper = new LambdaQueryWrapper<>();
		String name = assessCaseFilterReq.getName();
		if (null != name){
			queryWrapper.like(AssessCase::getName, name);

		}
		List<AssessCase> assessCases = assessCaseMapper.selectList(queryWrapper);
		return new BaseRsp<>(assessCases);
	}

	@ApiOperation("删除")
	@DeleteMapping("")
	public BaseRsp deleteAssessCase(BatchDeleteReq batchDeleteReq) {
		List<String> ids = batchDeleteReq.getIds();
		List<AssessCase> assessCases = assessCaseMapper.selectBatchIds(ids);
		if (!assessCases.isEmpty()) {
			assessCaseMapper.deleteBatchIds(ids);
		}
		return new BaseRsp<>();
	}

	@ApiOperation("详情")
	@GetMapping("")
	public BaseRsp<AssessCase> getAssessCase(@ApiParam(name = "resource_id",
			value = "评估案例id", required = true) @RequestParam(value = "resource_id") @NotNull String resourceId) {
		AssessCase assessCase = assessCaseMapper.selectById(resourceId);
		if(null == assessCase){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(assessCase);
	}

	@ApiOperation("修改")
	@PatchMapping("")
	public BaseRsp<AssessCase> updateAssessCase(@RequestBody AssessCaseUpdateReq assessCaseUpdateReq) {
		String resourceId = assessCaseUpdateReq.getResourceId();
		AssessCase assessCase = assessCaseMapper.selectById(resourceId);
		if(null == assessCase){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		BeanUtils.copyProperties(assessCaseUpdateReq, assessCase);
		assessCaseMapper.updateById(assessCase);
		return new BaseRsp<>(assessCase);
	}
}