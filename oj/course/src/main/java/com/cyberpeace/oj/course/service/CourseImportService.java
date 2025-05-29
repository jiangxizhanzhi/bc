package com.cyberpeace.oj.course.service;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.cyberpeace.oj.course.service.bo.CombinedCourseImportContext;

import java.util.List;
import java.util.Map;

/**
 * 课程导入Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseImportService` 的接口，该接口包含了多个方法，用于处理课程导入相关的功能。以下是该接口的简要分析： 
 * ### 接口概述 
 * - **包名**：`com.cyberpeace.oj.course.service` 
 * - **接口名**：`CourseImportService` 
 * - **作者**：SN 
 * - **版本**：1.0.0 
 * - **日期**：2024年11月8日 
 * ### 方法列表 
 * 1. **parseRmCourse** 
 * - **描述**：根据导入数据路径获取课程信息。 
 * - **参数**： 
 * - `path`：字符串类型，表示导入数据的路径。 
 * - **返回值**：`Course` 对象。 
 * 2. **parseRmLesson** 
 * - **描述**：组装资源导入的课时信息。 
 * - **参数**： 
 * - `course`：`Course` 对象，表示课程信息。 
 * - `path`：字符串类型，表示课程文件地址。 
 * - `envReqs`：`Map<String, String>` 类型，表示环境需求。 
 * - `nameMap`：`Map<String, String>` 类型，表示名称映射。 
 * - **返回值**：`List<CourseLesson>` 对象列表。 
 * 3. **parseRmLessonConfig** 
 * - **描述**：组装课时路径信息。 
 * - **参数**： 
 * - `course`：`Course` 对象，表示课程信息。 
 * - `path`：字符串类型，表示课程文件地址。 
 * - `nameMap`：`Map<String, String>` 类型，表示名称映射。 
 * - **返回值**：`List<LessonConfig>` 对象列表。 
 * 4. **rMCourseImport** 
 * - **描述**：课程数据导入。 
 * - **参数**： 
 * - `path`：字符串类型，表示课程资源所在地址。 
 * - `envReqs`：`Map<String, String>` 类型，表示环境需求。 
 * - `operationEnvReqs`：`Map<String, Map<String, String>>` 类型，表示操作环境需求。 
 * - `authContext`：`AuthContext` 对象，表示认证上下文。 
 * - **返回值**：布尔值 (`Boolean`)，表示导入是否成功。 
 * 5. **rMCombinedCourseImport** 
 * - **描述**：组合课程数据导入。 
 * - **参数**： 
 * - `courseInfos`：`List<CombinedCourseImportContext>` 对象列表，表示课时资源所在地址。 
 * - `authContext`：`AuthContext` 对象，表示认证上下文。 
 * - **返回值**：布尔值 (`Boolean`)，表示导入是否成功。 
 * 6. **destroyFailedTopo** 
 * - **描述**：用于课程导入失败后，将topo进行删除操作。 
 * - **参数**： 
 * - `envReqs`：`Map<String, String>` 类型，表示环境需求。 
 * - `operationEnvReqs`：`Map<String, Map<String, String>>` 类型，表示操作环境需求。 
 * - **返回值**：无（`void`）。 
 * ### 总结 
 * - 该接口主要处理课程数据的导入、解析以及在导入失败后的清理工作。 
 * - 每个方法都有详细的注释，描述了其功能、参数和返回值。 
 * - 方法涉及到了课程 (`Course`)、课时 (`CourseLesson`)、课时配置 (`LessonConfig`) 等对象，并且使用了 `Map` 和 `List` 等集合类来处理复杂的数据结构。 
 * - 使用了认证上下文 (`AuthContext`) 来确保操作的安全性。 
**/
public interface CourseImportService {

	/**
	 * 根据导入数据路径获取课程信息
	 *
	 * @param path
	 * @return
	 * @throws
	 * @see String
	 * @see Course
	 */
	Course parseRmCourse(String path);

	/**
	 * 组装资源导入的课时信息
	 *
	 * @param course
	 * @param path
	 * @param envReqs
	 * @param nameMap 名称地图
	 * @return
	 * @throws
	 * @see Course
	 * @see String
	 * @see Map<String, String>
	 * @see Map<String, String>
	 * @see List<CourseLesson>
	 */
	List<CourseLesson> parseRmLesson(Course course, String path, Map<String, String> envReqs,
			Map<String, String> nameMap);

	/**
	 * 组装课时路径信息
	 *
	 * @param course  课程信息
	 * @param path    课程文件地址
	 * @param nameMap key=名称 value=课时编号
	 * @return
	 * @throws
	 * @see Course
	 * @see String
	 * @see Map<String, String>
	 * @see List<LessonConfig>
	 */
	List<LessonConfig> parseRmLessonConfig(Course course, String path, Map<String, String> nameMap);

	/**
	 * 课程数据导入
	 *
	 * @param path             课程资源所在地址
	 * @param envReqs          topo与课时绑定数据 key=课时文件夹名称 value=topo编号
	 * @param operationEnvReqs
	 * @param authContext      auth 上下文
	 * @return
	 * @throws
	 * @see String
	 * @see Map<String, String>
	 * @see Map<String, Map<String, String>>
	 * @see AuthContext
	 * @see Boolean
	 */
	Boolean rMCourseImport(String path, Map<String, String> envReqs, Map<String, Map<String, String>> operationEnvReqs, AuthContext authContext);

	/**
	 * 组合课程数据导入
	 *
	 * @param courseInfos 课时资源所在地址
	 * @param authContext auth 上下文
	 * @return
	 * @throws
	 * @see List<CombinedCourseImportContext>
	 * @see AuthContext
	 * @see Boolean
	 */
	Boolean rMCombinedCourseImport(List<CombinedCourseImportContext> courseInfos, AuthContext authContext);

	/**
	 * 用于课程导入失败后，将topo进行删除操作
	 *
	 * @param envReqs
	 * @param operationEnvReqs 实操环境要求
	 * @throws
	 * @see Map<String, String>
	 * @see Map<String, Map<String, String>>
	 */
	void destroyFailedTopo(Map<String, String> envReqs, Map<String, Map<String, String>> operationEnvReqs);
}

