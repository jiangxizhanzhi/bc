package com.cyberpeace.bs.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.bs.controller.req.IdListReq;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.bo.behavior.BehaviorPageReq;
import com.cyberpeace.bs.service.bo.behavior.LcBehavior;
import com.cyberpeace.bs.service.bo.variable.VariableListBo;
import com.cyberpeace.bs.service.bo.variable.VariableModifyReq;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "行为模拟-行为管理")
@RestController
@RequestMapping("/bs/behavior")
// @RequiresAuthentication
public class BehaviorController {

	@Autowired
	LcService lcService;

	@ApiOperation("获取行为列表")
	@GetMapping("/page")
	public BaseRsp<ListRsp<LcBehavior>> pageBehavior(BehaviorPageReq pageReq) {
		ListRsp<LcBehavior> behaviorListRsp = lcService.getLcBehaviorList(pageReq);
		return new BaseRsp<>(behaviorListRsp);
	}

	@ApiOperation("获取变量列表")
	@GetMapping("/variable")
	public BaseRsp<VariableListBo> getVariables(@ApiParam(value = "行为id", name = "id") String id) {
		VariableListBo res = lcService.getVariableList(id);
		return new BaseRsp(res);
	}

	@ApiOperation("编辑行为变量")
	@PutMapping("/variable")
	public BaseRsp updateVariables(@Validated @RequestBody VariableModifyReq req) {
		lcService.updateVariable(req);
		return new BaseRsp();
	}

	@ApiOperation("批量删除行为接口")
	@DeleteMapping("/")
	public BaseRsp<Boolean> deleteBehavior(@Validated @RequestBody IdListReq req) {
		Boolean rsp = lcService.deleteBehavior(req.getIds());
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("导出行为")
	@PostMapping("/export")
	public void exportFile(HttpServletResponse response, @Validated @RequestBody IdListReq req) {
		lcService.exportBehavior(response, req.getIds());
	}

	@ApiOperation("导入行为")
	@PutMapping("/import")
	public BaseRsp<String> importBehavior(@RequestParam("file") MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new BusinessException(ErrorCode.EMPTY_FILE);
		}
		String rsp = lcService.importBehavior(file);
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("获取行为录制跳转host")
	@GetMapping("/host")
	public BaseRsp<String> getLcHost() {
		String host = lcService.getLcHost();
		return new BaseRsp<>(host);
	}

}
