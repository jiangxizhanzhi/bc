package com.cyberpeace.cr.tool.controller;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.tool.controller.req.ToolShellCodeListReq;
import com.cyberpeace.cr.tool.dao.entity.ToolShellCode;
import com.cyberpeace.cr.tool.service.ToolShellCodeService;
import com.cyberpeace.cr.tool.service.bo.ShellCodeContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Api(tags = "资源中心-ShellCode库")
@RestController
@RequestMapping("/cr/tool/shellcode")
@RequiresAuthentication
public class ToolShellcodeController {

	@Autowired
	private ToolShellCodeService toolShellCodeService;

	@GetMapping("/list")
	public BaseRsp<ListRsp<ToolShellCode>> getList(
			@Validated ToolShellCodeListReq toolShellCodeListReq) {
		ShellCodeContext shellCodeContext = BeanUtils
				.copy(toolShellCodeListReq, ShellCodeContext.class);
		ListRsp<ToolShellCode> toolShellCodeListRsp = toolShellCodeService
				.selectPage(toolShellCodeListReq, shellCodeContext);
		return new BaseRsp<>(toolShellCodeListRsp);
	}

	@GetMapping()
	public BaseRsp<ToolShellCode> getDetail(
			@ApiParam(name = "resource_id", value = "查询id", required = true)
			@RequestParam("resource_id") @NotBlank String resourceId) {
		ToolShellCode toolShellCode = toolShellCodeService.getById(resourceId);
		return new BaseRsp<>(toolShellCode);
	}
}
