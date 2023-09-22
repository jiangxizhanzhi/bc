package com.cyberpeace.oj.course.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.oj.course.service.bo.CourseQueryBo;
import com.cyberpeace.oj.course.service.bo.CourseWebBo;

public interface CourseService extends IService<Course> {

	/**
	 * 根据编号查询课程信息
	 * @param courseId
	 * @param authContext
	 * @return
	 */
	CourseInfoBo selectById(String courseId, AuthContext authContext);

	/**
	 * 根据课程编号集合查询课程信息
	 * @param ids
	 * @return
	 */
	List<CourseInfoBo> selectByIds(List<String> ids);

	/**
	 * 根据课程条件信息查询
	 * @param page
	 * @param courseContext
	 * @return
	 */
	ListRsp<CourseWebBo> selectPage(Page<Course> page, CourseQueryBo courseContext,
									AuthContext authContext, boolean isWeb);

	/**
	 * 筛选未被删除的课程编号
	 * @param courseIds
	 * @return
	 */
	List<String> idsByNotDeleteIds(List<String> courseIds);

	/**
	 * 课程新增
	 * @param userId
	 * @param course
	 * @return
	 */
	Course addCourse(String userId, Course course);

	/**
	 * 课程修改
	 * @param userId
	 * @param course
	 * @return
	 */
	Course updateCourse(String userId, Course course);

	/**
	 * 根据分类查询课程编号
	 * @param category
	 * @param authContext
	 * @return
	 */
	List<String> selectIdsByCategory(String category, AuthContext authContext);

	/**
	 * 批量删除 暂定课时记录不删除
	 * @param courseIds
	 * @return
	 */
	Boolean batchDelete(List<String> courseIds, AuthContext authContext);

	/**
	 * 课程数据导出
	 * @param courseIds
	 * @param authContext
	 * @return 文件路径
	 */
	String dumpCourse(List<String> courseIds, AuthContext authContext);

	/**
	 * 课程数据导入
	 * @param realPath
	 * @param authContext
	 * @return
	 */
	Boolean loadCourse(String realPath, AuthContext authContext);

	/**
	 * 获取一个随机的课程编号
	 * @return
	 */
	String getRandomCourseNum(int length);

	/**
	 * 检查课程是否重复
	 * @return
	 */
	List<String> duplicateCourseInspect(List<String> courseNames);

	void clearUserAuth(String user);

	void clearOrgAuth(String org);
}
