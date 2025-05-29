package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课配置Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonConfigMapper` 的接口，该接口继承自 `BaseMapper<LessonConfig>`，这是 MyBatis-Plus 提供的一个基础映射接口。通过这个接口，可以实现对 `LessonConfig` 实体类的操作。以下是对该接口方法的简要分析： 
 * 1. **getCourseNode**: 根据课程ID (`courseId`) 获取课程节点。 
 * 2. **listByParent**: 根据父节点ID (`parentId`) 列出所有的子目录配置信息。 
 * 3. **selectByCourse**: 根据课程ID (`courseId`) 查询所有相关的课程目录配置。 
 * 4. **selectLast**: 查找指定根目录ID (`rootId`) 下的最后一个课时配置。 
 * 5. **deleteByLessonIds**: 根据课时ID列表 (`lessonIds`) 删除对应的课时目录配置，并返回受影响的行数。 
 * 6. **selectByLessonIds**: 根据课程ID (`courseId`) 和课时ID列表 (`lessonIds`) 查询对应的课时目录配置。 
 * 7. **deleteByCourse**: 根据课程ID (`courseId`) 删除该课程的所有目录配置，并返回受影响的行数。 
 * 8. **batchDelete**: 根据课程ID列表 (`courseIds`) 批量删除课程目录。 
 * 9. **updateByCourseAndLesson**: 更新课时目录配置信息。 
 * 10. **selectByCourseAndLessonName**: 根据课程ID (`courseId`) 和课时名称 (`name`) 查询配置数据。 
 * 总结： 
 * - `LessonConfigMapper` 接口主要负责对 `LessonConfig` 实体类进行数据库操作，包括增删改查等基本功能。 
 * - 该接口使用了 MyBatis-Plus 的 `BaseMapper` 来简化数据库操作。 
 * - 方法名直观地反映了其功能，例如 `getCourseNode` 明确表示该方法用于获取课程节点信息。 
 * - 多个方法都使用了 `@Param` 注解来明确参数名称，这有助于提高代码的可读性和维护性。 
**/
@Mapper
public interface LessonConfigMapper extends BaseMapper<LessonConfig> {

	/**
	 * 获取课程节点
	 *
	 * @param courseId
	 * @return
	 * @throws
	 * @see String
	 * @see LessonConfig
	 */
	LessonConfig getCourseNode(@Param("courseId") String courseId);

	/**
	 * 根据父节点获取目录配置信息
	 *
	 * @param parentId
	 * @return
	 * @throws
	 * @see String
	 * @see List<LessonConfig>
	 */
	List<LessonConfig> listByParent(@Param("parentId") String parentId);

	/**
	 * 根据课程查询所有课程目录配置
	 *
	 * @param courseId
	 * @return
	 * @throws
	 * @see String
	 * @see List<LessonConfig>
	 */
	List<LessonConfig> selectByCourse(@Param("courseId") String courseId);

	/**
	 * 课程下的最后一个课时 第一级目录
	 *
	 * @param rootId
	 * @return
	 * @throws
	 * @see String
	 * @see LessonConfig
	 */
	LessonConfig selectLast(@Param("rootId") String rootId);

	/**
	 * 根据课时编号删除课时目录配置
	 *
	 * @param lessonIds
	 * @return
	 * @throws
	 * @see List<String>
	 * @see int
	 */
	int deleteByLessonIds(@Param("lessonIds") List<String> lessonIds);

	/**
	 * 根据课时编号查询
	 *
	 * @param courseId  课程id
	 * @param lessonIds
	 * @return
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see List<LessonConfig>
	 */
	List<LessonConfig> selectByLessonIds(@Param("courseId") String courseId,
			@Param("lessonIds") List<String> lessonIds);

	/**
	 * 删除课程目录
	 *
	 * @param courseId 课程id
	 * @return int
	 * @throws
	 * @see String
	 * @see int
	 */
	int deleteByCourse(String courseId);

	/**
	 * 批量删除课程目录
	 *
	 * @param courseIds 课程id列表
	 * @throws
	 * @see List<String>
	 */
	void batchDelete(@Param("courseIds") List<String> courseIds);

	/**
	 * 更新课时目录
	 *
	 * @param lessonConfig 课程配置
	 * @throws
	 * @see LessonConfig
	 */
	void updateByCourseAndLesson(@Param("lessonConfig") LessonConfig lessonConfig);

	/**
	 * 根据课程和名称查询配置数据
	 *
	 * @param courseId
	 * @param name
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	String selectByCourseAndLessonName(@Param("courseId") String courseId,
			@Param("name") String name);
}

