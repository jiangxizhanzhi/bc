package com.cyberpeace.gateway.service.impl;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.gateway.service.bo.OfficeFileInfoBo;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.service.impl.CourseValidateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.gateway.service.OfficeViewService;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * 办公室Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码是一个Spring服务类 `OfficeServiceImpl` 的实现，实现了 `OfficeViewService` 接口。该服务的主要功能是获取特定课时（lesson）的讲义文件信息，并返回一个 `OfficeFileInfoBo` 对象。以下是对代码的简要分析： 
 * ### 类和依赖注入 
 * - **类定义**： 
 * - `OfficeServiceImpl` 继承了 `OfficeViewService` 接口。 
 * - 使用了 `@Slf4j` 注解，提供日志记录功能。 
 * - 使用了 `@Service` 注解，表示这是一个Spring管理的服务类。 
 * - **依赖注入**： 
 * - `courseLessonMapper`：用于数据库操作的 `CourseLessonMapper`。 
 * - `utilService`：提供各种实用方法的工具类。 
 * - `courseValidateHandler`：用于验证课程和课时存在性的处理器。 
 * ### 主要方法 
 * - **`lessonHandoutInfo` 方法**： 
 * - 输入参数：`String lessonId`（课时ID）。 
 * - 功能： 
 * 1. 使用 `courseValidateHandler` 验证课时是否存在。 
 * 2. 通过 `utilService.getRealPath` 获取讲义文件的真实路径。 
 * 3. 创建 `OfficeFileInfoBo` 对象并填充数据： 
 * - 文件的 SHA256 哈希值。 
 * - 创建者的用户ID。 
 * - 文件大小。 
 * - 文件名。 
 * - 版本号。 
 * 4. 如果在处理过程中发生异常，则抛出 `BusinessException` 异常，并记录错误日志。 
 * ### 错误处理 
 * - 在文件处理过程中，如果出现任何异常，会捕获异常并记录详细的错误信息（包括课时ID和文件路径），然后抛出 `BusinessException` 异常。 
 * ### 总结 
 * - 这段代码的主要目的是根据课时ID获取讲义文件的相关信息，并将这些信息封装到 `OfficeFileInfoBo` 对象中返回。 
 * - 代码结构清晰，依赖注入合理，异常处理也较为完善。 
 * - 使用了日志记录，便于调试和问题追踪。 
**/
@Slf4j
@Service
public class OfficeServiceImpl implements OfficeViewService {

	/**
	 * 课程课Mapper
	 */
	@Autowired
	private CourseLessonMapper courseLessonMapper;

	/**
	 * utilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 课程验证处理器
	 */
	@Autowired
	private CourseValidateHandler courseValidateHandler;

	/**
	 * 课程讲义信息
	 *
	 * @param lessonId 课id
	 * @return {@code OfficeFileInfoBo }
	 */
	@Override
	public OfficeFileInfoBo lessonHandoutInfo(String lessonId) {
		// 课时校验
		CourseLesson courseLesson = courseValidateHandler.validateLessonExist(lessonId, false);

		// 获取真实路径
		String realPath = utilService.getRealPath(courseLesson.getHandoutUrl());

		// 数据组装
		OfficeFileInfoBo officeFileInfoBo = new OfficeFileInfoBo();
		try {
			File handout = new File(realPath);
			String hash256 = FileUtil.getHash256(handout);
			officeFileInfoBo.setSHA256(hash256);
			officeFileInfoBo.setOwnerId(courseLesson.getCreateUserId());
			officeFileInfoBo.setSize(String.valueOf(handout.length()));
			officeFileInfoBo.setBaseFileName(FileUtil.getFileName(realPath, true));
			officeFileInfoBo.setVersion(CommonConstant.ONE.toString());
		} catch (Exception e) {
			log.error(
					"Failed to obtain office file information！lesson_id=[{}], file_path=[{}], catch error:",
					lessonId, realPath, e);
			throw new BusinessException(ErrorCode.FAILED_TO_OBTAIN_OFFICE_FILE_INFO);
		}

		return officeFileInfoBo;
	}
}

