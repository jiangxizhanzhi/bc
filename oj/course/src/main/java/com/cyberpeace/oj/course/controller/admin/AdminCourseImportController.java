package com.cyberpeace.oj.course.controller.admin;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.service.PaperService;
import com.cyberpeace.oj.practice.service.bo.PaperContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.controller.req.RMCourseImportReq;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseImportService;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.LessonConfigService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "oj-后台课程数据导入相关api")
@Slf4j
@RestController
@RequestMapping("/oj/admin")
public class AdminCourseImportController {

	@Autowired
	private CourseImportService courseImportService;

	@ApiOperation("资管课程数据导入")
	@PostMapping("/rm/course/import")
	public BaseRsp<Boolean> RMCourseImport(@RequestBody RMCourseImportReq courseImportReq) {

		// 路径是否存在校验
		File file = new File(courseImportReq.getPath());
		if (!file.exists()) {
			log.error("RMCourseImport：File path not exist! path=[{}]", courseImportReq.getPath());
			throw new InvalidParamException("路径不存在！");
		}

		// 执行课程数据导入
		Boolean result = courseImportService.rMCourseImport(courseImportReq.getPath(), courseImportReq.getEnvReqs());

		return new BaseRsp<>(result);
	}

}
