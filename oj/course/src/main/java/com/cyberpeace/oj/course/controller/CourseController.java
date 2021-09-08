package com.cyberpeace.oj.course.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.controller.req.CourseAdminListReq;
import com.cyberpeace.oj.course.dao.context.AuthContext;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseAuthConstructService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.oj.course.controller.req.CollectionReq;
import com.cyberpeace.oj.course.controller.req.CourseListReq;
import com.cyberpeace.oj.course.controller.res.DirectionOutPut;
import com.cyberpeace.oj.course.dao.entity.CourseDirection;
import com.cyberpeace.oj.course.dao.mapper.DirectionMapper;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "oj-课程相关")
@RestController
@RequestMapping("/oj/course")
@RequiresAuthentication
public class CourseController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private DirectionMapper directionMapper;

	@Autowired
	private CourseAuthConstructService courseAuthConstructService;

	@GetMapping()
	@ApiOperation("单条查询")
	public BaseRsp<CourseInfoBo> getCourse(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam("resource_id") @NotBlank String resourceId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		CourseInfoBo course = courseService.selectById(resourceId, userId);
		BaseRsp rsp = new BaseRsp(course);
		return rsp;
	}

	@GetMapping("/list")
	@ApiOperation("分页查询全部课程信息")
	public BaseRsp<List<CourseInfoBo>>
			getList(@ApiParam(name = "listReq", value = "查询条件") @Validated CourseListReq listReq) {

		// 组装筛选条件
		CourseContext context = BeanUtils.copy(listReq, CourseContext.class);
		context.setUserId(UserInfoContext.getUserContext().getUserId());
		context.isWeb();
		Page page = listReq.toPageParam();

		// 数据查询
		ListRsp<CourseInfoBo> courseInfos = courseService.selectPage(page, context, null);
		BaseRsp rsp = new BaseRsp(courseInfos);
		return rsp;
	}

	@ApiOperation("前台课程列表查询")
	@GetMapping("/web/list")
	public BaseRsp<List<CourseInfoBo>> getWebCourse(
			@ApiParam(name = "listReq", value = "查询条件") @Validated CourseListReq listReq) {
		// 数据组装
		CourseContext context = BeanUtils.copy(listReq, CourseContext.class);
		context.setUserId(UserInfoContext.getUserContext().getUserId());
		context.isWeb();
		Page page = listReq.toPageParam();
		AuthContext authContext = courseAuthConstructService
				.webCourseConstruct(UserInfoContext.getUserContext());

		// 数据查询
		ListRsp<CourseInfoBo> boListRsp = courseService.selectPage(page, context, authContext);
		return new BaseRsp(boListRsp);
	}

	@ApiOperation("后台课程列表查询")
	@GetMapping("/admin/list")
	public BaseRsp<List<CourseInfoBo>>
			getAdminCourse(@ApiParam(name = "listReq", value = "查询条件") CourseAdminListReq listReq) {

		// 数据组装
		CourseContext context = BeanUtils.copy(listReq, CourseContext.class);
		context.setUserId(UserInfoContext.getUserContext().getUserId());
		Page page = listReq.toPageParam();
		AuthContext authContext = courseAuthConstructService.adminCourseConstruct(UserInfoContext.getUserContext());

		// 数据查询
		ListRsp<CourseInfoBo> rsp = courseService.selectPage(page, context, authContext);
		return new BaseRsp(rsp);
	}

	@GetMapping("/direction")
	@ApiOperation("课程分类")
	public BaseRsp<List<CourseDirection>> listDirection() {
		List<CourseDirection> courseDirections = directionMapper.selectList(null);

		List<DirectionOutPut> outPuts = courseDirections.stream().map(courseDirection -> {
			DirectionOutPut outPut = BeanUtils.copy(courseDirection, DirectionOutPut.class);
			return outPut;
		}).collect(Collectors.toList());

		BaseRsp rsp = new BaseRsp(outPuts);
		return rsp;
	}

	@ApiOperation("课程收藏")
	@PostMapping("/collect")
	public BaseRsp<String> courseCollection(@RequestBody CollectionReq req) {

		String userId = UserInfoContext.getUserContext().getUserId();

		String courseId = courseService.collectCourse(userId, req.getResource(),
				req.getIsCollection());
		return new BaseRsp<>(courseId);
	}

}
