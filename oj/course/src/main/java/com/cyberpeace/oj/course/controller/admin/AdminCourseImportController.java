package com.cyberpeace.oj.course.controller.admin;

import java.io.File;
import java.util.List;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.req.DuplicateCourseInspectReq;
import com.cyberpeace.oj.course.controller.req.RMCombinedCourseImportReq;
import com.cyberpeace.oj.course.service.CourseDumpService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseDumpService courseDumpService;

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
	@ApiOperation("课程导出")
	@GetMapping("/rm/course/export")
	public BaseRsp<String> export(
			@RequestParam(value = "resource_id") String resourceId,
			@RequestParam(value = "is_dump_topo", required = false, defaultValue = "0") Integer isDumpTopo,
			@RequestParam(value = "scene_number", required = false) String sceneNumber,
			@RequestParam(value = "disable_image_export", required = false) Boolean disableImageExport) {
		boolean isDumpTopoVal = null != isDumpTopo && isDumpTopo == 1;
		String filePath = courseDumpService.rMCourseDump(resourceId, isDumpTopoVal, sceneNumber, disableImageExport);

		return new BaseRsp<>(filePath);
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

	@ApiOperation("课程重复校验")
	@PostMapping("/import/inspect_duplication")
	public BaseRsp<List<String>> duplicateCourseInspect(@RequestBody @Validated DuplicateCourseInspectReq courseNamesReq) {

		List<String> result = courseService.duplicateCourseInspect(courseNamesReq.getFileNames());

		return new BaseRsp<>(result);

	}

}
