package com.cyberpeace.oj.course.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.controller.res.DirectionOutPut;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "oj-课程分类相关api")
@RestController
@RequestMapping("/oj/course/direction")
@RequiresAuthentication
public class CourseCategoryController {

	@Autowired
	private CourseCategoryMapper courseCategoryMapper;

	@Autowired
	private CourseCategoryService courseCategoryService;

	// LGD在使用 知识点和技能点绑定课程
	@GetMapping("")
	@ApiOperation("课程分类")
	public BaseRsp<List<CourseCategory>> listDirection() {
		List<CourseCategory> courseDirections = courseCategoryMapper.selectList(null);
		List<CourseCategory> categories = courseCategoryService.selectAll();
		List<DirectionOutPut> outPuts = courseDirections.stream().map(courseDirection -> {
			DirectionOutPut outPut = BeanUtils.copy(courseDirection, DirectionOutPut.class);
			return outPut;
		}).collect(Collectors.toList());

		BaseRsp rsp = new BaseRsp(outPuts);
		return rsp;
	}

}
