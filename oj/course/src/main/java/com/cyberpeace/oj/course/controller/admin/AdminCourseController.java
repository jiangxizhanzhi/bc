package com.cyberpeace.oj.course.controller.admin;

import java.util.List;

import javax.validation.Valid;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.oj.course.service.CourseDirectionService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.controller.req.CourseAddReq;
import com.cyberpeace.oj.course.controller.req.CourseAdminListReq;
import com.cyberpeace.oj.course.controller.req.CourseListReq;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseAdminBo;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.oj.course.service.bo.CourseWebBo;
import com.cyberpeace.oj.practice.controller.PracticeMixinController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "oj-后台课程相关api")
@RestController
@RequestMapping("/oj/admin/course")
@RequiresAuthentication
public class AdminCourseController extends PracticeMixinController<Course> {

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseDirectionService courseDirectionService;

	@Override
	public Class<Course> getEntityCls() {
		return Course.class;
	}

	// LGD在使用 知识点和技能点绑定课程
	@GetMapping("/all/list")
	@ApiOperation("分页查询全部课程信息")
	public BaseRsp<List<CourseWebBo>>
			getList(@ApiParam(name = "listReq", value = "查询条件") @Valid CourseListReq listReq) {

		// 组装筛选条件
		CourseContext context = BeanUtils.copy(listReq, CourseContext.class);
		context.setUserId(UserInfoContext.getUserContext().getUserId());

		if (StringUtils.isNotBlank(listReq.getDirection())) {
			context.setDirections(Lists.newArrayList(listReq.getDirection()));
		}
		Page page = listReq.toPageParam();

		// 数据查询
		ListRsp<CourseInfoBo> courseInfos = courseService.selectPage(page, context, null);
		BaseRsp rsp = new BaseRsp(courseInfos);
		return rsp;
	}

	@ApiOperation("后台课程列表查询")
	@GetMapping("/list")
	public BaseRsp<List<CourseAdminBo>>
			getAdminCourse(@ApiParam(name = "listReq", value = "查询条件") CourseAdminListReq listReq) {

		// 数据组装
		CourseContext context = BeanUtils.copy(listReq, CourseContext.class);
		if (StringUtils.isNotBlank(listReq.getDirection())) {
			List<String> childrenIds = courseDirectionService
					.getAllChildrenIds(listReq.getDirection());
			context.setDirections(childrenIds);
		}

		context.setUserId(UserInfoContext.getUserContext().getUserId());
		Page page = listReq.toPageParam();
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(), false);

		// 数据查询
		ListRsp<CourseInfoBo> rsp = courseService.selectPage(page, context, authContext);
		return new BaseRsp(rsp);
	}

	@ApiOperation("课程资源新增或修改")
	@PostMapping("")
	public BaseRsp<Course> addCourse(
			@ApiParam(name = "addReq", value = "课程基本信息") @Valid @RequestBody CourseAddReq addReq) {

		String userId = UserInfoContext.getUserContext().getUserId();
		Course course = BeanUtils.copy(addReq, Course.class);
		Course courseRsp = courseService.addCourse(userId, course);
		return new BaseRsp<>(courseRsp);
	}

	@ApiOperation("课程删除")
	@DeleteMapping("")
	public BaseRsp<String> deleteCourse(@ApiParam(name = "resource_id",
			value = "课程编号") @RequestParam("resource_id") String courseId) {

		courseMapper.deleteById(courseId);
		return new BaseRsp(courseId);
	}

}
