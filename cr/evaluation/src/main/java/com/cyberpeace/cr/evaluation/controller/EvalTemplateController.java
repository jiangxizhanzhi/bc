package com.cyberpeace.cr.evaluation.controller;

import java.util.List;

import com.cyberpeace.cr.evaluation.controller.req.EvalTemplateDeleteReq;
import org.apache.commons.compress.utils.Lists;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvaTemplateAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvaTemplateModifyReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTemplateListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemplateListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemplateRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "测试靶场-模板")
@RestController
@RequestMapping("/cr/eval/template")
public class EvalTemplateController {

	@ApiOperation("模板新增")
	@PostMapping("")
	public BaseRsp<EvalTemplateRsp>
			addEvalTemplate(@RequestBody EvaTemplateAddReq evaTemplateAddReq) {
		return new BaseRsp<>(new EvalTemplateRsp());
	}

	@ApiOperation("模板列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<EvalTemplateListRsp>>
			getEvalTemplateList(EvalTemplateListReq queryParams) {
		ListRsp<EvalTemplateListRsp> result = new ListRsp<>();
		EvalTemplateListRsp evalRoomListRsp = new EvalTemplateListRsp();
		List<EvalTemplateListRsp> evalRoomListRsps = Lists.newArrayList();
		evalRoomListRsps.add(evalRoomListRsp);
		result.setList(evalRoomListRsps);
		return new BaseRsp<>(result);
	}

	@ApiOperation("模板查询")
	@GetMapping("")
	public BaseRsp<EvalTemplateRsp> getEvalTemplate(
			@ApiParam(name = "template_id", value = "测试靶场id", required = true)
			@RequestParam("template_id") String templateId) {
		return new BaseRsp<>(new EvalTemplateRsp());
	}

	@ApiOperation("模板修改")
	@PutMapping("")
	public BaseRsp<EvalTemplateRsp>
			modifyEvalTemplate(@RequestBody EvaTemplateModifyReq evaTemplateModifyReq) {
		return new BaseRsp<>(new EvalTemplateRsp());
	}

	@ApiOperation("模板删除")
	@DeleteMapping("")
	public BaseRsp<Boolean> deleteEvalTemplate(@RequestBody EvalTemplateDeleteReq evalTemplateDeleteReq) {
		return new BaseRsp<>(true);
	}

}