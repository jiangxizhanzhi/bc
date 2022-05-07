package com.cyberpeace.rtks.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.rtks.service.AttackService;
import com.cyberpeace.rtks.service.bo.career.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "rtks-职业管理")
@RestController
@RequestMapping("/career/framework")
@RequiresAuthentication
public class CareerManagementController {

	@Autowired
	private AttackService attackService;

	@ApiOperation("ATT&CK框架后台")
	@GetMapping("/attack/all")
	@RequiresPermissions(MenuConstant.CMS_ATTACK_FRAME)
	public BaseRsp<List<AttackCategoryBo<AttackBackBo>>> getAttacksBack() {
		List<AttackCategoryBo<AttackBackBo>> attacks = attackService.getAttacks();
		return new BaseRsp<>(attacks);
	}

	@ApiOperation("ATT&CK详情页")
	@GetMapping("/attack")
	@RequiresPermissions(MenuConstant.CMS_ATTACK_FRAME)
	public BaseRsp<AttackDetail>
			getDetail(@ApiParam(name = "resource_id", value = "技术id") String resource_id) {
		AttackDetail res = attackService.getDetailById(resource_id);

		return new BaseRsp<>(res);
	}

	@ApiOperation("添加ATT&CK 关联")
	@PostMapping("/attack")
	@RequiresPermissions(MenuConstant.CMS_ATTACK_FRAME)
	public BaseRsp updateTemplateRel(@Validated AttackUpdateReq updateReq) {
		attackService.updateRelations(updateReq.getResourceId(),
				updateReq.getBusinessTemplateIds());
		return new BaseRsp();
	}

	@ApiOperation("ATT&CK 框架前台")
	@GetMapping("/attack/related")
	@RequiresPermissions(MenuConstant.CMS_ATTACK_FRAME)
	public BaseRsp<List<AttackCategoryBo<AttackFrontBo>>>
			getAttacksFront(@ApiParam(name = "resource_id", value = "训练模板id") String resource_id) {
		List<AttackCategoryBo<AttackFrontBo>> rsp = attackService.getAttacksByTraining(resource_id);
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("Engage框架")
	@GetMapping("/engage")
	@RequiresPermissions(MenuConstant.CMS_ATTACK_FRAME)
	public BaseRsp getEngages() {
		return new BaseRsp();
	}
}
