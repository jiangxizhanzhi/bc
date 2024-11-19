package com.cyberpeace.rtks.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

/**
 * 职业管理Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Api(tags = "rtks-职业管理")
@RestController
@RequestMapping("/career/framework")
@RequiresAuthentication
public class CareerManagementController {

	/**
	 * 攻击Service
	 *
	 * @see AttackService
	 */
	@Autowired
	private AttackService attackService;

	/**
	 * 攻击集成电路Service
	 *
	 * @see AttackIcsService
	 */
	@Autowired
	private AttackIcsService attackIcsService;

	/**
	 * EngageService
	 *
	 * @see EngageService
	 */
	@Autowired
	private EngageService engageService;

	/**
	 * 获取攻击返回
	 *
	 * @return {@code BaseRsp<List<AttackCategoryBo<AttackBackBo>>> }
	 * @throws
	 * @see BaseRsp<List<AttackCategoryBo<AttackBackBo>>>
	 */
	@ApiOperation("ATT&CK框架后台")
	@GetMapping("/attack/all")
	@RequiresPermissions(MenuConstant.CMS_ATTACK_FRAME)
	public BaseRsp<List<AttackCategoryBo<AttackBackBo>>> getAttacksBack() {
		List<AttackCategoryBo<AttackBackBo>> attacks = attackService.getAttacks();
		return new BaseRsp<>(attacks);
	}

	/**
	 * 获取攻击详情
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<AttackDetail> }
	 * @throws
	 * @see String
	 * @see BaseRsp<AttackDetail>
	 */
	@ApiOperation("ATT&CK详情页")
	@GetMapping("/attack")
	@RequiresPermissions(MenuConstant.CMS_ATTACK_FRAME)
	public BaseRsp<AttackDetail>
			getAttackDetail(@ApiParam(name = "resource_id", value = "技术id") String resource_id) {
		AttackDetail res = attackService.getDetailById(resource_id);

		return new BaseRsp<>(res);
	}

	/**
	 * 更新攻击模板关联
	 *
	 * @param updateReq 更新请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see AttackUpdateReq
	 * @see BaseRsp<Boolean>
	 */
	@ApiOperation("添加ATT&CK 关联")
	@PostMapping("/attack")
	@RequiresPermissions(MenuConstant.CMS_ATTACK_FRAME)
	public BaseRsp<Boolean> updateAttackTemplateRel(@Validated @RequestBody AttackUpdateReq updateReq) {
		attackService.updateRelations(updateReq.getResourceId(),
				updateReq.getBusinessTemplateIds());
		return new BaseRsp<>(true);
	}

	/**
	 * 获取攻击前面
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<List<AttackCategoryBo<AttackFrontBo>>> }
	 * @throws
	 * @see String
	 * @see BaseRsp<List<AttackCategoryBo<AttackFrontBo>>>
	 */
	@ApiOperation("ATT&CK 框架前台")
	@GetMapping("/attack/related")
	@RequiresPermissions(MenuConstant.WEB_ALL)
	public BaseRsp<List<AttackCategoryBo<AttackFrontBo>>>
			getAttacksFront(@ApiParam(name = "resource_id", value = "训练模板id") String resource_id) {
		List<AttackCategoryBo<AttackFrontBo>> rsp = attackService.getAttacksByTraining(resource_id);
		return new BaseRsp<>(rsp);
	}

	/**
	 * 获取攻击集成电路返回
	 *
	 * @return {@code BaseRsp<List<AttackCategoryBo<AttackBackBo>>> }
	 * @throws
	 * @see BaseRsp<List<AttackCategoryBo<AttackBackBo>>>
	 */
	@ApiOperation("ATT&CK ICS框架后台")
	@GetMapping("/attack_ics/all")
	@RequiresPermissions(MenuConstant.CMS_ATTACK_FRAME)
	public BaseRsp<List<AttackCategoryBo<AttackBackBo>>> getAttackIcsBack() {
		List<AttackCategoryBo<AttackBackBo>> attacks = attackIcsService.getAttacks();
		return new BaseRsp<>(attacks);
	}

	/**
	 * 获取攻击集成电路详情
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<AttackDetail> }
	 * @throws
	 * @see String
	 * @see BaseRsp<AttackDetail>
	 */
	@ApiOperation("ATT&CK ICS详情页")
	@GetMapping("/attack_ics")
	@RequiresPermissions(MenuConstant.CMS_ATTACK_FRAME)
	public BaseRsp<AttackDetail>
			getAttackIcsDetail(@ApiParam(name = "resource_id", value = "技术id") String resource_id) {
		return new BaseRsp<>(attackIcsService.getDetailById(resource_id));
	}

	/**
	 * 更新攻击集成电路模板关联
	 *
	 * @param updateReq 更新请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see AttackUpdateReq
	 * @see BaseRsp<Boolean>
	 */
	@ApiOperation("添加ATT&CK ICS关联")
	@PostMapping("/attack_ics")
	@RequiresPermissions(MenuConstant.CMS_ATTACK_FRAME)
	public BaseRsp<Boolean> updateAttackIcsTemplateRel(@Validated @RequestBody AttackUpdateReq updateReq) {
		attackIcsService.updateRelations(updateReq.getResourceId(),
				updateReq.getBusinessTemplateIds());
		return new BaseRsp<>(true);
	}

	/**
	 * 获取攻击集成电路前面
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<List<AttackCategoryBo<AttackFrontBo>>> }
	 * @throws
	 * @see String
	 * @see BaseRsp<List<AttackCategoryBo<AttackFrontBo>>>
	 */
	@ApiOperation("ATT&CK ICS框架前台")
	@GetMapping("/attack_ics/related")
	@RequiresPermissions(MenuConstant.WEB_ALL)
	public BaseRsp<List<AttackCategoryBo<AttackFrontBo>>> getAttackIcsFront(
			@ApiParam(name = "resource_id", value = "训练模板id") String resource_id) {
		List<AttackCategoryBo<AttackFrontBo>> rsp = attackIcsService
				.getAttacksByTraining(resource_id);
		return new BaseRsp<>(rsp);
	}

	/**
	 * 获取从事返回
	 *
	 * @return {@code BaseRsp<List<EngageCategoryBo<EngageBackBo>>> }
	 * @throws
	 * @see BaseRsp<List<EngageCategoryBo<EngageBackBo>>>
	 */
	@ApiOperation("Engage框架后台")
	@GetMapping("/engage/all")
	@RequiresPermissions(MenuConstant.CMS_ENGAGE_FRAME)
	public BaseRsp<List<EngageCategoryBo<EngageBackBo>>> getEngagesBack() {
		List<EngageCategoryBo<EngageBackBo>> engages = engageService.getEngages();
		return new BaseRsp<>(engages);
	}

	/**
	 * 获取Engage详情
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<EngageDetail> }
	 * @throws
	 * @see String
	 * @see BaseRsp<EngageDetail>
	 */
	@ApiOperation("Engage详情页")
	@GetMapping("/engage")
	@RequiresPermissions(MenuConstant.CMS_ENGAGE_FRAME)
	public BaseRsp<EngageDetail>
			getEngageDetail(@ApiParam(name = "resource_id", value = "技术id") String resource_id) {
		EngageDetail res = engageService.getDetailById(resource_id);

		return new BaseRsp<>(res);
	}

	/**
	 * 更新Engage模板关联
	 *
	 * @param updateReq 更新请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see EngageUpdateReq
	 * @see BaseRsp<Boolean>
	 */
	@ApiOperation("添加Engage 关联")
	@PostMapping("/engage")
	@RequiresPermissions(MenuConstant.CMS_ENGAGE_FRAME)
	public BaseRsp<Boolean> updateEngageTemplateRel(@Validated @RequestBody EngageUpdateReq updateReq) {
		engageService.updateRelations(updateReq.getResourceId(),
				updateReq.getBusinessTemplateIds());
		return new BaseRsp<>(true);
	}

	/**
	 * 获取从事前面
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<List<EngageCategoryBo<EngageFrontBo>>> }
	 * @throws
	 * @see String
	 * @see BaseRsp<List<EngageCategoryBo<EngageFrontBo>>>
	 */
	@ApiOperation("Engage 框架前台")
	@GetMapping("/engage/related")
	@RequiresPermissions(MenuConstant.WEB_ALL)
	public BaseRsp<List<EngageCategoryBo<EngageFrontBo>>>
			getEngagesFront(@ApiParam(name = "resource_id", value = "训练模板id") String resource_id) {
		List<EngageCategoryBo<EngageFrontBo>> rsp = engageService.getEngagesByTraining(resource_id);
		return new BaseRsp<>(rsp);
	}
}
