package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.MdUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.CourseImportService;
import com.cyberpeace.oj.ojbase.constant.ResourceImportContant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CourseImportServiceImpl implements CourseImportService {

	@Autowired
	private CourseCategoryService courseCategoryService;

	@Override
	public Course parseRmCourse(String path) {

		// 解析md文件 获取对应数据
		File markdownFile = new File(path, ResourceImportContant.README_NAME);
		if (!markdownFile.exists() || markdownFile.isDirectory()) {
			log.error(
					"parseRmCourse: Markdown file not found or is directory under path! path=[{}]",
					path);
			throw new InvalidParamException("文件路径不正确！");
		}
		JSONObject markdownValue = MdUtil.parseJsonMarkdown(markdownFile);

		// 解析数据转换 由markdown的key-value转换为数据库对应数据
		JSONObject convertValue = JsonUtil.filedConvert(markdownValue,
				ResourceImportContant.COURSE_DATA_MAPPING);
		Course course = convertValue.toJavaObject(Course.class);

		// 用户修改
		course.setCreateUserId(ResourceImportContant.SUPER_ADMIN_HASH);

		// 课程分类数据查询或创建
		CourseCategory category = courseCategoryService.getOrCreate(course.getCategoryId());
		course.setCategoryId(category.getResourceId());

		// logo文件迁移
		File logoFile = new File(path, ResourceImportContant.PNG_LOGO);
		if (!logoFile.exists()) {
			logoFile = new File(path, ResourceImportContant.JPG_LOGO);
		}
		String dest = EnumUtils.getEnumDescByCode(AttachmentPath.LOGO.getCode(),
				AttachmentPath.class) + CommonConstant.LEFT_OBLIQUE_LINE + logoFile.getName();
		FileUtil.copyFile(logoFile.getAbsolutePath(), dest);
		course.setLogo(dest);

		// 课程大纲数据读取
		File outlineFile = new File(path, ResourceImportContant.OUTLINE_NAME);
		if (outlineFile.exists()) {
			String content = FileUtil.getContent(outlineFile);
			course.setOutline(content);
		}

		// 课程其他数据初始化
		course.setCourseType(CourseCategory.CourseType.BASE.getCode());
		course.setBuiltin(CommonConstant.TRUE);
		course.setHeat(CommonConstant.ZERO);
		course.setReadAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		course.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		course.setIsPublic(CommonConstant.FALSE);
		course.setScore(CommonConstant.ZERO);

		return course;
	}

	@Override
	public List<CourseLesson> parseRmLesson(Course course, String path,
			Map<String, String> envReqs) {
		return null;
	}

	@Override
	public List<LessonConfig> parseRmLessonConfig(Course course, String path) {
		return null;
	}
}
