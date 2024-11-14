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
 * OfficeService实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Slf4j
@Service
public class OfficeServiceImpl implements OfficeViewService {

	/**
	 * 课程课时Mapper
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
	 * 课时讲义信息
	 *
	 * @param lessonId 课时id
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
