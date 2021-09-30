package com.cyberpeace.rtks.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.controller.req.AbilityAddReq;
import com.cyberpeace.rtks.controller.req.DeleteReq;
import com.cyberpeace.rtks.controller.req.SkillCategoryOrSystemAddReq;
import com.cyberpeace.rtks.controller.res.SkillRes;
import com.cyberpeace.rtks.dao.entity.SkillSystem;
import com.cyberpeace.rtks.service.SkillService;
import com.cyberpeace.rtks.service.SkillSystemService;
import com.cyberpeace.rtks.service.bo.SceneSkillSystem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "RTKS-技能体系管理")
@RestController
@RequestMapping("/rtks/skill-system")
@RequiresAuthentication
public class SkillSystemController {

	@Autowired
	private SkillSystemService skillSystemService;

	@Autowired
	private SkillService skillService;

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
		List<SkillRes> skillList = skillService.getAllSkills();
		for (SkillRes skillRes : skillList) {
			if (deleteReq.getIds().contains(skillRes.getSystemId())) {
				throw new BusinessException(RtksBusinessErrorCode.UNABLE_DELETE_SKILL);
			}
		}
		skillSystemService.removeByIds(deleteReq.getIds());

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/list")
	public BaseRsp<List<SkillSystem>> list(@Validated PageReqBase pageReqBase) {
		ListRsp<SkillSystem> data = skillSystemService.page(pageReqBase);

		return new BaseRsp(data);
	}

	@ApiOperation("新增场景技能体系")
	@PostMapping("/addSceneSkillSystem")
	public BaseRsp<String> addSceneSkillSystem(
			@RequestBody @Validated SceneSkillSystem sceneSkillSystemAddReq) {

		skillSystemService.updateSceneSkillSystem(sceneSkillSystemAddReq);

		return new BaseRsp<>();
	}

}
