package com.cyberpeace.oj.course.controller;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.service.CourseDumpService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "oj-后台课程数据导出相关api")
@Slf4j
@RestController
@RequestMapping("/oj/admin/course")
public class AdminCourseDumpController {

	@Autowired
	private CourseDumpService courseDumpService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CourseService courseService;

	@ApiOperation("资管课程数据导出")
	@PostMapping("/rm/dump")
	public BaseRsp RMCourseDump(HttpServletResponse response) {

		// TODO 存放路径
		String path = "";
		File file = new File(path);


		UserInfo userInfo = remoteUserService.getById(ResourceImportConstant.SUPER_ADMIN_HASH);
		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);
		Map<String, String> envReqs = new HashMap<>();
		// 执行课程数据导出
		File result = courseDumpService.rMCourseDump(path,envReqs,authContext);
		FileUtil.setResponse(result, true, response);

		return new BaseRsp<>();
	}



}
