package com.cyberpeace.oj.course.controller.admin;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 管理课时配置Controller
 *
 * @author SN
 * @date 2024/11/14
 */
@RestController
@RequestMapping("/oj/admin/lessonconfig")
@Api(tags = "oj-后台课程目录相关api")
@RequiresAuthentication
public class AdminLessonConfigController {

	/**
	 * 课时配置Service
	 */
	@Autowired
	private LessonConfigService lessonConfigService;

	/**
	 * 获取课时配置
	 *
	 * @param courseId 课程id
	 * @return {@code BaseRsp<LessonConfigBo> }
	 */
	@ApiOperation("后台课程目录查询")
	@GetMapping("")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<LessonConfigBo> getLessonConfig(@ApiParam(name = "course_id",
			value = "课程编号") @RequestParam("course_id") String courseId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		LessonConfigBo lessonConfig = lessonConfigService.getLessonConfig(courseId, authContext);
		return new BaseRsp<>(lessonConfig);
	}

	/**
	 * 设置课时配置
	 *
	 * @param configBo 配置Bo
	 * @return {@code BaseRsp<LessonConfigBo> }
	 */
	@ApiOperation("编排课时")
	@PutMapping("")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<LessonConfigBo> setLessonConfig(
			@ApiParam(name = "configBo", value = "课程编排信息") @RequestBody LessonConfigBo configBo) {

		LessonConfigBo lessonConfigBo = lessonConfigService.updateLessonConfig(configBo);
		return new BaseRsp<>(lessonConfigBo);
	}

}
