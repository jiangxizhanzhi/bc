package com.cyberpeace.oj.course.controller.admin;

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
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.LessonConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

import java.io.File;
import java.util.List;
import java.util.Map;

@Api(tags = "后台-资管数据导入相关api")
@Slf4j
@RestController
@RequestMapping("/oj/admin")
public class AdminCourseImportController {

	@Autowired
	private CourseImportService courseImportService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseLessonService courseLessonService;

	@Autowired
	private LessonConfigService lessonConfigService;

	@ApiOperation("资管课程数据导入")
	@PostMapping("/rm/course/import")
	public BaseRsp<Boolean> zgCourseImport(@RequestBody RMCourseImportReq courseImportReq) {

		// 路径是否存在校验
		File file = new File(courseImportReq.getPath());
		if (!file.exists()) {
			log.error("zgCourseImport：File path not exist! path=[{}]", courseImportReq.getPath());
			throw new InvalidParamException("路径不存在！");
		}

		// 课程信息组装
		Course course = courseImportService.parseRmCourse(courseImportReq.getPath());

		// 课时信息组装
		List<CourseLesson> lessons = courseImportService.parseRmLesson(course,
				courseImportReq.getPath(), courseImportReq.getEnvReqs());
		Map<String, String> nameMap = StreamApiUtils.toMap(lessons, CourseLesson::getName,
				CourseLesson::getResourceId);
		// 目录数据组装
		List<LessonConfig> lessonConfigs = courseImportService.parseRmLessonConfig(course,
				courseImportReq.getPath(), nameMap);

		// 数据入库
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				courseMapper.insert(course);
				courseLessonService.saveBatch(lessons);
				lessonConfigService.saveBatch(lessonConfigs);
			}
		});

		return new BaseRsp<>(true);

	}

}
