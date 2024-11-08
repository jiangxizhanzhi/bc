package com.cyberpeace.oj.course.controller.admin;

import java.io.File;
import java.util.List;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.req.DuplicateCourseInspectReq;
import com.cyberpeace.oj.course.controller.req.RMCombinedCourseImportReq;
import com.cyberpeace.oj.course.controller.req.RMCourseImportReq;
import com.cyberpeace.oj.course.service.CourseDumpService;
import com.cyberpeace.oj.course.service.CourseImportService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 管理课程进口Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Api(tags = "oj-后台课程数据导入相关api")
@Slf4j(topic = "resourceImport")
@RestController
@RequestMapping("/oj/admin/course")
public class AdminCourseImportController {

	/**
	 * 课程进口Service
	 */
	@Autowired
	private CourseImportService courseImportService;

	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 课程Service
	 */
	@Autowired
	private CourseService courseService;

	/**
	 * 课程转 储Service
	 */
	@Autowired
	private CourseDumpService courseDumpService;

	/**
	 * rmcourse 导入
	 *
	 * @param courseImportReq 课程进口请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("资管课程数据导入")
	@PostMapping("/rm/import")
	public BaseRsp<Boolean> RMCourseImport(@RequestBody RMCourseImportReq courseImportReq) {

		try {
			log.info("Resource management course import start! info=[{}]", JsonUtil.toJsonString(courseImportReq));

			// 路径是否存在校验
			File file = new File(courseImportReq.getPath());
			if (!file.exists()) {
				log.error("RMCourseImport：File path not exist! path=[{}]", courseImportReq.getPath());
				throw new BusinessException(ErrorCode.FILE_NOT_EXIST, courseImportReq.getPath());
			}

			UserInfo userInfo = remoteUserService.getById(ResourceImportConstant.SUPER_ADMIN_HASH);
			AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);
			UserInfoContext.setUserContext(userInfo);

			// 执行课程数据导入
			Boolean result = courseImportService.rMCourseImport(courseImportReq.getPath(),
					courseImportReq.getEnvReqs(),courseImportReq.getOperationEnvReqs(), authContext);
			return new BaseRsp<>(result);
		} catch (Exception e) {
			log.error("Course import failed! course_path=[{}] catch error:", courseImportReq.getPath(), e);
			courseImportService.destroyFailedTopo(courseImportReq.getEnvReqs(), courseImportReq.getOperationEnvReqs());
			throw e;
		}

	}

	/**
	 * 出口
	 *
	 * @param resourceId         资源id
	 * @param isDumpTopo         是转 储拓扑
	 * @param sceneNumber        场景数
	 * @param disableImageExport 禁用图像导出
	 * @return {@code BaseRsp<String> }
	 */
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

	/**
	 * rm组合课程导入
	 *
	 * @param courseImportReq 课程进口请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("资管组合课程数据导入")
	@PostMapping("/rm/import-combine")
	public BaseRsp<Boolean>
			RMCombinedCourseImport(@RequestBody RMCombinedCourseImportReq courseImportReq) {

		UserInfo userInfo = remoteUserService.getById(ResourceImportConstant.SUPER_ADMIN_HASH);
		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);

		// 执行课程数据导入
		Boolean result = courseImportService
				.rMCombinedCourseImport(courseImportReq.getCourseInfos(), authContext);

		return new BaseRsp<>(result);
	}

	/**
	 * 重复课程检查
	 *
	 * @param courseNamesReq 课程名字请求体
	 * @return {@code BaseRsp<List<String>> }
	 */
	@ApiOperation("课程重复校验")
	@PostMapping("/import/inspect_duplication")
	public BaseRsp<List<String>> duplicateCourseInspect(
			@RequestBody @Validated DuplicateCourseInspectReq courseNamesReq) {

		List<String> result = courseService.duplicateCourseInspect(courseNamesReq.getFileNames());

		return new BaseRsp<>(result);

	}

}
