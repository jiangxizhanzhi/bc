package com.cyberpeace.space.controller;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.space.dao.entity.SpaceTacticAttack;
import com.cyberpeace.space.service.SpaceTacticAttackService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.tactic.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "战法库-攻击战法")
@RestController
@RequestMapping("/space/tactic/attack")
public class SpaceTacticAttackController {

	@Autowired
	private SpaceTacticAttackService attackService;

	@ApiOperation("攻击战法列表")
	@GetMapping("/page")
	public BaseRsp<ListRsp<AttackListBo>> listAttack(SpaceTacticPageReq req) {
		ListRsp<AttackListBo> res = attackService.pageAttack(req);
		return new BaseRsp(res);
	}

	@ApiOperation("新增攻击战法")
	@PostMapping("")
	public BaseRsp addAttack(@RequestBody AttackAddReq req) {
		attackService.addAttack(req);
		return new BaseRsp();
	}

	@ApiOperation("更新攻击战法")
	@PatchMapping("")
	public BaseRsp updateA(@RequestBody AttackModifyReq req) {
		SpaceTacticAttack old=attackService.getById(req.getResourceId());
		if(old.getBuiltin()==true){
			throw new BusinessException(ErrorCode.BUILT_IN);
		}
		attackService.updateAttack(req);
		return new BaseRsp();
	}

	@ApiOperation("删除攻击战法")
	@DeleteMapping("")
	public BaseRsp deleteA(@RequestBody IdListReq req) {
		List<SpaceTacticAttack> attacks = attackService.listByIds(req.getIds());
		for(SpaceTacticAttack attack:attacks){
			if(Boolean.TRUE.equals(attack.getBuiltin())){
				throw new BusinessException(ErrorCode.BUILT_IN);
			}
		}

		attackService.deleteBatch(req.getIds());
		return new BaseRsp();
	}

	@ApiOperation("攻击战法详情")
	@GetMapping("")
	public BaseRsp<AttackDetail>
			getADetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
		AttackDetail detail = attackService.getDetail(resource_id);
		return new BaseRsp<>(detail);
	}

	@ApiOperation("攻击战法类别列表")
	@GetMapping("/category/page")
	public BaseRsp<ListRsp<TacticAttackCategoryBo>> pageC(PageReqBase reqBase) {
		ListRsp<TacticAttackCategoryBo> rsp = attackService.pageCategory(reqBase);
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("新增攻击战法类别")
	@PostMapping("/category")
	public BaseRsp addC(@RequestBody TacticAttackCategoryAddReq req) {
		attackService.addAttackCategory(req);
		return new BaseRsp();
	}

	@ApiOperation("修改攻击战法类别")
	@PatchMapping("/category")
	public BaseRsp updateC(@RequestBody TacticAttackCategoryModifyReq req) {
		attackService.updateAttackCategory(req);
		return new BaseRsp();
	}

	@ApiOperation("删除攻击战法类别")
	@DeleteMapping("/category")
	public BaseRsp deleteC(@RequestBody IdListReq req) {
		List <String> resourceIds =attackService.listByCategoryids(req.getIds());
		if (resourceIds!= null && !resourceIds.isEmpty()) {
			throw new BusinessException(ErrorCode.BUILT_IN);
		}
		attackService.deleteCategory(req.getIds());
		return new BaseRsp();
	}
}
