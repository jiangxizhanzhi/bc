package com.cyberpeace.rtks.controller;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.rtks.controller.res.OccupationCourseRes;
import com.cyberpeace.rtks.service.CourseTrainService;
import com.cyberpeace.rtks.service.bo.CourseKnowledgeSkillBo;
import com.cyberpeace.rtks.service.bo.RoleCourseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "rtks-课程训练相关")
@RestController
@RequestMapping("/course/train")
@RequiresAuthentication
public class CourseTrainController {

	@Autowired
	private CourseTrainService courseTrainService;

	// 对应页面“推荐学习路径”
	@ApiOperation("课程训练-职业课程课程学习进度")
	@GetMapping("/knowledge")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	public BaseRsp<OccupationCourseRes> courseKnowledge() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String roleId = UserInfoContext.getUserContext().getRoleId();
		List<CourseKnowledgeSkillBo> courseKnowledges = courseTrainService
				.getCourseKnowledge(userId, roleId);
		return new BaseRsp<>(calculatePercent(courseKnowledges));
	}

	@ApiOperation("学习路径-专业课程")
	@GetMapping("/getCourseKnowledgeByRoleId")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	public BaseRsp<OccupationCourseRes> getCourseKnowledgeByRoleId(
			@ApiParam(name = "resource_id", value = "岗位id", required = true) String resource_id) {
		List<CourseKnowledgeSkillBo> courseKnowledges = courseTrainService
				.getCourseKnowledgeByRoleId(resource_id);
		return new BaseRsp<>(calculatePercent(courseKnowledges));
	}

	/**
	 * 计算进度
	 * @param courseKnowledges
	 * @return
	 */
	private OccupationCourseRes calculatePercent(List<CourseKnowledgeSkillBo> courseKnowledges) {

		OccupationCourseRes courseRes = new OccupationCourseRes();
		// 计算理论课时进度
		int theoryCount = courseKnowledges.stream().mapToInt(CourseKnowledgeSkillBo::getTheoryCount)
				.sum();
		int completeTheory = courseKnowledges.stream()
				.mapToInt(CourseKnowledgeSkillBo::getCompleteTheory).sum();
		courseRes.setTheoryPercent(MathUtils.ceilDivide(Double.valueOf(completeTheory * 100),
				Double.valueOf(theoryCount)));

		// 计算实验课时进度
		int exerciseCount = courseKnowledges.stream()
				.mapToInt(CourseKnowledgeSkillBo::getExerciseCount).sum();
		int completeExercise = courseKnowledges.stream()
				.mapToInt(CourseKnowledgeSkillBo::getCompleteExercise).sum();
		courseRes.setExercisePercent(MathUtils.ceilDivide(Double.valueOf(completeExercise * 100),
				Double.valueOf(exerciseCount)));

		// 计算总进度
		int count = courseKnowledges.stream().mapToInt(CourseKnowledgeSkillBo::getLessonCount)
				.sum();
		int completeCount = courseKnowledges.stream()
				.mapToInt(CourseKnowledgeSkillBo::getCompleteCount).sum();
		courseRes.setCoursePercent(
				MathUtils.ceilDivide(Double.valueOf(completeCount * 100), Double.valueOf(count)));

		courseRes.setKnowledgeBos(courseKnowledges);
		return courseRes;
	}

	@ApiOperation("职业路径课程")
	@GetMapping("/role/path")
	@RequiresPermissions(value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE,
			MenuConstant.WEB_PERSON_CENTER }, logical = Logical.OR)
	public BaseRsp<List<RoleCourseBo>> getRoleCourse(@ApiParam(value = "查询id") @RequestParam(value = "resource_id",required = false) String resourceId) {
		String roleId = UserInfoContext.getUserContext().getRoleId();

		if (StringUtils.isNotEmpty(UserInfoContext.getUserContext().getHaiboToken())) {
			BaseRsp<List<RoleCourseBo>> rsp = new BaseRsp<>();
			rsp.setData(new ArrayList<>());
			return rsp;
		}

		BaseRsp<List<RoleCourseBo>> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(roleId)) {
			List<RoleCourseBo> courses = courseTrainService.getRoleCoursePath(roleId, resourceId);
			rsp.setData(courses);
		}
		return rsp;
	}

}
