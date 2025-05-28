/**
 * 这段代码定义了一个名为 `ClassroomBo` 的 Java 类，该类用于表示课堂业务对象（BO）。以下是对该代码的简要分析： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **类名**：`ClassroomBo` 
 * - **实现接口**：`Serializable` 
 * ### 类注释 
 * - **作者**：SN 
 * - **版本**：1.0.0 
 * - **日期**：2024年11月8日 
 * ### 类特性 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter` 来自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 注解 `@ApiModel` 和 `@ApiModelProperty` 来描述该类和其属性，以便在 API 文档中使用。 
 * ### 属性 
 * 该类包含多个属性，每个属性都有相应的 Swagger 注解来描述其用途。以下是主要属性： 
 * - `resourceId`：资源编号 
 * - `startTime`：开始时间 
 * - `endTime`：结束时间 
 * - `courseId`：课程编号 
 * - `courseName`：课程名称 
 * - `courseType`：课程类型 
 * - `lessonId`：课时编号 
 * - `lessonName`：课时名称 
 * - `lessonType`：课时类型 
 * - `organizationId`：组织编号 
 * - `organizationName`：组织名称 
 * - `orgPath`：组织路径 
 * - `parentOrgIds`：父级组织编号列表 
 * - `createUserId`：创建人 
 * - `trainPlanId`：训练计划编号 
 * - `trainPlanName`：训练计划名称 
 * - `classify`：日程类型 
 * - `name`：日程名称 
 * - `roleGroup`：角色类型 
 * ### 总结 
 * - `ClassroomBo` 类主要用于存储和传输课堂相关的数据信息。 
 * - 通过 Swagger 注解，使得该类可以方便地在 API 文档中展示其结构和属性。 
 * - 使用 Lombok 注解简化了代码，减少了样板代码的编写。 
**/
package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课堂 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class ClassroomBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 开始时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("开始时间")
	private Date startTime;

	/**
	 * 结束时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("结束时间")
	private Date endTime;

	/**
	 * 课程id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String courseId;

	/**
	 * 课程名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程名称")
	private String courseName;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程类型")
	private Integer courseType;

	/**
	 * 课id
	 *
	 * @see String
	 */
	@ApiModelProperty("课时编号")
	private String lessonId;

	/**
	 * 课名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课时名称")
	private String lessonName;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时类型")
	private Integer lessonType;

	/**
	 * 组织id
	 *
	 * @see String
	 */
	@ApiModelProperty("组织编号")
	private String organizationId;

	/**
	 * 组织名称
	 *
	 * @see String
	 */
	@ApiModelProperty("组织名称")
	private String organizationName;

	/**
	 * 组织路径
	 *
	 * @see String
	 */
	@ApiModelProperty("组织路径")
	private String orgPath;

	/**
	 * 父母组织id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("父级组织编号")
	private List<String> parentOrgIds;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人")
	private String createUserId;

	/**
	 * 训练计划id
	 *
	 * @see String
	 */
	@ApiModelProperty("训练计划编号")
	private String trainPlanId;

	/**
	 * 训练计划名称
	 *
	 * @see String
	 */
	@ApiModelProperty("训练计划名称")
	private String trainPlanName;

	/**
	 * 分类
	 *
	 * @see Integer
	 */
	@ApiModelProperty("日程类型")
	private Integer classify;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("日程名称")
	private String name;

	/**
	 * 岗位群
	 *
	 * @see Integer
	 */
	@ApiModelProperty("角色类型")
	private Integer roleGroup;

}
