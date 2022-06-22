package com.cyberpeace.oj.course.controller.admin;

import java.io.File;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.req.RMCombinedCourseImportReq;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.course.controller.req.RMCourseImportReq;
import com.cyberpeace.oj.course.service.CourseImportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "oj-后台课程数据导入相关api")
@Slf4j
@RestController
@RequestMapping("/oj/admin/course")
public class AdminCourseImportController {

	@Autowired
	private CourseImportService courseImportService;

	@Autowired
	private RemoteUserService remoteUserService;

	@ApiOperation("资管课程数据导入")
	@PostMapping("/rm/import")
	public BaseRsp<Boolean> RMCourseImport(@RequestBody RMCourseImportReq courseImportReq) {

		// 路径是否存在校验
		File file = new File(courseImportReq.getPath());
		if (!file.exists()) {
			log.error("RMCourseImport：File path not exist! path=[{}]", courseImportReq.getPath());
			throw new BusinessException(ErrorCode.FILE_NOT_EXIST);
		}

		UserInfo userInfo = remoteUserService.getById(ResourceImportConstant.SUPER_ADMIN_HASH);
		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);

		// 执行课程数据导入
		Boolean result = courseImportService.rMCourseImport(courseImportReq.getPath(),
				courseImportReq.getEnvReqs(), authContext);

		return new BaseRsp<>(result);
	}

	@ApiOperation("资管组合课程数据导入")
	@PostMapping("/rm/import-combine")
	public BaseRsp<Boolean> RMCombinedCourseImport(@RequestBody RMCombinedCourseImportReq courseImportReq) {

		UserInfo userInfo = remoteUserService.getById(ResourceImportConstant.SUPER_ADMIN_HASH);
		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);

		// 执行课程数据导入
		Boolean result = courseImportService.rMCombinedCourseImport(courseImportReq.getCourseInfos(), authContext);

		return new BaseRsp<>(result);
	}

}
