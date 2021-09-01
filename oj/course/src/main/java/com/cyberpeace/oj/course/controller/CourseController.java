package com.cyberpeace.oj.course.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.controller.req.CourseListReq;
import com.cyberpeace.oj.course.controller.res.DirectionOutPut;
import com.cyberpeace.oj.course.dao.entity.CourseDirection;
import com.cyberpeace.oj.course.dao.mapper.DirectionMapper;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;

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

	@Autowired
	private DirectionMapper directionMapper;

	@GetMapping()
	@ApiOperation("单条查询")
	public BaseRsp<CourseInfoBo> getCourse(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam("resource_id") @NotBlank String resourceId) {
		CourseInfoBo course = courseService.selectById(resourceId);
		BaseRsp rsp = new BaseRsp(course);
		return rsp;
	}

	@GetMapping("/list")
	@ApiOperation("分页查询课程信息")
	public BaseRsp<List<CourseInfoBo>>
			getList(@ApiParam(name = "listReq", value = "查询条件") @Validated CourseListReq listReq) {

		ListRsp<CourseInfoBo> courseInfos = courseService.selectPage(listReq);
		BaseRsp rsp = new BaseRsp(courseInfos);
		return rsp;
	}

	@GetMapping("/direction")
	@ApiOperation("课程分类")
	public BaseRsp<List<CourseDirection>> listDirection() {
		List<CourseDirection> courseDirections = directionMapper.selectList(null);
		List<DirectionOutPut> outPuts = courseDirections.stream().map(courseDirection -> {
			DirectionOutPut outPut = new DirectionOutPut();
			BeanUtils.copyProperties(courseDirection, outPut);
			return outPut;
		}).collect(Collectors.toList());
		BaseRsp rsp = new BaseRsp(outPuts);
		return rsp;
	}

}
