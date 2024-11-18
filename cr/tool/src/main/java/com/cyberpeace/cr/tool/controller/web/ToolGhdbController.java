package com.cyberpeace.cr.tool.controller.web;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.tool.controller.req.ToolGhdbListReq;
import com.cyberpeace.cr.tool.dao.entity.ToolGhdb;
import com.cyberpeace.cr.tool.service.ToolGhdbService;
import com.cyberpeace.cr.tool.controller.res.ToolGhdbRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工具 ghdb 控制器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Api(tags = "前台-资源中心-ghdb")
@RestController
@RequestMapping("/cr/tool/ghdb")
@RequiresAuthentication
public class ToolGhdbController {

	/**
	 * 工具GHDB 数据库Service
	 *
	 * @see ToolGhdbService
	 */
	@Autowired
	private ToolGhdbService toolGhdbService;

	/**
	 * 列表
	 *
	 * @param toolGhdbListReq 工具GHDB 数据库列表请求体
	 * @return {@code BaseRsp<ListRsp<ToolGhdb>> }
	 * @throws
	 * @see ToolGhdbListReq
	 * @see BaseRsp<ListRsp<ToolGhdb>>
	 */
	@ApiOperation("ghdb列表")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.WEB_CR_TOOL_GHDB)
	public BaseRsp<ListRsp<ToolGhdb>> list(@Validated ToolGhdbListReq toolGhdbListReq) {
		ToolGhdbRsp toolGhdbRsp = BeanUtils.copy(toolGhdbListReq, ToolGhdbRsp.class);
		ListRsp<ToolGhdb> data = toolGhdbService.listGhdb(toolGhdbListReq, toolGhdbRsp);
		return new BaseRsp<>(data);
	}

	/**
	 * 详情
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<ToolGhdb> }
	 * @throws
	 * @see String
	 * @see BaseRsp<ToolGhdb>
	 */
	@ApiOperation("ghdb详情")
	@GetMapping()
	@RequiresPermissions(MenuConstant.WEB_CR_TOOL_GHDB)
	public BaseRsp<ToolGhdb> detail(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		ToolGhdb toolGhdb = toolGhdbService.getById(resource_id);

		return new BaseRsp<>(toolGhdb);
	}
}
