package com.cyberpeace.space.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.space.service.SpaceTacticDefenseService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.tactic.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "知识空间-知识库-战法库-防御战法")
@RestController
@RequestMapping("/space/tactic/defense")
public class SpaceTacticDefenseController {

	@Autowired
	private SpaceTacticDefenseService defenseService;

	@ApiOperation("防御战法列表")
	@GetMapping("/page")
	public BaseRsp<ListRsp<DefenseListBo>> listAttack(PageReqBase reqBase) {
		ListRsp<DefenseListBo> res = defenseService.page(reqBase);
		return new BaseRsp(res);
	}

	@ApiOperation("新增防御战法")
	@PostMapping("")
	public BaseRsp addAttack(@RequestBody DefenseAddReq req) {
		defenseService.add(req);
		return new BaseRsp();
	}

	@ApiOperation("更新防御战法")
	@PatchMapping("")
	public BaseRsp updateA(@RequestBody DefenseModifyReq req) {
		defenseService.updateDefense(req);
		return new BaseRsp();
	}

	@ApiOperation("删除防御战法")
	@DeleteMapping("")
	public BaseRsp deleteA(@RequestBody IdListReq req) {
		defenseService.deleteBatch(req.getIds());
		return new BaseRsp();
	}

	@ApiOperation("获取防御战法详情")
	@GetMapping("")
	public BaseRsp<DefenseDetail>
			getADetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
		DefenseDetail detail = defenseService.getDetail(resource_id);
		return new BaseRsp<>(detail);
	}

	@ApiOperation("防御战法战术分页")
	@GetMapping("/category/page")
	public BaseRsp<ListRsp<TacticDefenseCategoryBo>> pageC(PageReqBase reqBase) {
		ListRsp<TacticDefenseCategoryBo> rsp = defenseService.pageCategory(reqBase);
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("新增防御战法战术")
	@PostMapping("/category")
	public BaseRsp addC(@RequestBody TacticDefenseCategoryAddReq req) {
		defenseService.addDefenseCategory(req);
		return new BaseRsp();
	}

	@ApiOperation("更新防御战法战术")
	@PatchMapping("/category")
	public BaseRsp updateC(@RequestBody TacticDefenseCategoryModifyReq req) {
		defenseService.updateDefenseCategory(req);
		return new BaseRsp();
	}

	@ApiOperation("删除防御战法战术")
	@DeleteMapping("/category")
	public BaseRsp deleteC(@RequestBody IdListReq req) {
		defenseService.deleteCategory(req.getIds());
		return new BaseRsp();
	}

	@ApiOperation("防御战法战术详情")
	@GetMapping("/category")
	public BaseRsp<DefenseCategoryBo>
			getCDetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
        DefenseCategoryBo bo = defenseService.getCategoryDetail(resource_id);
        return new BaseRsp<>(bo);
	}

	@ApiOperation("防御战法战术全部")
	@GetMapping("/category/list")
	public BaseRsp<List<TacticDefenseCategoryBo>> listC() {
		List<TacticDefenseCategoryBo> rsp = defenseService.listCategory();
		return new BaseRsp<>(rsp);
	}
}
