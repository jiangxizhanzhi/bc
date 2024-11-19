package com.cyberpeace.rtks.service;

import java.util.List;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.controller.res.RelatedCourse;

/**
 * 相关课程Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public interface RelatedCourseService {

	/**
	 * 通过课程id字符串，获取所有课程名称和类型,如果课程存在删除情况，返回true
	 * 12.28修改了逻辑：如果存在课程不在远程接口中，调用本地课程接口获取内容，权限都是无。逻辑如下：
	 * 先查询已存在的课程id，
	 * 再获取不存在的课程id，
	 * 调用本地接口获取课程内容列表，
	 * 增加远程接口的课程列表
	 *
	 * @param userInfo
	 * @param courses           课程列表字符串,relatedCourseList 要返回的列表
	 * @param relatedCourseList 相关课程列表
	 * @param processBos
	 * @param relatedCourseType
	 * @return 是否存在删除的课程
	 * @throws
	 * @see UserInfo
	 * @see String
	 * @see List<RelatedCourse>
	 * @see List<CourseStudyProcessBo>
	 * @see String
	 * @see boolean
	 */

	boolean setCourseResult(UserInfo userInfo, String courses,
			List<RelatedCourse> relatedCourseList, List<CourseStudyProcessBo> processBos,
			String relatedCourseType);
}
