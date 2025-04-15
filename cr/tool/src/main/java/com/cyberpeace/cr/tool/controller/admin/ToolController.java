package com.cyberpeace.cr.tool.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
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
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 工具Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Api(tags = "后台-资源中心-工具库")
@RestController
@RequestMapping("/cr/admin/tools")
@RequiresAuthentication
public class ToolController {

	/**
	 * 工具Service
	 */
	@Autowired
	private ToolService toolService;

	/**
	 * utilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 远程日志Service
	 */
	@Autowired
	private RemoteLogService remoteLogService;

	/**
	 * 获取列表
	 *
	 * @param pageReqBase 分页请求体基础
	 * @param type        类型
	 * @param tag         标记
	 * @param category    类别
	 * @return {@code BaseRsp<ListRsp<ToolRsp>> }
	 */
	@ApiOperation("工具库列表")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<ListRsp<ToolRsp>> getList(
			@ApiParam(name = "listReq", value = "查询条件") @Validated PageReqBase pageReqBase,
			@ApiParam(name = "type", value = "工具分类")String type,
			@ApiParam(name = "tag", value = "工具标签")String tag,
			@ApiParam(name = "category", value = "工具分类")String category) {
		Boolean isAdmin = true;
		Page page = pageReqBase.toPageParam();
		ToolListQuery listQuery = BeanUtils.copy(pageReqBase, ToolListQuery.class);
		listQuery.setType(type);
		listQuery.setTag(tag);
		listQuery.setCategory(category);
		IPage<ToolRsp> toolBoListRsp = toolService.selectPage(page, listQuery, isAdmin);
		return new BaseRsp(PageUtil.getListResult(toolBoListRsp));
	}

	/**
	 * 获取详情
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<ToolRsp> }
	 */
	@ApiOperation("工具详情")
	@GetMapping()
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<ToolRsp> getDetail(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam("resource_id") @NotBlank String resourceId) {
		ToolRsp toolRsp = toolService.selectById(resourceId);
		return new BaseRsp<>(toolRsp);
	}

	/**
	 * 创造
	 *
	 * @param toolReq 工具请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("工具创建")
	@PostMapping()
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<ToolRsp> create(@RequestBody @Validated ToolAddReq toolReq) {
		Tool tool = BeanUtils.copy(toolReq, Tool.class);

		tool.setCover(utilService.replaceFileUrl(tool.getCover()));
		tool.setTag(JsonUtil.arrayToString(toolReq.getTag()));
		toolService.validateToolNameExist(tool);
		toolService.save(tool);

		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TOOL,
				OperationType.CREATE, tool.getName());

		return this.getDetail(tool.getResourceId());
	}

	/**
	 * 更新
	 *
	 * @param toolupdateReq 工具更新请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("工具更新")
	@PatchMapping()
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<String> update(@RequestBody @Validated ToolUpdateReq toolupdateReq) {
		Tool tool = BeanUtils.copy(toolupdateReq, Tool.class);
		tool.setTag(JsonUtil.arrayToString(toolupdateReq.getTag()));
		toolService.validateEditToolNameExist(tool);
		toolService.updateById(tool);

		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TOOL,
				OperationType.UPDATE, tool.getName());

		return new BaseRsp<>();
	}

	/**
	 * 批量删除
	 *
	 * @param deleteReq 删除请求体
	 * @return {@code BaseRsp<String> }
	 */
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
