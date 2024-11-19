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
 * 能力Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Api(tags = "RTKS-能力库管理")
@RestController
@RequestMapping("/rtks")
@RequiresAuthentication
public class AbilityController {

	/**
	 * 能力Service
	 *
	 * @see AbilityService
	 */
	@Autowired
	private AbilityService abilityService;

	/**
	 * 岗位Service
	 *
	 * @see RoleService
	 */
	@Autowired
	private RoleService roleService;

	/**
	 * 获取能力
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<Ability> }
	 * @throws
	 * @see String
	 * @see BaseRsp<Ability>
	 */
	@ApiOperation("查询单条能力")
	@GetMapping("/ability")
	public BaseRsp<Ability> getAbility(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		return new BaseRsp<>(abilityService.getById(resource_id));
	}

	/**
	 * 新增能力
	 *
	 * @param abilityAddReq 能力新增请求体
	 * @return {@code BaseRsp<String> }
	 * @throws
	 * @see AbilityAddReq
	 * @see BaseRsp<String>
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
	 * @throws
	 * @see AbilityAddReq
	 * @see BaseRsp<String>
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
	 * @throws
	 * @see DeleteReq
	 * @see BaseRsp<String>
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
	 * @return {@code BaseRsp<ListRsp<Ability>> }
	 * @throws
	 * @see AbilityListReq
	 * @see BaseRsp<ListRsp<Ability>>
	 */
	@ApiOperation("分页查询")
	@GetMapping("/ability/list")
	public BaseRsp<ListRsp<Ability>> list(@Validated AbilityListReq abilityListReq) {
		return new BaseRsp<>(abilityService.page(abilityListReq));
	}

}
