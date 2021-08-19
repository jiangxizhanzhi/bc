package com.cyberpeace.oj.rtks.controller;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.model.PageReqBase;
import com.cyberpeace.oj.rtks.controller.req.AbilityAddReq;
import com.cyberpeace.oj.rtks.controller.req.DeleteReq;
import com.cyberpeace.oj.rtks.controller.req.SkillCategoryOrSystemAddReq;
import com.cyberpeace.oj.rtks.dao.entity.SkillSystem;
import com.cyberpeace.oj.rtks.service.SkillSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "RTKS-技能体系管理")
@RestController
@RequestMapping("/rtks/skill-system")
public class SkillSystemController {

	@Autowired
	private SkillSystemService skillSystemService;

	@ApiOperation("查询单条")
	@GetMapping()
	public BaseRsp<SkillSystem> getSkill(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		// ApiParam中的name和java参数String id的两个id必须同名
		SkillSystem skillSystem = skillSystemService.getById(resource_id);

		return new BaseRsp<>(skillSystem);
	}

	@ApiOperation("新增单条")
	@PostMapping()
	public BaseRsp<String> addSkill(
			@RequestBody @Validated SkillCategoryOrSystemAddReq skillCatagaryOrSystemAddReq) {
		SkillSystem skillSystem = new SkillSystem();
		BeanUtils.copyProperties(skillCatagaryOrSystemAddReq, skillSystem);
		skillSystemService.save(skillSystem);

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条")
	@PutMapping()
	public BaseRsp<String>
			modifySkill(@RequestBody @Validated AbilityAddReq skillCatagaryOrSystemAddReq) {
		SkillSystem skillSystem = new SkillSystem();
		BeanUtils.copyProperties(skillCatagaryOrSystemAddReq, skillSystem);
		skillSystemService.updateById(skillSystem);

		return new BaseRsp<>();
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> deleteSkillSystem(@RequestBody @Validated DeleteReq deleteReq) {
		skillSystemService.removeByIds(deleteReq.getIds());

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/list")
	public BaseRsp<List<SkillSystem>> list(@Validated PageReqBase pageReqBase) {
		ListRsp<SkillSystem> data = skillSystemService.page(pageReqBase);

		return new BaseRsp(data);
	}
}
