package com.cyberpeace.cr.tool.controller.admin;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.DateUtil;
import com.cyberpeace.cr.tool.controller.req.DeleteReq;
import com.cyberpeace.cr.tool.controller.req.ToolAddReq;
import com.cyberpeace.cr.tool.controller.req.ToolUpdateReq;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.cr.tool.service.bo.ToolBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Api(tags = "后台-资源中心-工具库")
@RestController
@RequestMapping("/cr/admin/tools")
@RequiresAuthentication
public class ToolController {

	@Autowired
	private ToolService toolService;

	@ApiOperation("工具库列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<ToolBo>> getList(
			@ApiParam(name = "listReq", value = "查询条件") @Validated PageReqBase pageReqBase) {
		ListRsp<ToolBo> toolBoListRsp = toolService.selectPage(pageReqBase);
		return new BaseRsp<>(toolBoListRsp);
	}

	@ApiOperation("工具详情")
	@GetMapping()
	public BaseRsp<ToolBo> getDetail(
			@ApiParam(name = "resource_id", value = "查询id", required = true)
			@RequestParam("resource_id") @NotBlank String resourceId) {
		ToolBo toolBo = toolService.selectById(resourceId);
		return new BaseRsp<>(toolBo);
	}

	@ApiOperation("工具创建")
	@PostMapping()
	public BaseRsp<String> create(@RequestBody ToolAddReq toolReq) {
		Tool tool = BeanUtils.copy(toolReq, Tool.class);
		if (!StringUtils.isBlank(toolReq.getPublishTime())) {
			Date date = DateUtil.StringToDate(toolReq.getPublishTime(), DateUtil.SHOT_PATTERN);
			tool.setPublishTime(date);
		}
		tool.setImageResourceId(
				toolReq.getImageResourceId() == null ? toolReq.getImageResourceId() : "");
		toolService.save(tool);
		return new BaseRsp<>();
	}

	@ApiOperation("工具更新")
	@PatchMapping()
	public BaseRsp<String> update(@RequestBody ToolUpdateReq toolupdateReq) {
		Tool tool = BeanUtils.copy(toolupdateReq, Tool.class);
		if (!StringUtils.isBlank(toolupdateReq.getPublishTime())) {
			Date date = DateUtil.StringToDate(toolupdateReq.getPublishTime(), DateUtil.SHOT_PATTERN);
			tool.setPublishTime(date);
		}
		toolService.updateById(tool);
		return new BaseRsp<>();
	}

	@ApiOperation("工具删除")
	@DeleteMapping()
	public BaseRsp<String> batchRemove(@RequestBody @Validated DeleteReq deleteReq) {
		toolService.removeByIds(deleteReq.getIds());
		return new BaseRsp<>();
	}

}
