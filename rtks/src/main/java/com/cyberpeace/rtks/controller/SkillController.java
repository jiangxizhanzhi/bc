package com.cyberpeace.rtks.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.rtks.controller.req.DeleteReq;
import com.cyberpeace.rtks.controller.req.SkillAddReq;
import com.cyberpeace.rtks.controller.req.SkillListReq;
import com.cyberpeace.rtks.controller.res.SkillRes;
import com.cyberpeace.rtks.dao.entity.CourseRelated;
import com.cyberpeace.rtks.dao.entity.RelatedCourse;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.service.CourseRelatedService;
import com.cyberpeace.rtks.service.SkillService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "RTKS-技能管理")
@RestController
@RequestMapping("/rtks/skill")
@RequiresAuthentication
public class SkillController {

	@Autowired
	private SkillService skillService;

	@Autowired
	private CourseRelatedService courseRelatedService;

	@ApiOperation("查询单条")
	@GetMapping()
	public BaseRsp<SkillRes> getSkill(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		// ApiParam中的name和java参数String id的两个id必须同名
		SkillRes skillRes = skillService.getSkillByResourceId(resource_id);
		return new BaseRsp<>(skillRes);
	}

	@ApiOperation("新增单条")
	@PostMapping()
	public BaseRsp<String> addSkill(@RequestBody @Validated SkillAddReq skillAddReq) {
		Skill skill = new Skill();
		BeanUtils.copyProperties(skillAddReq, skill);
		skill.setCourses(JsonUtil.toJsonString(skillAddReq.getCourseList()));

		skillService.validate(skill);
		List<RelatedCourse> relatedCourseList = skillAddReq.getCourseList();

		String courses = courseRelatedService.saveCourseRelatedList(relatedCourseList,
				skill.getResourceId(), CourseRelated.RELATED_TYPE_SKILL);
		skill.setCourses(courses);

		skillService.save(skill);

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条")
	@PutMapping()
	public BaseRsp<String> modifySkill(@RequestBody @Validated SkillAddReq skillAddReq) {
		Skill skill = new Skill();
		BeanUtils.copyProperties(skillAddReq, skill);
		skill.setCourses(JsonUtil.toJsonString(skillAddReq.getCourseList()));

		skillService.validate(skill);
		List<RelatedCourse> relatedCourseList = skillAddReq.getCourseList();
		String courses = courseRelatedService.saveCourseRelatedList(relatedCourseList,
				skill.getResourceId(), CourseRelated.RELATED_TYPE_SKILL);
		skill.setCourses(courses);

		skillService.updateById(skill);

		return new BaseRsp<>();
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> deleteSkill(@RequestBody @Validated DeleteReq deleteReq) {
		skillService.removeByIds(deleteReq.getIds());
		courseRelatedService.deleteCourseRelatedByRelatedIds(deleteReq.getIds());

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/list")
	public BaseRsp<List<SkillRes>> list(@Validated SkillListReq skillListReq) {
		ListRsp<SkillRes> data = skillService.page(skillListReq);

		return new BaseRsp(data);
	}
}
