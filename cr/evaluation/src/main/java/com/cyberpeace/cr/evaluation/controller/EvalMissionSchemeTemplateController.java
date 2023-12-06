package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeStage;
import com.cyberpeace.cr.evaluation.controller.req.EvalMissionSchemeTemplateReq;
import com.cyberpeace.cr.evaluation.controller.req.UpdateEvalMissionSchemeTemplateReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalMissionSchemeTemplate;
import com.cyberpeace.cr.evaluation.service.EvalMissionSchemeTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "靶场测评-技战法")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/mission/template/")
@Slf4j
public class EvalMissionSchemeTemplateController {

	@Autowired
	private EvalMissionSchemeTemplateService evalMissionSchemeTemplateService;

	@ApiOperation("新增技战法")
	@PostMapping("")
	public BaseRsp<String> addEvalMissionSchemeTemplate(@RequestBody @Validated EvalMissionSchemeTemplateReq evalMissionSchemeTemplateReq) {
		EvalMissionSchemeTemplate evalMissionSchemeTemplate = BeanUtils.copy(evalMissionSchemeTemplateReq, EvalMissionSchemeTemplate.class);
		evalMissionSchemeTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		evalMissionSchemeTemplate.setTemplateData(JsonUtil.toJsonString(evalMissionSchemeTemplateReq.getContent()));
		evalMissionSchemeTemplateService.addEvalMissionSchemeTemplate(evalMissionSchemeTemplate);
		return new BaseRsp<>(evalMissionSchemeTemplate.getResourceId());
	}

	@ApiOperation("查看技战法详情")
	@GetMapping("")
	public BaseRsp<EvalMissionSchemeTemplateReq> getEvalMissionSchemeTemplate(@ApiParam(name = "资源id", value = "resource_id", required = true) @RequestParam("resource_id") String resourceId) {
		EvalMissionSchemeTemplate evalMissionSchemeTemplate = evalMissionSchemeTemplateService.getEvalMissionSchemeTemplate(resourceId);
		return new BaseRsp<>(entityToReq(evalMissionSchemeTemplate));
	}

	@ApiOperation("删除技战法")
	@DeleteMapping("")
	public BaseRsp<Boolean> deleteEvalMissionSchemeTemplate(@RequestBody DeleteReq deleteReq) {
		evalMissionSchemeTemplateService.deleteEvalMissionSchemeTemplate(deleteReq.getIds());
		return new BaseRsp<>(true);
	}

	@ApiOperation("修改技战法")
	@PatchMapping("")
	public BaseRsp<Boolean> updateEvalMissionSchemeTemplate(@RequestBody @Validated UpdateEvalMissionSchemeTemplateReq updateEvalMissionSchemeTemplateReq) {
		EvalMissionSchemeTemplate evalMissionSchemeTemplate = BeanUtils.copy(updateEvalMissionSchemeTemplateReq, EvalMissionSchemeTemplate.class);
		evalMissionSchemeTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		evalMissionSchemeTemplate.setTemplateData(JsonUtil.toJsonString(updateEvalMissionSchemeTemplateReq.getContent()));
		evalMissionSchemeTemplateService.updateEvalMissionSchemeTemplate(evalMissionSchemeTemplate);
		return new BaseRsp<>(true);
	}

	public EvalMissionSchemeTemplateReq entityToReq(EvalMissionSchemeTemplate evalMissionSchemeTemplate) {
		EvalMissionSchemeTemplateReq evalMissionSchemeTemplateReq = BeanUtils.copy(evalMissionSchemeTemplate, EvalMissionSchemeTemplateReq.class);
		evalMissionSchemeTemplateReq.setContent(JsonUtil.toJsonObjectArray(evalMissionSchemeTemplate.getTemplateData(), MissionSchemeStage.class));
		return evalMissionSchemeTemplateReq;
	}
}
