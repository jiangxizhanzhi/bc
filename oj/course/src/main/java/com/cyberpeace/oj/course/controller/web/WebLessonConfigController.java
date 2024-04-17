package com.cyberpeace.oj.course.controller.web;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.integratation.service.impl.RemoteHaiboTokenService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

@Api(tags = "oj-前台课程目录相关api")
@RestController
@RequestMapping("/oj/lessonconfig")
@RequiresAuthentication
public class WebLessonConfigController {

	@Autowired
	private LessonConfigService lessonConfigService;

	@GetMapping("")
	@ApiOperation("课程目录查询")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE}, logical = Logical.OR)
	public BaseRsp<LessonConfigBo> getLessonConfig(@ApiParam(name = "course_id",
			value = "课程编号") @RequestParam("course_id") String courseId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);
		if (authContext.isHaiboUser()) {
			List<String> courses = RemoteHaiboTokenService.getValidCourses(authContext.getHaiboToken());
			if (!courses.contains(courseId)) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
			}
		}
		LessonConfigBo config = lessonConfigService.getLessonConfig(courseId, authContext);
		return new BaseRsp<>(config);
	}

}
