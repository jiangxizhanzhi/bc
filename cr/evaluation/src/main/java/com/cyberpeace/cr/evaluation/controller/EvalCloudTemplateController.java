package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.evaluation.controller.req.*;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "后台-云场景模板")
@RestController
@RequestMapping("/cr/eval/cloud/template")
@RequiresAuthentication
public class EvalCloudTemplateController {

	@Autowired
	private EvalCloudTemplateService evalCloudTemplateService;


	@ApiOperation("新增云场景模板")
	@PostMapping("/")
	public BaseRsp<Boolean> addCloudTemplate(@RequestBody EvalCloudTemplateAddReq evalCloudTemplateAddReq) {
		evalCloudTemplateService.addCloudTemplate(evalCloudTemplateAddReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("修改云场景模板")
	@PutMapping("/")
	public BaseRsp<Boolean> modifyCloudTemplate(@RequestBody EvalCloudTemplateAddReq evalCloudTemplateAddReq) {
		evalCloudTemplateService.modifyCloudTemplate(evalCloudTemplateAddReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("删除云场景模板")
	@DeleteMapping("/")
	public BaseRsp<Boolean> deleteCloudTemplate(@RequestBody DeleteReq deleteReq) {
		evalCloudTemplateService.deleteCloudTemplate(deleteReq.getIds());
		return new BaseRsp<>(true);
	}

	@ApiOperation("分页查询云场景模板")
	@GetMapping("/page")
	public BaseRsp<ListRsp<EvalCloudTemplateListRsp>> getCloudTemplateList(EvalCloudTemplateListReq evalCloudTemplateListReq) {
		List<EvalCloudTemplateListRsp> evalCloudTemplateListRspList = evalCloudTemplateService.getCloudTemplateList(evalCloudTemplateListReq);
		return new BaseRsp<>(PageUtil.getListResult(evalCloudTemplateListRspList, evalCloudTemplateListReq.toPageParam(), evalCloudTemplateListRspList.size()));
	}

	@ApiOperation("查询云场景模板详情")
	@GetMapping("")
	public BaseRsp<EvalCloudTemplateAddReq> getCloudTemplateDetail(@ApiParam(name = "cloud_template_id", value = "云模板id") @RequestParam("cloud_template_id") String cloudTemplateId) {
		return new BaseRsp<>(evalCloudTemplateService.getCloudTemplate(cloudTemplateId));
	}

	@ApiOperation("获取云模板基础信息相关枚举")
	@GetMapping("/enum")
	public BaseRsp<EvalCloudTemplateEnumRsp> getCloudTemplateEnum() {
		return new BaseRsp<>(evalCloudTemplateService.getCloudTemplateEnum());
	}

}
