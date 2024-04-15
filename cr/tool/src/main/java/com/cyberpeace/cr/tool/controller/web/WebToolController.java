package com.cyberpeace.cr.tool.controller.web;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.tool.controller.req.ToolUpdateReq;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.tool.controller.req.WebToolListReq;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import com.cyberpeace.cr.tool.controller.res.WebToolRsp;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.service.ToolService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "前台-资源中心-工具库")
@RestController
@RequestMapping("/cr/tools")
@RequiresAuthentication
public class WebToolController {

	@Autowired
	private ToolService toolService;

	@Autowired
	private RemoteLogService remoteLogService;

	@ApiOperation("工具库列表")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.WEB_CR_TOOL_TOOLS)
	public BaseRsp<ListRsp<WebToolRsp>> getList(
			@ApiParam(name = "listReq", value = "查询条件") @Validated WebToolListReq webToolListReq) {

		Boolean isAdmin = false;
		Page<Tool> page = webToolListReq.toPageParam();
		ToolListQuery listQuery = BeanUtils.copy(webToolListReq, ToolListQuery.class);
		if (webToolListReq.getCracked() != null
				&& StringUtils.isNotBlank(webToolListReq.getCracked())) {
			if (webToolListReq.getCracked().equals(Tool.Cracked.TRIAL.getCode())) {
				return new BaseRsp<>(PageUtil.getNullResult());
			}
			listQuery.setCracked(webToolListReq.getCracked().equals(Tool.Cracked.FREE.getCode()));
		}

		IPage<ToolRsp> toolBoListRsp = toolService.selectPage(page, listQuery, isAdmin);
		IPage<WebToolRsp> convert = toolBoListRsp
				.convert(webTool -> BeanUtils.copy(webTool, WebToolRsp.class));

		return new BaseRsp<>(PageUtil.getListResult(convert));
	}

	@ApiOperation("工具详情")
	@GetMapping()
	@RequiresPermissions(MenuConstant.WEB_CR_TOOL_TOOLS)
	public BaseRsp<ToolRsp> getDetail(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam("resource_id") @NotBlank String resourceId) {
		ToolRsp toolRsp = toolService.selectById(resourceId);
		return new BaseRsp<>(toolRsp);
	}

	@ApiOperation("工具下载")
	@PostMapping("/download")
	@RequiresPermissions(MenuConstant.WEB_CR_TOOL_TOOLS)
	public BaseRsp<String> download(@RequestBody @Validated ToolUpdateReq toolupdateReq) {

		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TOOL,
				OperationType.DOWNLOAD, toolupdateReq.getName());

		return new BaseRsp<>();
	}

}
