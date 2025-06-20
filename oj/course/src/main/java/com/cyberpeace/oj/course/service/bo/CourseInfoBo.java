package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 课程信息 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseInfoBo` 的 Java 类，用于表示课程的基本信息。以下是对该类的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **继承**：实现 `Serializable` 接口，支持序列化。 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 提供，自动生成 getter 和 setter 方法。 
 * - `@ApiModel`：用于 Swagger 文档生成，描述该对象为“课程基本信息”。 
 * ### 类成员变量 
 * - **静态常量**：`DEFAULT_WEIGHT`（默认权重） 
 * - **属性**：包括课程编号、名称、是否公开、描述、类别ID、图标、大纲、难度、创建者ID、修改者ID、课程类型、热度、讲师、学分、课程编号、方向名称、课时数量、课时时长、理论课时数量、实验课时数量、建议学习时长、是否收藏、权重、是否有访问权限、岗位等。 
 * ### 属性说明 
 * - 每个属性都有对应的注释和 Swagger 注解 (`@ApiModelProperty`)，用于描述属性的作用和在文档中的展示名称。 
 * ### 总结 
 * - `CourseInfoBo` 是一个数据传输对象 (DTO)，用于封装课程的相关信息。 
 * - 该类主要用于存储课程的基本信息，并且支持序列化以便在网络传输中使用。 
 * - 使用 Lombok 减少了样板代码，使得代码更加简洁。 
 * - Swagger 注解使该类在 API 文档中更易被理解和使用。 
**/
@Getter
@Setter
@ApiModel("课程基本信息")
public class CourseInfoBo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 默认权重
	 *
	 * @see Integer
	 */
	public final static Integer DEFAULT_WEIGHT = 1;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty("课程简介")
	private String description;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程方向")
	private String categoryId;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty("课程海报")
	private String logo;

	/**
	 * 大纲
	 *
	 * @see String
	 */
	@ApiModelProperty("课程大纲")
	private String outline;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程难度")
	private Integer difficulty;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程创建人")
	private String createUserId;

	/**
	 * 修改用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程最后编辑人")
	private String modifyUserId;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程类型 0=")
	private Integer courseType;

	/**
	 * 热
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程热度")
	private Integer heat;

	/**
	 * 讲师
	 *
	 * @see String
	 */
	@ApiModelProperty("课程讲师")
	private String lecturer;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程学分")
	private Integer score;

	/**
	 * 课程数量
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String courseNum;

	/**
	 * 方向名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程方向名称")
	private String directionName;

	/**
	 * 课计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程课时数量")
	private Integer lessonCount;

	/**
	 * 课程时间
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程课时时长")
	private Integer courseDuration;

	/**
	 * 理论计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("理论课时数量")
	private Integer theoryCount;

	/**
	 * 锻炼计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程实验课时数量")
	private Integer exerciseCount;

	/**
	 * 建议持续时间
	 *
	 * @see Integer
	 */
	@ApiModelProperty("建议学习时长")
	private Integer suggestDuration;

	/**
	 * is 集合
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 重量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程权重")
	private Integer weight = DEFAULT_WEIGHT;

	/**
	 * 面试
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否具有访问权限")
	private Boolean interview;

	/**
	 * 岗位
	 *
	 * @see Integer
	 */
	private Integer role;
}

