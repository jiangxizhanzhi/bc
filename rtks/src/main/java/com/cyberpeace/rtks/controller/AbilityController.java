package com.cyberpeace.rtks.controller;

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

@Api(tags = "RTKS-能力库管理")
@RestController
@RequestMapping("/rtks")
@RequiresAuthentication
public class AbilityController {

	@Autowired
	private AbilityService abilityService;

	@Autowired
	private RoleService roleService;

	@ApiOperation("查询单条能力")
	@GetMapping("/ability")
	public BaseRsp<Ability> getAbility(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam(name = "resource_id") String resourceId) {
		return new BaseRsp<>(abilityService.getById(resourceId));
	}

	@ApiOperation("新增单条能力")
	@PostMapping("/ability")
	public BaseRsp<String> addAbility(@RequestBody @Validated AbilityAddReq abilityAddReq) {
		Ability toAddAbility = new Ability();
		BeanUtils.copyProperties(abilityAddReq, toAddAbility);

		abilityService.save(toAddAbility);

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条能力")
	@PutMapping("/ability")
	public BaseRsp<String> modifyAbility(@RequestBody @Validated AbilityAddReq abilityAddReq) {
		Ability toUpdateAbility = new Ability();
		BeanUtils.copyProperties(abilityAddReq, toUpdateAbility);

		abilityService.updateById(toUpdateAbility);

		return new BaseRsp<>();
	}

	@ApiOperation("删除能力")
	@DeleteMapping("/ability")
	public BaseRsp<String> deleteAbility(@RequestBody @Validated DeleteReq deleteReq) {
		abilityService.removeByIds(deleteReq.getIds());
		roleService.updateBatchByDeleteRelatedIds(deleteReq, "ability");

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/ability/list")
	public BaseRsp<ListRsp<Ability>> list(@Validated AbilityListReq abilityListReq) {
		return new BaseRsp<>(abilityService.page(abilityListReq));
	}

}
