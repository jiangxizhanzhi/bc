package com.cyberpeace.oj.rtks.controller;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.model.PageReqBase;
import com.cyberpeace.oj.rtks.controller.req.AbilityAddReq;
import com.cyberpeace.oj.rtks.controller.req.DeleteReq;
import com.cyberpeace.oj.rtks.controller.req.SkillCategoryOrSystemAddReq;
import com.cyberpeace.oj.rtks.dao.entity.SkillCategory;
import com.cyberpeace.oj.rtks.service.SkillCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "RTKS-技能类别管理")
@RestController
@RequestMapping("/rtks/skill-category")
@RequiresAuthentication
public class SkillCategoryController {

	@Autowired
	private SkillCategoryService skillCategoryService;

	@ApiOperation("查询单条")
	@GetMapping()
	public BaseRsp<SkillCategory> getSkill(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		// ApiParam中的name和java参数String id的两个id必须同名
		SkillCategory skillCategory = skillCategoryService.getById(resource_id);

		return new BaseRsp<>(skillCategory);
	}

	@ApiOperation("新增单条")
	@PostMapping()
	public BaseRsp<String> addSkill(
			@RequestBody @Validated SkillCategoryOrSystemAddReq skillCatagaryOrSystemAddReq) {
		SkillCategory skillCategory = new SkillCategory();
		BeanUtils.copyProperties(skillCatagaryOrSystemAddReq, skillCategory);
		skillCategoryService.save(skillCategory);

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条")
	@PutMapping()
	public BaseRsp<String>
			modifySkill(@RequestBody @Validated AbilityAddReq skillCatagaryOrSystemAddReq) {
		SkillCategory skillCategory = new SkillCategory();
		BeanUtils.copyProperties(skillCatagaryOrSystemAddReq, skillCategory);
		skillCategoryService.updateById(skillCategory);

		return new BaseRsp<>();
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> deleteSkillCategory(@RequestBody @Validated DeleteReq deleteReq) {
		skillCategoryService.removeByIds(deleteReq.getIds());

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/list")
	public BaseRsp<List<SkillCategory>> list(@Validated PageReqBase pageReqBase) {
		ListRsp<SkillCategory> data = skillCategoryService.page(pageReqBase);

		return new BaseRsp(data);
	}
}
