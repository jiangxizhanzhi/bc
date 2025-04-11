package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.oj.course.service.bo.CourseQueryBo;
import com.cyberpeace.oj.course.service.bo.CourseWebBo;

import java.util.List;

/**
 * 课程Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
 */
public interface CourseService extends IService<Course> {

	/**
	 * 根据编号查询课程信息
	 *
	 * @param courseId
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see CourseInfoBo
	 */
	CourseInfoBo selectById(String courseId, AuthContext authContext);

	/**
	 * 根据课程编号集合查询课程信息
	 *
	 * @param ids
	 * @return
	 * @throws
	 * @see List<String>
	 * @see List<CourseInfoBo>
	 */
	List<CourseInfoBo> selectByIds(List<String> ids);

	/**
	 * 根据课程条件信息查询
	 *
	 * @param page
	 * @param courseContext
	 * @param authContext   auth 上下文
	 * @param isWeb         是 Web
	 * @return
	 * @throws
	 * @see Page
	 * @see CourseQueryBo
	 * @see AuthContext
	 * @see boolean
	 * @see ListRsp<CourseWebBo>
	 */
	ListRsp<CourseWebBo> selectPage(Page page, CourseQueryBo courseContext,
									AuthContext authContext, boolean isWeb);

	/**
	 * 筛选未被删除的课程编号
	 *
	 * @param courseIds
	 * @return
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> idsByNotDeleteIds(List<String> courseIds);

	/**
	 * 课程新增
	 *
	 * @param userId
	 * @param course
	 * @return
	 * @throws
	 * @see String
	 * @see Course
	 * @see Course
	 */
	Course addCourse(String userId, Course course);

	/**
	 * 课程修改
	 *
	 * @param userId
	 * @param course
	 * @return
	 * @throws
	 * @see String
	 * @see Course
	 * @see Course
	 */
	Course updateCourse(String userId, Course course);

	/**
	 * 根据分类查询课程编号
	 *
	 * @param category
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see List<String>
	 */
	List<String> selectIdsByCategory(String category, AuthContext authContext);

	/**
	 * 批量删除 暂定课时记录不删除
	 *
	 * @param courseIds
	 * @param authContext auth 上下文
	 * @return
	 * @throws
	 * @see List<String>
	 * @see AuthContext
	 * @see Boolean
	 */
	Boolean batchDelete(List<String> courseIds, AuthContext authContext);

	/**
	 * 课程数据导出
	 *
	 * @param courseIds
	 * @param authContext
	 * @return 文件路径
	 * @throws
	 * @see List<String>
	 * @see AuthContext
	 * @see String
	 */
	String dumpCourse(List<String> courseIds, AuthContext authContext);

	/**
	 * 课程数据导入
	 *
	 * @param realPath
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see Boolean
	 */
	Boolean loadCourse(String realPath, AuthContext authContext);

	/**
	 * 获取一个随机的课程编号
	 *
	 * @param length 长度
	 * @return
	 * @throws
	 * @see int
	 * @see String
	 */
	String getRandomCourseNum(int length);

	/**
	 * 检查课程是否重复
	 *
	 * @param courseNames 课程名称
	 * @return
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> duplicateCourseInspect(List<String> courseNames);

	/**
	 * 清除用户身份验证
	 *
	 * @param user 用户
	 * @throws
	 * @see String
	 */
	void clearUserAuth(String user);

	/**
	 * 清除组织身份验证
	 *
	 * @param org 组织
	 * @throws
	 * @see String
	 */
	void clearOrgAuth(String org);

	/**
	 * 分页查询课程
	 *
	 * @param page
	 * @param courseContext
	 * @return
	 * @throws
	 * @see Page
	 * @see CourseQueryBo
	 * @see ListRsp<CourseWebBo>
	 */
	ListRsp<CourseWebBo> selectPage(Page page, CourseQueryBo courseContext);

	/**
	 * 导出课程,只导出json
	 *
	 * @param courseIds   课程id列表
	 * @param authContext auth 上下文
	 * @return {@code String }
	 * @throws
	 * @see List<String>
	 * @see AuthContext
	 * @see String
	 */
	String dumpCourseSlim(List<String> courseIds, AuthContext authContext);

	Boolean loadCourseSlim(String realPath, AuthContext authContext);
}
