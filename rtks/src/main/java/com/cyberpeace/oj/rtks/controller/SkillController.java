package com.cyberpeace.oj.rtks.controller;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.JsonUtil;
import com.cyberpeace.oj.rtks.controller.req.DeleteReq;
import com.cyberpeace.oj.rtks.controller.req.SkillAddReq;
import com.cyberpeace.oj.rtks.controller.req.SkillListReq;
import com.cyberpeace.oj.rtks.dao.entity.Skill;
import com.cyberpeace.oj.rtks.controller.res.SkillRes;
import com.cyberpeace.oj.rtks.service.SkillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "RTKS-技能管理")
@RestController
@RequestMapping("/rtks/skill")
public class SkillController {

	@Autowired
	private SkillService skillService;

	@ApiOperation("查询单条")
	@GetMapping()
	public BaseRsp<Skill> getSkill(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		// ApiParam中的name和java参数String id的两个id必须同名
		Skill skill = skillService.getSkillByResourceId(resource_id);

		return new BaseRsp<>(skill);
	}

	@ApiOperation("新增单条")
	@PostMapping()
	public BaseRsp<String> addSkill(@RequestBody @Validated SkillAddReq skillAddReq) {
		Skill skill = new Skill();
		BeanUtils.copyProperties(skillAddReq, skill);
		skill.setCourses(JsonUtil.toJsonString(skillAddReq.getCourseList()));
		skillService.save(skill);
		// List<SkillCourse> skillCourses = Lists.newArrayList();
		// for (String courseId : skillAddReq.getCourseList()) {
		// SkillCourse sc = new SkillCourse();
		// sc.setSkillId(skillAddReq.getResourceId());
		// sc.setCourseId(courseId);
		// skillCourses.add(sc);
		// }
		// skillCourseService.saveBatch(skillCourses);

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条")
	@PutMapping()
	public BaseRsp<String> modifySkill(@RequestBody @Validated SkillAddReq skillAddReq) {
		Skill skill = new Skill();
		BeanUtils.copyProperties(skillAddReq, skill);
		skill.setCourses(JsonUtil.toJsonString(skillAddReq.getCourseList()));
		skillService.updateById(skill);

		return new BaseRsp<>();
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> deleteTask(@RequestBody @Validated DeleteReq deleteReq) {
		skillService.removeByIds(deleteReq.getIds());

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/list")
	public BaseRsp<List<SkillRes>> list(@Validated SkillListReq skillListReq) {
		ListRsp<SkillRes> data = skillService.page(skillListReq);

		return new BaseRsp(data);
	}
}
