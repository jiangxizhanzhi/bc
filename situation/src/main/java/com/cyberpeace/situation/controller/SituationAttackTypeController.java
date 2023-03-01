package com.cyberpeace.situation.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.situation.constant.ws.ActionType;
import com.cyberpeace.situation.controller.req.AddAttackTypeReq;
import com.cyberpeace.situation.controller.req.UpdateAttackTypeReq;
import com.cyberpeace.situation.dao.entity.AttackType;
import com.cyberpeace.situation.service.AttackTypeService;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "态势攻击类型")
@RestController
@RequestMapping("/situation/attack_type")
public class SituationAttackTypeController {

	@Autowired
	private AttackTypeService attackTypeService;

	@ApiOperation("新增攻击类型")
	@PostMapping("/")
	public BaseRsp<Boolean> addAttackType(@RequestBody @Valid AddAttackTypeReq addAttackTypeReq) {
		attackTypeService.addAttackType(addAttackTypeReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("修改攻击类型")
	@PatchMapping("/")
	public BaseRsp<Boolean> updateAttackType(@RequestBody @Valid UpdateAttackTypeReq updateAttackTypeReq) {
		attackTypeService.updateAttackType(updateAttackTypeReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("删除攻击类型")
	@DeleteMapping("/")
	public BaseRsp<Boolean> deleteAttackType(@RequestParam("attack_type_ids") List<String> attackTypeIds) {
		attackTypeService.deleteAttackType(attackTypeIds);
		return new BaseRsp<>(true);
	}

	@ApiOperation("查看攻击类型")
	@GetMapping("/")
	public BaseRsp<AttackType> getAttackType(@RequestParam("attack_type_id")String attackTypeId) {
		return new BaseRsp<>(attackTypeService.getAttackType(attackTypeId));
	}

	@ApiOperation("列表查询攻击类型")
	@GetMapping("/list")
	public BaseRsp<ListRsp<AttackType>> getListAttackType(@Valid PageReqBase pageReqBase) {
		if (StringUtils.isBlank(pageReqBase.getSort())) {
			pageReqBase.setSort("-update_time");
		}
		return new BaseRsp<>(attackTypeService.getListAttackType(pageReqBase));
	}

	@ApiOperation("返回动作类型")
	@PostMapping("/action_type_list")
	public BaseRsp<List<ActionType>> getActionTypeList() {
		return new BaseRsp<>(Lists.newArrayList(ActionType.values()));
	}
}
