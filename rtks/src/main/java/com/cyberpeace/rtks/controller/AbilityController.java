package com.cyberpeace.rtks.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.rtks.controller.req.AbilityAddReq;
import com.cyberpeace.rtks.controller.req.AbilityListReq;
import com.cyberpeace.rtks.dao.entity.Ability;
import com.cyberpeace.rtks.service.AbilityService;
import com.cyberpeace.rtks.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 能力控制器
 *
 * @author SN
 * @date 2024/11/07
 */
@Api(tags = "RTKS-能力库管理")
@RestController
@RequestMapping("/rtks")
@RequiresAuthentication
public class AbilityController {

	/**
	 * 能力Service
	 */
	@Autowired
	private AbilityService abilityService;

	/**
	 * 角色Service
	 */
	@Autowired
	private RoleService roleService;

	/**
	 * 获取能力
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<Ability> }
	 */
	@ApiOperation("查询单条能力")
	@GetMapping("/ability")
	public BaseRsp<Ability> getAbility(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		Ability ability = abilityService.getById(resource_id);

		return new BaseRsp(ability);
	}

	/**
	 * 新增能力
	 *
	 * @param abilityAddReq 能力新增请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("新增单条能力")
	@PostMapping("/ability")
	public BaseRsp<String> addAbility(@RequestBody @Validated AbilityAddReq abilityAddReq) {
		Ability toAddAbility = new Ability();
		BeanUtils.copyProperties(abilityAddReq, toAddAbility);

		abilityService.save(toAddAbility);

		return new BaseRsp<>();
	}

	/**
	 * 修改能力
	 *
	 * @param abilityAddReq 能力新增请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("修改单条能力")
	@PutMapping("/ability")
	public BaseRsp<String> modifyAbility(@RequestBody @Validated AbilityAddReq abilityAddReq) {
		Ability toUpdateAbility = new Ability();
		BeanUtils.copyProperties(abilityAddReq, toUpdateAbility);

		abilityService.updateById(toUpdateAbility);

		return new BaseRsp<>();
	}

	/**
	 * 删除功能
	 *
	 * @param deleteReq 删除请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("删除能力")
	@DeleteMapping("/ability")
	public BaseRsp<String> deleteAbility(@RequestBody @Validated DeleteReq deleteReq) {
		abilityService.removeByIds(deleteReq.getIds());
		roleService.updateBatchByDeleteRelatedIds(deleteReq, "ability");

		return new BaseRsp<>();
	}

	/**
	 * 列表
	 *
	 * @param abilityListReq 能力列表请求体
	 * @return {@code BaseRsp<List<Ability>> }
	 */
	@ApiOperation("分页查询")
	@GetMapping("/ability/list")
	public BaseRsp<List<Ability>> list(@Validated AbilityListReq abilityListReq) {
		ListRsp<Ability> data = abilityService.page(abilityListReq);

		return new BaseRsp(data);
	}

}
