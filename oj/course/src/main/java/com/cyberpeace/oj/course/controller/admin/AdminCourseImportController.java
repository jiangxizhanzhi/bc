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
 * 管理课程导入Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `AdminCourseImportController` 的 Spring Boot 控制器类，用于处理与课程导入相关的 API 请求。以下是该代码的简要分析： 
 * ### 类和包结构 
 * - **包名**: `com.cyberpeace.oj.course.controller.admin` 
 * - **类名**: `AdminCourseImportController` 
 * ### 主要功能 
 * 该控制器提供了以下几个主要功能： 
 * 1. **课程导入**：支持单个课程和组合课程的导入。 
 * 2. **课程导出**：将课程数据导出为文件。 
 * 3. **课程重复检查**：检查课程名称是否重复。 
 * ### 关键属性 
 * - **依赖注入**: 
 * - `CourseImportService`: 用于处理课程导入逻辑。 
 * - `RemoteUserService`: 用于获取用户信息。 
 * - `CourseService`: 用于处理课程相关业务逻辑。 
 * - `CourseDumpService`: 用于处理课程数据导出逻辑。 
 * ### 方法 
 * #### 1. `RMCourseImport` 
 * - **路径**: `/oj/admin/course/rm/import` 
 * - **方法类型**: `POST` 
 * - **功能**: 导入单个课程数据。 
 * - **参数**: `RMCourseImportReq` 请求体。 
 * - **返回值**: `BaseRsp<Boolean>`。 
 * #### 2. `export` 
 * - **路径**: `/oj/admin/course/rm/course/export` 
 * - **方法类型**: `GET` 
 * - **功能**: 导出课程数据。 
 * - **参数**: 
 * - `resource_id`: 资源ID。 
 * - `is_dump_topo`: 是否转储拓扑。 
 * - `scene_number`: 场景数。 
 * - `disable_image_export`: 是否禁用图像导出。 
 * - **返回值**: `BaseRsp<String>`。 
 * #### 3. `RMCombinedCourseImport` 
 * - **路径**: `/oj/admin/course/rm/import-combine` 
 * - **方法类型**: `POST` 
 * - **功能**: 导入组合课程数据。 
 * - **参数**: `RMCombinedCourseImportReq` 请求体。 
 * - **返回值**: `BaseRsp<Boolean>`。 
 * #### 4. `duplicateCourseInspect` 
 * - **路径**: `/oj/admin/course/import/inspect_duplication` 
 * - **方法类型**: `POST` 
 * - **功能**: 检查课程名称是否重复。 
 * - **参数**: `DuplicateCourseInspectReq` 请求体。 
 * - **返回值**: `BaseRsp<List<String>>`。 
 * ### 日志记录 
 * 使用了 `lombok` 注解 `@Slf4j` 来简化日志记录，日志记录在每个方法中用于记录关键步骤和错误信息。 
 * ### 异常处理 
 * - 使用 `try-catch` 块来捕获异常，并记录错误信息。 
 * - 在某些情况下，调用 `destroyFailedTopo` 方法清理失败的数据。 
 * ### 总结 
 * 该控制器类主要用于处理课程导入、导出和重复检查的功能。通过依赖注入的方式，它能够与其他服务进行交互，实现复杂的业务逻辑。每个方法都有详细的注释和日志记录，确保了代码的可读性和可维护性。 
**/
@Api(tags = "oj-后台课程数据导入相关api")
@Slf4j(topic = "resourceImport")
@RestController
@RequestMapping("/oj/admin/course")
public class AdminCourseImportController {

	/**
	 * 课程导入Service
	 *
	 * @see CourseImportService
	 */
	@Autowired
	private CourseImportService courseImportService;

	/**
	 * 远程用户Service
	 *
	 * @see RemoteUserService
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 课程Service
	 *
	 * @see CourseService
	 */
	@Autowired
	private CourseService courseService;

	/**
	 * 课程转 储Service
	 *
	 * @see CourseDumpService
	 */
	@Autowired
	private CourseDumpService courseDumpService;

	/**
	 * rmcourse 导入
	 *
	 * @param courseImportReq 课程导入请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see RMCourseImportReq
	 * @see BaseRsp<Boolean>
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
	 * 导出
	 *
	 * @param resourceId         资源id
	 * @param isDumpTopo         是转 储拓扑
	 * @param sceneNumber        场景数
	 * @param disableImageExport 禁用图像导出
	 * @return {@code BaseRsp<String> }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see String
	 * @see Boolean
	 * @see BaseRsp<String>
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
	 * @param courseImportReq 课程导入请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see RMCombinedCourseImportReq
	 * @see BaseRsp<Boolean>
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
	 * @throws
	 * @see DuplicateCourseInspectReq
	 * @see BaseRsp<List<String>>
	 */
	@ApiOperation("课程重复校验")
	@PostMapping("/import/inspect_duplication")
	public BaseRsp<List<String>> duplicateCourseInspect(
			@RequestBody @Validated DuplicateCourseInspectReq courseNamesReq) {

		List<String> result = courseService.duplicateCourseInspect(courseNamesReq.getFileNames());

		return new BaseRsp<>(result);

	}

}

