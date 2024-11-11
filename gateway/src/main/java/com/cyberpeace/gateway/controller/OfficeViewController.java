package com.cyberpeace.gateway.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.cyberpeace.common.util.ObjUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.gateway.service.OfficeViewService;
import com.cyberpeace.gateway.service.bo.OfficeFileInfoBo;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.service.impl.CourseValidateHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 办公室视图Controller
 *
 * @author SN
 * @date 2024/11/11
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
