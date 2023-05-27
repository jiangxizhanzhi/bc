package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.controller.req.CrNoticeReq;
import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrNotice;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrNoticeService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

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
		CrNotice notice = BeanUtils.copy(crNoticeReq, CrNotice.class);
		notice.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		crNoticeService.save(notice);
		crNoticeService.notice(crNoticeReq);
		return new BaseRsp<CrNotice>(notice);
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
		return new BaseRsp<CrNotice>(notice);
	}

	@ApiOperation("删除")
	@PostMapping("/delete")
	@RequiresPermissions(
			value = { MenuConstant.WEB_CR_TEAM_TRAIN, MenuConstant.WEB_CR_RED_VS_BLUE },
			logical = Logical.OR)
	public BaseRsp delete(@RequestBody CrNoticeReq crNoticeReq) {
		crNoticeService.removeById(crNoticeReq.getResourceId());
		return new BaseRsp();
	}

	@ApiOperation("红蓝查询")
	@GetMapping("/rb/list")
	@RequiresPermissions(
			value = { MenuConstant.WEB_CR_TEAM_TRAIN, MenuConstant.WEB_CR_RED_VS_BLUE },
			logical = Logical.OR)
	public BaseRsp<List<CrNoticeRsp>> rblist(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		List<CrNoticeRsp> res = crNoticeService.queryList(resource_id, true);
		return new BaseRsp<List<CrNoticeRsp>>(res);
	}

	@ApiOperation("团队查询")
	@GetMapping("/teamList/list")
	@RequiresPermissions(
			value = { MenuConstant.WEB_CR_TEAM_TRAIN, MenuConstant.WEB_CR_RED_VS_BLUE },
			logical = Logical.OR)
	public BaseRsp<List<CrNoticeRsp>> teamList(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		List<CrNoticeRsp> res = crNoticeService.queryList(resource_id, false);
		return new BaseRsp<List<CrNoticeRsp>>(res);
	}

}
