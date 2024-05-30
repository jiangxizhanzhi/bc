package com.cyberpeace.cr.tool.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.DateUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.tool.controller.req.DeleteReq;
import com.cyberpeace.cr.tool.controller.req.ToolAddReq;
import com.cyberpeace.cr.tool.controller.req.ToolUpdateReq;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Api(tags = "后台-资源中心-工具库")
@RestController
@RequestMapping("/cr/admin/tools")
@RequiresAuthentication
public class ToolController {

	@Autowired
	private ToolService toolService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private RemoteLogService remoteLogService;

	@ApiOperation("工具库列表")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<ListRsp<ToolRsp>> getList(
			@ApiParam(name = "listReq", value = "查询条件") @Validated PageReqBase pageReqBase) {
		Boolean isAdmin = true;
		Page<Tool> page = pageReqBase.toPageParam();
		ToolListQuery listQuery = BeanUtils.copy(pageReqBase, ToolListQuery.class);
		IPage<ToolRsp> toolBoListRsp = toolService.selectPage(page, listQuery, isAdmin);
		return new BaseRsp<>(PageUtil.getListResult(toolBoListRsp));
	}

	@ApiOperation("工具详情")
	@GetMapping()
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<ToolRsp> getDetail(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam("resource_id") @NotBlank String resourceId) {
		ToolRsp toolRsp = toolService.selectById(resourceId);
		return new BaseRsp<>(toolRsp);
	}

	@ApiOperation("工具创建")
	@PostMapping()
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<String> create(@RequestBody @Validated ToolAddReq toolReq) {
		Tool tool = BeanUtils.copy(toolReq, Tool.class);
		if (tool.getIntroduction() != null && tool.getIntroduction().length() > 65535) {
			throw new BusinessException(CrBusinessErrorCode.DESC_TOO_LONG);
		}

		tool.setCover(utilService.replaceFileUrl(tool.getCover()));
		toolService.validateToolNameExist(tool);
		toolService.save(tool);

		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TOOL,
				OperationType.CREATE, tool.getName());

		return new BaseRsp<>();
	}

	@ApiOperation("工具更新")
	@PatchMapping()
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<String> update(@RequestBody @Validated ToolUpdateReq toolupdateReq) {
		Tool tool = BeanUtils.copy(toolupdateReq, Tool.class);
		if (tool.getIntroduction() != null && tool.getIntroduction().length() > 65535) {
			throw new BusinessException(CrBusinessErrorCode.DESC_TOO_LONG);
		}
		toolService.validateEditToolNameExist(tool);
		toolService.updateById(tool);

		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TOOL,
				OperationType.UPDATE, tool.getName());

		return new BaseRsp<>();
	}

	@ApiOperation("工具删除")
	@DeleteMapping()
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<String> batchRemove(@RequestBody @Validated DeleteReq deleteReq) {

		List<Tool> toolList = toolService.listByIds(deleteReq.getIds());

		toolService.removeByIds(deleteReq.getIds());

		toolList.forEach(tool -> {
			if (tool != null) {
				remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TOOL,
						OperationType.DELETE, tool.getName());
			}
		});

		return new BaseRsp<>();
	}

}
