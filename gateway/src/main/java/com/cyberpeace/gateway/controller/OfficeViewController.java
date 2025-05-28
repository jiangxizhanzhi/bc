/**
 * 这段代码定义了一个名为 `OfficeViewController` 的 Spring MVC 控制器，用于处理与办公室视图相关的请求。该控制器主要提供了两个功能：获取文件信息和获取文件流。 
 * ### 主要组成部分 
 * 1. **依赖注入** 
 * - `OfficeViewService`: 用于处理与办公室视图相关的业务逻辑。 
 * - `CourseValidateHandler`: 用于验证课程是否存在。 
 * - `UtilService`: 提供一些实用工具方法。 
 * 2. **API 方法** 
 * - **获取文件信息 (`getFileInfo`)** 
 * - URL: `/gateway/office/oj/lesson/handout/info` 
 * - HTTP 方法: GET 
 * - 参数: 
 * - `fileId`: 课时编号 
 * - 返回值: 包含文件信息的 `Map<String, Object>` 
 * - 功能: 根据课时编号从 `OfficeViewService` 获取文件信息，并将其转换为 `Map<String, Object>` 类型返回。 
 * - **获取文件流 (`getFileStream`)** 
 * - URL: `/gateway/office/oj/lesson/handout` 
 * - HTTP 方法: GET 
 * - 参数: 
 * - `fileId`: 课时编号 
 * - 返回值: `BaseRsp` 
 * - 异常: `FileNotFoundException` 
 * - 功能: 
 * - 验证课程是否存在。 
 * - 获取文件的实际路径。 
 * - 检查文件是否存在，如果不存在则抛出 `FileNotFoundException`。 
 * - 设置响应头并返回文件流。 
 * - 返回一个空的 `BaseRsp` 对象。 
 * ### 代码结构和设计 
 * - **注解使用** 
 * - 使用了 `@Slf4j` 注解来自动生成日志记录器。 
 * - 使用了 `@RestController` 和 `@RequestMapping` 注解来定义控制器和基础路径。 
 * - 使用了 `@GetMapping` 和 `@ApiOperation` 注解来定义 RESTful API 并生成 Swagger 文档。 
 * - **异常处理** 
 * - 在 `getFileStream` 方法中，如果文件不存在，则抛出 `FileNotFoundException` 并记录错误日志。 
 * - **工具类使用** 
 * - `ObjUtil.obj2map`: 将对象转换为 `Map<String, Object>`。 
 * - `FileUtil.setResponse`: 设置 HTTP 响应以返回文件流。 
 * ### 总结 
 * 这个控制器主要用于处理与课程课时相关的文件操作，包括获取文件信息和文件流。通过依赖注入，它能够方便地调用其他服务层的方法来完成具体的业务逻辑。整体设计清晰，注解使用合理，易于理解和维护。 
**/
package com.cyberpeace.gateway.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.ObjUtil;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.gateway.service.OfficeViewService;
import com.cyberpeace.gateway.service.bo.OfficeFileInfoBo;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.service.impl.CourseValidateHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * 办公室视图Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Api(tags = "外部接口-office文件展示相关api")
@RestController
@RequestMapping("/gateway/office/")
public class OfficeViewController {

	/**
	 * 办公室视图Service
	 */
	@Autowired
	private OfficeViewService officeViewService;

	/**
	 * 课程验证处理器
	 */
	@Autowired
	private CourseValidateHandler courseValidateHandler;

	/**
	 * utilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 获取文件信息
	 *
	 * @param lessonId 课id
	 * @return {@code Map<String, Object> }
	 */
	@ApiOperation("获取课时文件信息")
	@GetMapping("/oj/lesson/handout/info")
	public Map<String, Object> getFileInfo(
			@ApiParam(name = "fileId", value = "课时编号") @RequestParam("fileId") String lessonId) {

		OfficeFileInfoBo officeFileInfoBo = officeViewService.lessonHandoutInfo(lessonId);
        return ObjUtil.obj2map(officeFileInfoBo);
	}

	/**
	 * 获取文件流
	 *
	 * @param lessonId 课id
	 * @param response 响应
	 * @return {@code BaseRsp }
	 * @throws FileNotFoundException 文件未找到异常
	 */
	@ApiOperation("获取课时文件流")
	@GetMapping("/oj/lesson/handout")
	public BaseRsp getFileStream(
			@ApiParam(name = "fileId", value = "课时编号") @RequestParam("fileId") String lessonId,
			HttpServletResponse response) throws FileNotFoundException {

		CourseLesson courseLesson = courseValidateHandler.validateLessonExist(lessonId, false);
		String realPath = utilService.getRealPath(courseLesson.getHandoutUrl());

		File file = new File(realPath);
		if (!file.exists()) {
			log.error("File not found! real_path=[{}]", realPath);
			throw new FileNotFoundException(realPath);
		}

		FileUtil.setResponse(file, false, response);
		return new BaseRsp<>();
	}

}
