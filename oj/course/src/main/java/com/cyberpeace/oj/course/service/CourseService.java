/**
 * 这段代码定义了一个名为 `CourseService` 的接口，该接口扩展了 `IService<Course>`。这个接口主要用于处理与课程相关的服务操作。以下是该接口的主要功能和方法的简要分析： 
 * ### 接口定义 
 * - **继承自 `IService<Course>`**：这表明 `CourseService` 接口扩展了 MyBatis-Plus 提供的 `IService` 接口，该接口提供了一些基本的 CRUD（创建、读取、更新、删除）操作。 
 * ### 方法描述 
 * 1. **`selectById(String courseId, AuthContext authContext)`** 
 * - 功能：根据课程编号查询课程信息。 
 * - 参数： 
 * - `courseId`：课程的唯一标识符。 
 * - `authContext`：认证上下文，可能用于权限控制。 
 * - 返回值：`CourseInfoBo`，表示课程信息。 
 * 2. **`selectByIds(List<String> ids)`** 
 * - 功能：根据多个课程编号查询课程信息。 
 * - 参数：`ids`：一个包含多个课程编号的列表。 
 * - 返回值：`List<CourseInfoBo>`，表示查询到的课程信息列表。 
 * 3. **`selectPage(Page page, CourseQueryBo courseContext, AuthContext authContext, boolean isWeb)`** 
 * - 功能：分页查询课程信息。 
 * - 参数： 
 * - `page`：分页对象。 
 * - `courseContext`：课程查询条件。 
 * - `authContext`：认证上下文。 
 * - `isWeb`：是否为 Web 端查询。 
 * - 返回值：`ListRsp<CourseWebBo>`，表示分页查询结果。 
 * 4. **`idsByNotDeleteIds(List<String> courseIds)`** 
 * - 功能：筛选未被删除的课程编号。 
 * - 参数：`courseIds`：课程编号列表。 
 * - 返回值：`List<String>`，表示未被删除的课程编号列表。 
 * 5. **`addCourse(String userId, Course course)`** 
 * - 功能：添加新的课程。 
 * - 参数： 
 * - `userId`：用户的唯一标识符。 
 * - `course`：课程实体对象。 
 * - 返回值：`Course`，表示添加后的课程对象。 
 * 6. **`updateCourse(String userId, Course course)`** 
 * - 功能：更新课程信息。 
 * - 参数： 
 * - `userId`：用户的唯一标识符。 
 * - `course`：课程实体对象。 
 * - 返回值：`Course`，表示更新后的课程对象。 
 * 7. **`selectIdsByCategory(String category, AuthContext authContext)`** 
 * - 功能：根据分类查询课程编号。 
 * - 参数： 
 * - `category`：课程分类。 
 * - `authContext`：认证上下文。 
 * - 返回值：`List<String>`，表示符合条件的课程编号列表。 
 * 8. **`batchDelete(List<String> courseIds, AuthContext authContext)`** 
 * - 功能：批量删除课程。 
 * - 参数： 
 * - `courseIds`：课程编号列表。 
 * - `authContext`：认证上下文。 
 * - 返回值：`Boolean`，表示删除操作是否成功。 
 * 9. **`dumpCourse(List<String> courseIds, AuthContext authContext)`** 
 * - 功能：导出课程数据。 
 * - 参数： 
 * - `courseIds`：课程编号列表。 
 * - `authContext`：认证上下文。 
 * - 返回值：文件路径。 
 * 10. **`loadCourse(String realPath, AuthContext authContext)`** 
 * - 功能：导入课程数据。 
 * - 参数： 
 * - `realPath`：文件路径。 
 * - `authContext`：认证上下文。 
 * - 返回值：`Boolean`，表示导入操作是否成功。 
 * 11. **`getRandomCourseNum(int length)`** 
 * - 功能：生成随机的课程编号。 
 * - 参数：`length`：编号长度。 
 * - 返回值：`String`，表示生成的随机课程编号。 
 * 12. **`duplicateCourseInspect(List<String> courseNames)`** 
 * - 功能：检查课程名称是否重复。 
 * - 参数：`courseNames`：课程名称列表。 
 * - 返回值：`List<String>`，表示重复的课程名称列表。 
 * 13. **`clearUserAuth(String user)`** 
 * - 功能：清除用户身份验证。 
 * - 参数：`user`：用户标识符。 
 * 14. **`clearOrgAuth(String org)`** 
 * - 功能：清除组织身份验证。 
 * - 参数：`org`：组织标识符。 
 * 15. **`selectPage(Page page, CourseQueryBo courseContext)`** 
 * - 功能：分页查询课程。 
 * - 参数： 
 * - `page`：分页对象。 
 * - `courseContext`：课程查询条件。 
 * - 返回值：`ListRsp<CourseWebBo>`，表示分页查询结果。 
 * 16. **`dumpCourseSlim(List<String> courseIds, AuthContext authContext)`** 
 * - 功能：导出课程数据（仅导出 JSON）。 
 * - 参数： 
 * - `courseIds`：课程编号列表。 
 * - `authContext`：认证上下文。 
 * - 返回值：`String`，表示文件路径。 
 * 17. **`loadCourseSlim(String realPath, AuthContext authContext)`** 
 * - 功能：导入课程数据（仅导入 JSON）。 
 * - 参数： 
 * - `realPath`：文件路径。 
 * - `authContext`：认证上下文。 
 * - 返回值：`Boolean`，表示导入操作是否成功。 
 * ### 总结 
 * 该接口提供了全面的课程管理功能，包括查询、添加、更新、删除、导出和导入课程数据等。这些方法覆盖了课程管理的各个方面，并且通过 `AuthContext` 参数支持了权限控制的需求。 
**/
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
