package com.cyberpeace.oj.course.service.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 课程 dump load bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseDumpLoadBo` 的 Java 类，该类用于表示课程的详细信息，并实现了 `Serializable` 接口。以下是该类的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **实现接口**：`Serializable` 
 * ### 类属性 
 * - **基本属性**： 
 * - `name`：课程名称（字符串） 
 * - `description`：课程描述（字符串） 
 * - `categoryName`：类别名称（字符串） 
 * - `logo`：图标（字符串） 
 * - `outline`：大纲（字符串） 
 * - `difficulty`：难度（整数） 
 * - `rank`：等级（整数） 
 * - `createUserId`：创建用户ID（字符串） 
 * - `modifyUserId`：修改用户ID（字符串） 
 * - `isPublic`：是否公开（布尔值） 
 * - `courseType`：课程类型（整数） 
 * - `lecturer`：讲师（字符串） 
 * - `score`：得分（整数） 
 * - `role`：岗位（整数） 
 * - `courseNum`：课程数量（字符串） 
 * - **复杂属性**： 
 * - `lesson`：课程包含的课（列表，元素类型为 `LessonDumpLoadBo`） 
 * - `lessonConfig`：课程配置（类型为 `LessonConfigDumpBo`） 
 * - `filePathMap`：文件路径映射（键值对，键和值都是字符串） 
 * ### 注解 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * ### 功能 
 * - `CourseDumpLoadBo` 类主要用于存储和传输课程相关信息，支持序列化以便在不同系统之间传递数据。 
 * - 该类包含了丰富的课程细节信息，如名称、描述、难度等，还包含了与课程相关的其他对象（如 `LessonDumpLoadBo` 和 `LessonConfigDumpBo`）。 
 * ### 总结 
 * 这个类是一个典型的业务对象（BO），用于封装课程的详细信息。它不仅包含了基础的信息字段，还包含了与课程相关的复杂对象，便于在不同的业务场景中使用。 
**/
@Getter
@Setter
public class CourseDumpLoadBo implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 类别名称
	 *
	 * @see String
	 */
	private String categoryName;

	/**
	 * 图标
	 *
	 * @see String
	 */
	private String logo;

	/**
	 * 大纲
	 *
	 * @see String
	 */
	private String outline;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	private Integer difficulty;

	/**
	 * 等级
	 *
	 * @see Integer
	 */
	private Integer rank;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	private String createUserId;

	/**
	 * 修改用户id
	 *
	 * @see String
	 */
	private String modifyUserId;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	private Boolean isPublic;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	private Integer courseType;

	/**
	 * 讲师
	 *
	 * @see String
	 */
	private String lecturer;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	private Integer score;

	/**
	 * 岗位
	 *
	 * @see Integer
	 */
	private Integer role;

	/**
	 * 课程数量
	 *
	 * @see String
	 */
	private String courseNum;

	/**
	 * 课
	 *
	 * @see List<LessonDumpLoadBo>
	 */
	private List<LessonDumpLoadBo> lesson;

	/**
	 * 课程配置
	 *
	 * @see LessonConfigDumpBo
	 */
	private LessonConfigDumpBo lessonConfig;

	/**
	 * 文件路径图
	 *
	 * @see Map<String, String>
	 */
	private Map<String, String> filePathMap;
}

