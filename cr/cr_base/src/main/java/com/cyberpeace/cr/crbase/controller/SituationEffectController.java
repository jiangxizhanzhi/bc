package com.cyberpeace.cr.crbase.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.crbase.service.SituationEffectService;
import com.cyberpeace.common.model.IdListReq;
import com.cyberpeace.cr.crbase.service.context.IdReq;
import com.cyberpeace.cr.crbase.service.context.effect.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "动效管理")
@RestController
@Slf4j
@RequestMapping("/situation/effect")
@RequiresAuthentication
public class SituationEffectController {

	@Autowired
	private SituationEffectService situationEffectService;

	@ApiOperation("分类列表")
	@GetMapping("/category/list")
	// @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE,
	// MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
	public BaseRsp<List<SituationEffectCategoryName>> listCategory() {
		List<SituationEffectCategoryName> res = situationEffectService.listCategory();
		return new BaseRsp<>(res);
	}

	@ApiOperation("分类分页列表")
	@GetMapping("/category/page")
	public BaseRsp<ListRsp<SituationEffectCategoryName>>
			pageCategory(@Validated PageReqBase reqBase) {
		ListRsp<SituationEffectCategoryName> res = situationEffectService.pageCategory(reqBase);
		return new BaseRsp<>(res);
	}

	@ApiOperation("新增分类")
	@PostMapping("/category")
	public BaseRsp createCategory(@RequestBody @Valid SituationEffectCategoryAddReq req) {

		situationEffectService.createCategory(req);
		return new BaseRsp();
	}

	@ApiOperation("修改分类")
	@PutMapping("/category")
	public BaseRsp modifyCategory(@RequestBody @Valid SituationEffectCategoryModifyReq req) {

		situationEffectService.modifyCategory(req);
		return new BaseRsp();
	}

	@ApiOperation("删除分类")
	@DeleteMapping("/category")
	public BaseRsp deleteCategory(@RequestBody @Valid IdListReq req) {

		situationEffectService.deleteCategory(req);
		return new BaseRsp();
	}

	@ApiOperation("动效列表")
	@GetMapping("/page")
	public BaseRsp<ListRsp<SituationEffectBo>>
			getEffectPage(@Validated SituationEffectPageReq req) {

		ListRsp<SituationEffectBo> res = situationEffectService.pageEffect(req);
		return new BaseRsp(res);
	}

	@ApiOperation("新增动效")
	@PostMapping("/")
	public BaseRsp create(@RequestBody @Valid SituationEffectAddReq req) {

		situationEffectService.createEffect(req);
		return new BaseRsp();
	}

	@ApiOperation("查看动效")
	@GetMapping("/")
	public BaseRsp<SituationEffectBo>
			getDetail(@ApiParam(value = "id", name = "resource_id") String resource_id) {

		SituationEffectBo res = situationEffectService.getDetail(resource_id);
		return new BaseRsp<>(res);
	}

	@ApiOperation("修改动效")
	@PutMapping("/")
	public BaseRsp modify(@RequestBody @Valid SituationEffectModifyReq req) {

		situationEffectService.modifyEffect(req);
		return new BaseRsp();
	}

	@ApiOperation("删除动效")
	@DeleteMapping("/")
	public BaseRsp delete(@RequestBody @Valid IdReq req) {

		situationEffectService.deleteEffect(req.getId());
		return new BaseRsp();
	}

	@ApiOperation("动效映射列表")
	@GetMapping("/mapping/page")
	public BaseRsp<ListRsp<SituationEffectMappingBo>> getMappingPage(@Validated PageReqBase req) {

		ListRsp<SituationEffectMappingBo> res = situationEffectService.pageMapping(req);
		return new BaseRsp(res);
	}

	@ApiOperation("新增动效映射")
	@PostMapping("/mapping")
	public BaseRsp createMapping(@RequestBody @Valid SituationEffectMappingAddReq req) {

		situationEffectService.createMapping(req);
		return new BaseRsp();
	}

	@ApiOperation("查看映射关系")
	@GetMapping("/mapping")
	public BaseRsp<SituationEffectMappingBo>
			getMappingDetail(@ApiParam(value = "id", name = "resource_id") String resource_id) {

		SituationEffectMappingBo res = situationEffectService.getMappingDetail(resource_id);
		return new BaseRsp<>(res);
	}

	@ApiOperation("修改动效映射")
	@PutMapping("/mapping")
	public BaseRsp modifyMapping(@RequestBody @Valid SituationEffectMappingModifyReq req) {

		situationEffectService.modifyMapping(req);
		return new BaseRsp();
	}

	@ApiOperation("删除动效映射")
	@DeleteMapping("/mapping")
	public BaseRsp deleteMapping(@RequestBody @Valid IdListReq req) {

		situationEffectService.deleteMapping(req.getIds());
		return new BaseRsp();
	}

}
