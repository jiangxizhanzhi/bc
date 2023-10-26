package com.cyberpeace.cr.crbase.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.controller.req.CrNoticeReq;
import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrNotice;
import com.cyberpeace.cr.crbase.service.CrNoticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "公告")
@RestController
@Slf4j
@RequestMapping("/cr/notice")
public class CrNoticeController {

	@Autowired
	private CrNoticeService crNoticeService;

	@ApiOperation("新增")
	@PostMapping()
	@RequiresPermissions(
			value = { MenuConstant.WEB_CR_TEAM_TRAIN, MenuConstant.WEB_CR_RED_VS_BLUE },
			logical = Logical.OR)
	public BaseRsp<CrNotice> add(@RequestBody CrNoticeReq crNoticeReq) {
		crNoticeService.notice(crNoticeReq);
		return new BaseRsp<>();
	}

	@ApiOperation("置顶取消置顶")
	@PostMapping("/top")
	@RequiresPermissions(
			value = { MenuConstant.WEB_CR_TEAM_TRAIN, MenuConstant.WEB_CR_RED_VS_BLUE },
			logical = Logical.OR)
	public BaseRsp<CrNotice> updateTop(@RequestBody CrNoticeReq crNoticeReq) {
		CrNotice notice = crNoticeService.getById(crNoticeReq.getResourceId());
		notice.setIsTop(crNoticeReq.getIsTop());
		crNoticeService.updateById(notice);
		return new BaseRsp<>(notice);
	}

	@ApiOperation("删除")
	@PostMapping("/delete")
	@RequiresPermissions(
			value = { MenuConstant.WEB_CR_TEAM_TRAIN, MenuConstant.WEB_CR_RED_VS_BLUE },
			logical = Logical.OR)
	public BaseRsp<Boolean> delete(@RequestBody CrNoticeReq crNoticeReq) {
		crNoticeService.remveAndNotice(crNoticeReq.getResourceId());
		return new BaseRsp<>();
	}

	@ApiOperation("红蓝查询")
	@GetMapping("/rb/list")
	@RequiresPermissions(
			value = { MenuConstant.WEB_CR_TEAM_TRAIN, MenuConstant.WEB_CR_RED_VS_BLUE },
			logical = Logical.OR)
	public BaseRsp<List<CrNoticeRsp>> rblist(@ApiParam(name = "resource_id", value = "团队训练id",
			required = true) @RequestParam(value = "resource_id") String resourceId) {
		List<CrNoticeRsp> res = crNoticeService.queryList(resourceId, true);
		return new BaseRsp<>(res);
	}

	@ApiOperation("查询是否有新消息")
	@GetMapping("/hasNewInfo")
	@RequiresPermissions(
			value = { MenuConstant.WEB_CR_TEAM_TRAIN, MenuConstant.WEB_CR_RED_VS_BLUE },
			logical = Logical.OR)
	public BaseRsp<Boolean> hasNewInfo(
			@ApiParam(name = "resource_id", value = "团队训练id",
					required = true) @RequestParam(value = "resource_id") String resourceId,
			@ApiParam(name = "is_rb", value = "是否红蓝",
					required = true) @RequestParam(value = "is_rb") Boolean isRb) {
		Boolean res = crNoticeService.hasNewInfo(resourceId, isRb);
		return new BaseRsp<>(res);
	}


	@ApiOperation("团队查询")
	@GetMapping("/teamList/list")
	@RequiresPermissions(
			value = { MenuConstant.WEB_CR_TEAM_TRAIN, MenuConstant.WEB_CR_RED_VS_BLUE },
			logical = Logical.OR)
	public BaseRsp<List<CrNoticeRsp>> teamList(
			@ApiParam(name = "resource_id", value = "团队训练id",
					required = true) @RequestParam(value = "resource_id") String resourceId) {
		List<CrNoticeRsp> res = crNoticeService.queryList(resourceId, false);
		return new BaseRsp<>(res);
	}

}
