package com.cyberpeace.cr.tool.controller.web;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.tool.controller.req.ToolShellCodeListReq;
import com.cyberpeace.cr.tool.dao.entity.ToolShellCode;
import com.cyberpeace.cr.tool.service.ToolShellCodeService;
import com.cyberpeace.cr.tool.controller.res.ToolShellCodeRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 工具 shellcode 控制器
 *
 * @author SN
 * @date 2024/11/07
 */
@Api(tags = "前台-资源中心-ShellCode库")
@RestController
@RequestMapping("/cr/tool/shellcode")
@RequiresAuthentication
public class ToolShellcodeController {

	/**
	 * 工具壳代号Service
	 */
	@Autowired
	private ToolShellCodeService toolShellCodeService;

	/**
	 * 获取列表
	 *
	 * @param toolShellCodeListReq 工具壳代号列表请求体
	 * @return {@code BaseRsp<ListRsp<ToolShellCode>> }
	 */
	@GetMapping("/list")
	@ApiOperation("shellcode列表")
	@RequiresPermissions(MenuConstant.WEB_CR_TOOL_SHELLCODE)
	public BaseRsp<ListRsp<ToolShellCode>> getList(
			@Validated ToolShellCodeListReq toolShellCodeListReq) {
		ToolShellCodeRsp toolShellCodeRsp = BeanUtils
				.copy(toolShellCodeListReq, ToolShellCodeRsp.class);
		ListRsp<ToolShellCode> toolShellCodeListRsp = toolShellCodeService
				.selectPage(toolShellCodeListReq, toolShellCodeRsp);
		return new BaseRsp<>(toolShellCodeListRsp);
	}

	/**
	 * 获取详情
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<ToolShellCode> }
	 */
	@GetMapping()
	@ApiOperation("shellcode详情")
	@RequiresPermissions(MenuConstant.WEB_CR_TOOL_SHELLCODE)
	public BaseRsp<ToolShellCode> getDetail(
			@ApiParam(name = "resource_id", value = "查询id", required = true)
			@RequestParam("resource_id") @NotBlank String resourceId) {
		ToolShellCode toolShellCode = toolShellCodeService.getById(resourceId);
		return new BaseRsp<>(toolShellCode);
	}
}
