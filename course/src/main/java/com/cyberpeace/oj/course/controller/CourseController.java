package com.cyberpeace.oj.course.controller;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.course.controller.req.CourseListReq;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

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

	@GetMapping("/list")
	@ApiOperation("分页查询课程信息")
	public BaseRsp<List<CourseInfoBo>>
			getList(@ApiParam(name = "listReq", value = "查询条件") @Validated CourseListReq listReq) {

		ListRsp<CourseInfoBo> courseInfos = courseService.selectPage(listReq);
		BaseRsp rsp = new BaseRsp(courseInfos);
		return rsp;
	}

	@GetMapping("/test")
	@ApiOperation("测试调用service代码")
	public BaseRsp test() {
		List<String> ids = Lists.newArrayList();
		ids.add("c73ec991cff9ba147b8e5a224cec379c");
		ids.add("ded39c03161f4763f2d24a2b2cbd957d");
		ids.add("574230c97d8b8ec75ed411ee68baf6f5");
		ids.add("8ebc93399ac6e384a26f51090e582f09");
		ids.add("62e21666d4e5ecd60ee7023f513e365b");
		ids.add("70597a642644d4b5f11abcf8b1f126c7");
		ids.add("f0ecb2bf5b898503f6a0f9756f40cdd3");
		List<String> idsNoDelete = courseService.idsByNotDeleteIds(ids);
		BaseRsp rsp = new BaseRsp(idsNoDelete);
		return rsp;
	}

}
