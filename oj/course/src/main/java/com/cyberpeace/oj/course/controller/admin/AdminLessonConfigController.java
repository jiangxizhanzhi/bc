package com.cyberpeace.oj.course.controller.admin;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/oj/admin/lessonconfig")
@Api(tags = "oj-后台课程目录相关api")
@RequiresAuthentication
public class AdminLessonConfigController {

	@Autowired
	private LessonConfigService lessonConfigService;

	@ApiOperation("后台课程目录查询")
	@GetMapping("")
	public BaseRsp<LessonConfigBo> getLessonConfig(@ApiParam(name = "course_id",
			value = "课程编号") @RequestParam("course_id") String courseId) {

		LessonConfigBo lessonConfig = lessonConfigService.getLessonConfig(courseId, null, false);
		return new BaseRsp(lessonConfig);
	}

	@ApiOperation("编排课时")
	@PutMapping("")
	public BaseRsp<LessonConfigBo> setLessonConfig(
			@ApiParam(name = "configBo", value = "课程编排信息") @RequestBody LessonConfigBo configBo) {

		LessonConfigBo lessonConfigBo = lessonConfigService.updateLessonConfig(configBo);
		return new BaseRsp(lessonConfigBo);
	}

}
