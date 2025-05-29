package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `Mission` 的 Java 类，属于 `com.cyberpeace.cr.crbase.dao.entity` 包。以下是代码的主要分析和总结： 
 * ### 1. **类的基本信息** 
 * - **类名**：`Mission` 
 * - **继承关系**：该类继承自 `MissionBase`。 
 * - **注解**： 
 * - `@TableName("cr_mission")`：表示该类与数据库表 `cr_mission` 对应。 
 * - `@Getter` 和 `@Setter`：通过 Lombok 提供自动生成 getter 和 setter 方法。 
 * ### 2. **字段说明** 
 * - **`showWriteup`**： 
 * - 类型：`Boolean` 
 * - 注解：`@ApiModelProperty(value = "是否显示题解")` 
 * - 功能：表示是否显示题解。 
 * - **`roomId`**： 
 * - 类型：`String` 
 * - 注解：`@ApiModelProperty(value = "房间id")` 
 * - 功能：存储房间 ID。 
 * - **`missionTemplateId`**： 
 * - 类型：`String` 
 * - 注解：`@ApiModelProperty(value = "任务模板id")` 
 * - 功能：存储任务模板的 ID。 
 * ### 3. **功能描述** 
 * - 该类是用于表示任务实体的 Java Bean，通常用于数据传输或持久化。 
 * - 它通过 MyBatis-Plus 的 `@TableName` 注解映射到数据库表 `cr_mission`。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，便于生成 API 文档时描述字段含义。 
 * ### 4. **技术点** 
 * - **Lombok**：通过 `@Getter` 和 `@Setter` 减少了手动编写 getter 和 setter 方法的工作量。 
 * - **MyBatis-Plus**：通过 `@TableName` 实现了对象与数据库表的映射。 
 * - **Swagger**：通过 `@ApiModelProperty` 提供了字段的描述信息，方便接口文档生成。 
 * ### 总结 
 * 这是一个典型的 Java 数据库实体类，设计简洁且功能明确，适用于基于 MyBatis-Plus 的后端开发场景。它通过注解实现了与数据库表的映射以及 API 文档的描述，提升了开发效率。 
**/
@Getter
@Setter
@TableName("cr_mission")
public class Mission extends MissionBase {

	/**
	 * 显示 Writeup
	 */
	@ApiModelProperty(value = "是否显示题解")
	private Boolean showWriteup;

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "房间id")
	private String roomId;

	/**
	 * 任务模板id
	 */
	@ApiModelProperty(value = "任务模板id")
	private String missionTemplateId;
}

