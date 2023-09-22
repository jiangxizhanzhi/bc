package com.cyberpeace.oj.course.controller.web;

import java.util.List;

import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.service.bo.CourseWebBo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.course.controller.req.CourseListReq;
import com.cyberpeace.oj.course.controller.res.WebCourseRes;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.oj.course.service.bo.CourseQueryBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "oj-前台课程相关api")
@RestController
@RequestMapping("/oj/course")
@RequiresAuthentication
public class WebCourseController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseCategoryService courseDirectionService;

	@ApiOperation("前台课程列表查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<ListRsp<CourseWebBo>> getWebCourse(
			@ApiParam(name = "listReq", value = "查询条件") @Validated CourseListReq listReq) {
		// 数据组装
		CourseQueryBo context = BeanUtils.copy(listReq, CourseQueryBo.class);
		context.setUserId(UserInfoContext.getUserContext().getUserId());
		if (StringUtils.isNotBlank(listReq.getDirection())) {
			List<String> childrenIds = courseDirectionService.getChildrenIds(listReq.getDirection(),
					CommonConstant.TRUE);
			context.setDirections(childrenIds);
		}

		Page<Course> page = listReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);

		// 数据查询
		ListRsp<CourseWebBo> boListRsp = courseService.selectPage(page, context, authContext, true);
		return new BaseRsp<>(boListRsp);
	}

	@GetMapping("")
	@ApiOperation("单条查询")
	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<WebCourseRes> getCourse(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam("resource_id") String resourceId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		CourseInfoBo course = courseService.selectById(resourceId, authContext);

		WebCourseRes courseRes = BeanUtils.copy(course, WebCourseRes.class);
        return new BaseRsp<>(courseRes);
	}

}
