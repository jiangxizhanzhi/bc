package com.cyberpeace.rtks.controller;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.rtks.service.AttackIcsService;
import com.cyberpeace.rtks.service.AttackService;
import com.cyberpeace.rtks.service.EngageService;
import com.cyberpeace.rtks.service.bo.EngageDetail;
import com.cyberpeace.rtks.service.bo.career.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "rtks-职业管理")
@RestController
@RequestMapping("/career/framework")
@RequiresAuthentication
public class CareerManagementController {

	@Autowired
	private AttackService attackService;

	@Autowired
	private AttackIcsService attackIcsService;

	@Autowired
	private EngageService engageService;

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
			getAttackDetail(@ApiParam(name = "resource_id", value = "技术id") String resource_id) {
		AttackDetail res = attackService.getDetailById(resource_id);

		return new BaseRsp<>(res);
	}

	@ApiOperation("添加ATT&CK 关联")
	@PostMapping("/attack")
	@RequiresPermissions(MenuConstant.CMS_ATTACK_FRAME)
	public BaseRsp<Boolean> updateAttackTemplateRel(@Validated @RequestBody AttackUpdateReq updateReq) {
		attackService.updateRelations(updateReq.getResourceId(),
				updateReq.getBusinessTemplateIds());
		return new BaseRsp<>(true);
	}

	@ApiOperation("ATT&CK 框架前台")
	@GetMapping("/attack/related")
	@RequiresPermissions(MenuConstant.WEB_ALL)
	public BaseRsp<List<AttackCategoryBo<AttackFrontBo>>>
			getAttacksFront(@ApiParam(name = "resource_id", value = "训练模板id") String resource_id) {
		List<AttackCategoryBo<AttackFrontBo>> rsp = attackService.getAttacksByTraining(resource_id);
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("ATT&CK ICS框架后台")
	@GetMapping("/attack_ics/all")
	@RequiresPermissions(MenuConstant.CMS_ATTACK_FRAME)
	public BaseRsp<List<AttackCategoryBo<AttackBackBo>>> getAttackIcsBack() {
		List<AttackCategoryBo<AttackBackBo>> attacks = attackIcsService.getAttacks();
		return new BaseRsp<>(attacks);
	}

	@ApiOperation("ATT&CK ICS详情页")
	@GetMapping("/attack_ics")
	@RequiresPermissions(MenuConstant.CMS_ATTACK_FRAME)
	public BaseRsp<AttackDetail>
			getAttackIcsDetail(@ApiParam(name = "resource_id", value = "技术id") String resource_id) {
		return new BaseRsp<>(attackIcsService.getDetailById(resource_id));
	}

	@ApiOperation("添加ATT&CK ICS关联")
	@PostMapping("/attack_ics")
	@RequiresPermissions(MenuConstant.CMS_ATTACK_FRAME)
	public BaseRsp<Boolean> updateAttackIcsTemplateRel(@Validated @RequestBody AttackUpdateReq updateReq) {
		attackIcsService.updateRelations(updateReq.getResourceId(),
				updateReq.getBusinessTemplateIds());
		return new BaseRsp<>(true);
	}

	@ApiOperation("ATT&CK ICS框架前台")
	@GetMapping("/attack_ics/related")
	@RequiresPermissions(MenuConstant.WEB_ALL)
	public BaseRsp<List<AttackCategoryBo<AttackFrontBo>>> getAttackIcsFront(
			@ApiParam(name = "resource_id", value = "训练模板id") String resource_id) {
		List<AttackCategoryBo<AttackFrontBo>> rsp = attackIcsService
				.getAttacksByTraining(resource_id);
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("Engage框架后台")
	@GetMapping("/engage/all")
	@RequiresPermissions(MenuConstant.CMS_ENGAGE_FRAME)
	public BaseRsp<List<EngageCategoryBo<EngageBackBo>>> getEngagesBack() {
		List<EngageCategoryBo<EngageBackBo>> engages = engageService.getEngages();
		return new BaseRsp<>(engages);
	}

	@ApiOperation("Engage详情页")
	@GetMapping("/engage")
	@RequiresPermissions(MenuConstant.CMS_ENGAGE_FRAME)
	public BaseRsp<EngageDetail>
			getEngageDetail(@ApiParam(name = "resource_id", value = "技术id") String resource_id) {
		EngageDetail res = engageService.getDetailById(resource_id);

		return new BaseRsp<>(res);
	}

	@ApiOperation("添加Engage 关联")
	@PostMapping("/engage")
	@RequiresPermissions(MenuConstant.CMS_ENGAGE_FRAME)
	public BaseRsp<Boolean> updateEngageTemplateRel(@Validated @RequestBody EngageUpdateReq updateReq) {
		engageService.updateRelations(updateReq.getResourceId(),
				updateReq.getBusinessTemplateIds());
		return new BaseRsp<>(true);
	}

	@ApiOperation("Engage 框架前台")
	@GetMapping("/engage/related")
	@RequiresPermissions(MenuConstant.WEB_ALL)
	public BaseRsp<List<EngageCategoryBo<EngageFrontBo>>>
			getEngagesFront(@ApiParam(name = "resource_id", value = "训练模板id") String resource_id) {
		List<EngageCategoryBo<EngageFrontBo>> rsp = engageService.getEngagesByTraining(resource_id);
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("查看ATT&CK树形列表")
	@GetMapping("/attack/tree")
	public BaseRsp<List<AttackEvalSchemeBo>> getAttackTree() {
		List<AttackEvalSchemeBo> attackEvalSchemeBoList = new ArrayList<>();
		List<AttackCategoryBo<FrameItemBase>> attackCategoryBoList = attackService.getCache();
		for (AttackCategoryBo<FrameItemBase> attackCategoryBo : attackCategoryBoList) {
			AttackEvalSchemeBo category = new AttackEvalSchemeBo();
			category.setName(attackCategoryBo.getCategoryName());
			category.setResourceId(attackCategoryBo.getCode());
			category.setChildren(attackCategoryBo.getAttacks());
			attackEvalSchemeBoList.add(category);
		}
		return new BaseRsp<>(attackEvalSchemeBoList);
	}

}
