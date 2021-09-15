package com.cyberpeace.cr.tool.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.tool.controller.req.ToolGhdbListReq;
import com.cyberpeace.cr.tool.dao.entity.ToolGhdb;
import com.cyberpeace.cr.tool.service.ToolGhdbService;
import com.cyberpeace.cr.tool.service.bo.GhdbQueryContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "资源中心-ghdb")
@RestController
@RequestMapping("/cr/tool/")
@RequiresAuthentication
public class ToolGhdbController {

	@Autowired
	private ToolGhdbService toolGhdbService;

	@ApiOperation("分页查询")
	@GetMapping("/ghdbs")
	public BaseRsp<ListRsp<ToolGhdb>> list(@Validated ToolGhdbListReq toolGhdbListReq) {
		GhdbQueryContext ghdbQueryContext = BeanUtils.copy(toolGhdbListReq, GhdbQueryContext.class);
		ListRsp<ToolGhdb> data = toolGhdbService.listGhdb(toolGhdbListReq, ghdbQueryContext);
		return new BaseRsp<>(data);
	}

	@ApiOperation("详情")
	@GetMapping("/ghdb")
	public BaseRsp<ToolGhdb> detail(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		ToolGhdb toolGhdb = toolGhdbService.getById(resource_id);

		return new BaseRsp<>(toolGhdb);
	}
}
