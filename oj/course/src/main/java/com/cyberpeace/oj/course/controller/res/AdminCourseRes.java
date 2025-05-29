package com.cyberpeace.oj.course.controller.res;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.xdevapi.JsonArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 管理课程结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `AdminCourseRes` 的 Java 类，用于表示管理课程的结果。以下是详细的分析和总结： 
 * ### 类定义 
 * - **包声明**：该类位于 `com.cyberpeace.oj.course.controller.res` 包中。 
 * - **导入语句**： 
 * - 导入了 `com.alibaba.fastjson.JSONArray`，用于处理 JSON 数组。 
 * - 导入了 `com.mysql.cj.xdevapi.JsonArray`，但这个导入可能是错误的，因为通常不会在 Java 应用程序中同时使用 Alibaba 的 `JSONArray` 和 MySQL 的 `JsonArray`。建议检查并移除不必要的导入。 
 * - 导入了 Swagger 相关注解，用于生成 API 文档。 
 * - 导入了 Lombok 注解，用于简化代码。 
 * ### 类注释 
 * - 类注释说明了这是一个用于管理课程结果的类，并提供了作者、版本和日期信息。 
 * ### 类属性 
 * - 类属性包括： 
 * - `resourceId`: 课程资源编号 
 * - `name`: 课程名称 
 * - `description`: 课程简介 
 * - `logo`: 课程封面 
 * - `score`: 学分 
 * - `categoryId`: 课程分类 
 * - `outline`: 课程大纲 
 * - `difficulty`: 课程难度 
 * - `rank`: 等级 
 * - `attackTag`: 攻击知识点（使用 `JSONArray`） 
 * - `defenseTag`: 防御知识点（使用 `JSONArray`） 
 * - `courseType`: 课程类型 
 * - `isPublic`: 是否公开 
 * - `lecturer`: 课程讲师 
 * - `courseNum`: 课程编号 
 * - `role`: 岗位 
 * ### Lombok 注解 
 * - 使用了 `@Getter` 和 `@Setter` 注解，自动生成所有属性的 getter 和 setter 方法。 
 * ### Swagger 注解 
 * - 使用了 `@ApiModel` 注解，标记这是一个模型对象。 
 * - 使用了 `@ApiModelProperty` 注解，为每个属性提供描述。 
 * ### 实现接口 
 * - 类实现了 `Serializable` 接口，表明它可以被序列化。 
 * ### 总结 
 * - 这个类主要用于封装管理课程所需的信息。 
 * - 使用了 Lombok 和 Swagger 注解来减少样板代码和生成 API 文档。 
 * - 需要检查并移除不必要的导入（如 `com.mysql.cj.xdevapi.JsonArray`）以避免潜在的冲突。 
 * 通过这些分析，可以更好地理解该类的功能和用途，并确保代码的清晰性和正确性。 
**/
@Getter
@Setter
@ApiModel
public class AdminCourseRes implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程资源编号")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty("课程简介")
	private String description;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty("课程封面")
	private String logo;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	@ApiModelProperty("学分")
	private Integer score;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程分类")
	private String categoryId;

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
	@ApiModelProperty("课程难度,type=51")
	private Integer difficulty;

	/**
	 * 等级
	 *
	 * @see Integer
	 */
	@ApiModelProperty("等级,type=9")
	private Integer rank;

	@ApiModelProperty("攻击知识点")
	private JSONArray attackTag;

	@ApiModelProperty("防御知识点")
	private JSONArray defenseTag;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程类型")
	private Integer courseType;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 讲师
	 *
	 * @see String
	 */
	@ApiModelProperty("课程讲师")
	private String lecturer;

	/**
	 * 课程数量
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String courseNum;

	/**
	 * 岗位
	 *
	 * @see Integer
	 */
	@ApiModelProperty("岗位")
	private Integer role;
}

