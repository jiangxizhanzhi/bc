package com.cyberpeace.oj.course.controller;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.service.CourseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "实训-课程相关")
@RestController
@RequestMapping("/oj/course")
@RequiresAuthentication
public class CourseController {

	@Autowired
	private CourseService courseService;

	@GetMapping()
	@ApiOperation("单条查询")
	public BaseRsp<Course> getCourse(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam("resource_id") @NotBlank String resourceId) {
		Course course = courseService.selectById(resourceId);
		BaseRsp rsp = new BaseRsp(course);
		return rsp;
	}

}
