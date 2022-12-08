package com.cyberpeace.assess.controller;

import com.cyberpeace.assess.controller.req.AssessCaseConfigUpdateRes;
import com.cyberpeace.assess.controller.res.AssessCaseConfigRes;
import com.cyberpeace.assess.service.AssessMaterialConfigService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/assess/material/config")
@Api(tags = "评估材料编排")
@RequiresAuthentication
@RequiresPermissions(MenuConstant.CMS_ASSESS_EVALUATE_CASE_DATA_MANAGE)
public class AssessMaterialConfigController {

	@Autowired
	private AssessMaterialConfigService assessMaterialConfigService;

	@ApiOperation("后台课程目录查询")
	@GetMapping("")
	public BaseRsp<AssessCaseConfigRes> getLessonConfig(@ApiParam(name = "case_id",
			value = "课程编号") @RequestParam("case_id") String caseId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		AssessCaseConfigRes lessonConfig = assessMaterialConfigService.getAssessMaterialConfig(caseId, authContext);
		return new BaseRsp(lessonConfig);
	}

	@ApiOperation("编排课时")
	@PutMapping("")
	public void setLessonConfig(
			@ApiParam(name = "configBo", value = "课程编排信息") @RequestBody @Validated AssessCaseConfigUpdateRes assessCaseConfigUpdateRes) {

		assessMaterialConfigService.updateAssessMaterialConfig(assessCaseConfigUpdateRes);
	}

}
