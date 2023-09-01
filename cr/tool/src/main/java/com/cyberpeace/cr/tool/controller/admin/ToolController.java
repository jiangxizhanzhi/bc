package com.cyberpeace.cr.tool.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.controller.req.ShareReq;
import com.cyberpeace.cr.crbase.service.context.Share;
import com.cyberpeace.cr.crbase.service.context.ShareContext;
import com.cyberpeace.cr.tool.controller.req.DeleteReq;
import com.cyberpeace.cr.tool.controller.req.ToolAddReq;
import com.cyberpeace.cr.tool.controller.req.ToolUpdateReq;
import com.cyberpeace.cr.tool.controller.req.WebToolListReq;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "后台-资源中心-工具库")
@RestController
@RequestMapping("/cr/admin/tools")
@RequiresAuthentication
public class ToolController {

	@Autowired
	private ToolService toolService;

	@Autowired
	private RemoteLogService remoteLogService;

	@ApiOperation("工具库列表")
	@GetMapping("/list")
	@RequiresPermissions(value = {MenuConstant.CMS_TOOL, MenuConstant.CMS_TOOLS}, logical = Logical.OR)
	public BaseRsp<ListRsp<ToolRsp>> getList(@ApiParam(name = "listReq", value = "查询条件") @Validated WebToolListReq req) {
		Boolean isAdmin = true;
		Page page = req.toPageParam();
		ToolListQuery listQuery = BeanUtils.copy(req, ToolListQuery.class);

		if (req.getSort() != null && req.getSort().equals("name")) {
			req.setSort(null);
			page = req.toPageParam();
		}
		// 获取权限
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),false);
		IPage<ToolRsp> toolBoListRsp = toolService.selectPage(page, listQuery, isAdmin, authContext);

		return new BaseRsp(PageUtil.getListResult(toolBoListRsp));
	}

	@ApiOperation("工具详情")
	@GetMapping()
	@RequiresPermissions(value = {MenuConstant.CMS_TOOL, MenuConstant.CMS_TOOLS}, logical = Logical.OR)
	public BaseRsp<ToolRsp> getDetail(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam("resource_id") @NotBlank String resourceId) {
		ToolRsp toolRsp = toolService.selectById(resourceId);
		return new BaseRsp<>(toolRsp);
	}

	@ApiOperation("工具创建")
	@PostMapping()
	@RequiresPermissions(value = {MenuConstant.CMS_TOOL, MenuConstant.CMS_TOOLS}, logical = Logical.OR)
	public BaseRsp<String> create(@RequestBody @Validated ToolAddReq toolReq) {
		toolService.insert(toolReq);
		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TOOL,
				OperationType.CREATE, toolReq.getName());
		return new BaseRsp<>();
	}

	@ApiOperation("工具更新")
	@PatchMapping()
	@RequiresPermissions(value = {MenuConstant.CMS_TOOL, MenuConstant.CMS_TOOLS}, logical = Logical.OR)
	public BaseRsp<String> update(@RequestBody @Validated ToolUpdateReq toolupdateReq) {
		toolService.update(toolupdateReq);
		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TOOL,
				OperationType.UPDATE, toolupdateReq.getName());

		return new BaseRsp<>();
	}

	@ApiOperation("工具删除")
	@DeleteMapping()
	@RequiresPermissions(value = {MenuConstant.CMS_TOOL, MenuConstant.CMS_TOOLS}, logical = Logical.OR)
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

	/**
	 * 设置靶场业务模板的操作权限
	 * @return
	 */
	@ApiOperation("批量修改操作权限")
	@PostMapping("/batchSetShares")
	@RequiresPermissions(value = {MenuConstant.CMS_TOOL, MenuConstant.CMS_TOOLS}, logical = Logical.OR)
	public BaseRsp<String> batchSetShares(@RequestBody @Validated ShareReq shareReq) {
		ShareContext shareContext = BeanUtils.copy(shareReq, ShareContext.class);
		toolService.batchSetShares(shareContext);
		return new BaseRsp();
	}

	/**
	 * 获取靶场业务模板的操作权限
	 * @return
	 */
	@ApiOperation("查询操作权限")
	@GetMapping("/getShares")
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.CMS_CR_RED_TRAIN,
			MenuConstant.CMS_CR_BLUE_TRAIN, MenuConstant.CMS_DAILY_MODE,
			MenuConstant.CMS_CR_SINGLE_TRAIN }, logical = Logical.OR)
	public BaseRsp<Share> getShares(
			@ApiParam(name = "resource_id", value = "模板id", required = true) String resource_id) {
		Share data = toolService.getShares(resource_id);

		return new BaseRsp(data);
	}

}
