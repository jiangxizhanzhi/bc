package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.req.*;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalMissionTemplateRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemTemplateListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemTemplateRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTool;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalToolMapper;
import com.cyberpeace.cr.evaluation.service.EvalMissionTemplateService;
import com.cyberpeace.cr.evaluation.service.EvalTemTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "靶场测评-后台模板")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/template/template/")
public class EvalTemTemplateController {

	@Autowired
	private EvalMissionTemplateService evalMissionTemplateService;

	@Autowired
	private EvalTemTemplateService evalTemTemplateService;

	@Autowired
	private EvalToolMapper evalToolMapper;

	@ApiOperation("新增任务")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
	@PostMapping("/mission")
	public BaseRsp<EvalMissionTemplateRsp> createMission(@RequestBody @Validated EvalMissionTemplateReq evalMissionTemplateReq) {
		String resourceId = evalMissionTemplateService.addMissionTemplate(evalMissionTemplateReq);
		return new BaseRsp<>(evalMissionTemplateService.getMissionTemplate(resourceId));
	}

	@ApiOperation("修改任务")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
	@PatchMapping("/mission")
	public BaseRsp<EvalMissionTemplateRsp> updateMission(@RequestBody @Validated UpdateEvalMissionTemplateReq updateEvalMissionReq) {
		evalMissionTemplateService.updateMissionTemplate(updateEvalMissionReq);
		return new BaseRsp<>(evalMissionTemplateService.getMissionTemplate(updateEvalMissionReq.getResourceId()));
	}

	@ApiOperation("查询任务信息")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
	@GetMapping("/mission")
	public BaseRsp<EvalMissionTemplateRsp> getMission(@ApiParam(name = "resource_id", required = true, value = "资源id") @RequestParam(value = "resource_id") String resourceId) {
		return new BaseRsp<>(evalMissionTemplateService.getMissionTemplate(resourceId));
	}

	@ApiOperation("靶场测评后台模板创建")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
	@PostMapping("/")
	public BaseRsp<String> createTemplate(@RequestBody EvalTemTemplateReq evalTemTemplateReq) {
		return new BaseRsp<>(evalTemTemplateService.createTemplate(evalTemTemplateReq));
	}

	@ApiOperation("靶场测评后台模板更新")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
	@PatchMapping("/")
	public BaseRsp<Boolean> updateTemplate(@RequestBody UpdateEvalTemTemplateReq updateEvalTemTemplateReq) {
		evalTemTemplateService.updateTemplate(updateEvalTemTemplateReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("靶场测评后台模板删除")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
	@DeleteMapping("/")
	public BaseRsp<Boolean> deleteTemplate(@RequestBody DeleteReq deleteReq) {
		evalTemTemplateService.deleteTemplates(deleteReq.getIds());
		return new BaseRsp<>(true);
	}

	@ApiOperation("靶场测评后台模板查询")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
	@GetMapping("/")
	public BaseRsp<EvalTemTemplateRsp> getTemplate(@ApiParam(name = "resource_id", required = true, value = "模板id") @RequestParam(value = "resource_id") String resourceId) {
		return new BaseRsp<>(evalTemTemplateService.getTemplate(resourceId));
	}

	@ApiOperation("查询靶场测评后台模板列表")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
	@GetMapping("/list")
	public BaseRsp<ListRsp<EvalTemTemplateListRsp>> getTemplateList(@ApiParam(name = "listReq", value = "查询条件") @Validated EvalTemTemplatePageReq pageReqBase) {
		return new BaseRsp<>(evalTemTemplateService.getTemplateList(pageReqBase));
	}

	@ApiOperation("查询工具列表")
	@GetMapping("/tool")
	public BaseRsp<List<EvalTool>> getTool() {
		return new BaseRsp<>(evalToolMapper.selectList(new LambdaQueryWrapper<>()));
	}

}
