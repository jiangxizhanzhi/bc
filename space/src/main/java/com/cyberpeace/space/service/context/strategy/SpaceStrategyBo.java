package com.cyberpeace.space.service.context.strategy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 空间策略Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 该代码定义了一个名为 `SpaceStrategyBo` 的 Java 类，属于 `com.cyberpeace.space.service.context.strategy` 包。以下是代码的简要分析： 
 * 1. **类功能**： 
 * - 这是一个业务对象（BO, Business Object），用于封装与“空间策略”相关的数据。 
 * 2. **注解与框架**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为某些字段提供元信息，便于生成 API 文档。 
 * 3. **字段说明**： 
 * - 类中包含多个字段，涵盖了资源 ID、名称、用户信息、内容、脚本、视图数、创建/更新时间、方法、路线、建议、依据以及内置状态等信息。 
 * - 字段类型包括 `String`, `Integer`, `Date`, 和 `Boolean`，覆盖了文本、数字、日期和布尔值等多种数据类型。 
 * 4. **用途**： 
 * - 该类主要用于存储和传递与“空间策略”相关的信息，可能是某个系统中的策略管理模块的一部分。 
 * 5. **设计特点**： 
 * - 字段命名清晰，每个字段都有对应的注释描述其含义。 
 * - 使用了 Lombok 和 Swagger 注解，提高了开发效率并增强了代码的可读性。 
 * 总体而言，这是一个典型的 Java Bean 类，专注于数据的封装和传输。 
**/
@Getter
@Setter
public class SpaceStrategyBo {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "")
	private String name;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 用户
	 *
	 * @see String
	 */
	private String user;

	/**
	 * 用户头像
	 *
	 * @see String
	 */
	private String userAvatar;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "策略内容")
	private String content;

	/**
	 * 步骤
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "操作步骤")
	private String step;

	/**
	 * 例
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "配置举例")
	private String example;

	/**
	 * 脚本
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "执行脚本")
	private String script;

	/**
	 * 脚本类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "脚本类型，常量type=44")
	private Integer scriptType;

	/**
	 * 框架id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "框架id")
	private String frameId;

	/**
	 * 视图
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "浏览次数")
	private Integer view;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
	private Date createTime;

	/**
	 * 更新时间
	 *
	 * @see Date
	 */
	private Date updateTime;

	/**
	 * 方法
	 *
	 * @see String
	 */
	@ApiModelProperty("策略执行成功判定方法")
	private String method;

	/**
	 * 路线
	 *
	 * @see String
	 */
	private String route;

	/**
	 * 建议
	 *
	 * @see String
	 */
	@ApiModelProperty("处置建议")
	private String suggest;

	/**
	 * 轨距
	 *
	 * @see String
	 */
	@ApiModelProperty("策略依据")
	private String gauge;
	/**
	 * 内置
	 *
	 * @see Boolean
	 */
	private Boolean builtin;
}

