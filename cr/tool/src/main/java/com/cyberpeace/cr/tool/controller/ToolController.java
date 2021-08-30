package com.cyberpeace.cr.tool.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.cr.tool.service.bo.ToolBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "工具库")
@RestController
@RequestMapping("/cr/tools")
public class ToolController {

	@Autowired
	private ToolService toolService;

	@ApiOperation("工具库列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<ToolBo>> getList(@ApiParam(name = "listReq", value = "查询条件") @Validated
			PageReqBase pageReqBase) {
		ListRsp<ToolBo> toolBoListRsp = toolService.selectPage(pageReqBase);
		return new BaseRsp<>(toolBoListRsp);
	}
}
