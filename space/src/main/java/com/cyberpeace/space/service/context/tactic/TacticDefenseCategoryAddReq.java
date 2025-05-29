package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 战法防御类别新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 该代码定义了一个名为 `TacticDefenseCategoryAddReq` 的 Java 类，用于表示战法防御类别新增请求体。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于接收战法防御类别新增的相关参数，是一个数据传输对象（DTO）。 
 * 2. **属性说明**： 
 * - `name`：表示名称，类型为 `String`。 
 * - `stage`：表示战术阶段，类型为 `Integer`，并带有 Swagger 注解 `@ApiModelProperty`，用于描述其含义（战术阶段，常量 type=48）。 
 * - `cover`：表示“盖”，类型为 `String`。 
 * 3. **注解使用**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，用于生成 API 文档时描述字段含义。 
 * 4. **作者与版本信息**： 
 * - 代码由 `SN` 编写，版本为 `1.0.0`，创建日期为 `2025/05/28`。 
 * 总结：这是一个简单的 DTO 类，用于封装战法防御类别新增请求所需的数据。代码结构清晰，注解使用合理。 
**/
@Getter
@Setter
public class TacticDefenseCategoryAddReq {

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;
	/**
	 * 阶段
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "战术阶段，常量type=48")
	private Integer stage;

	/**
	 * 盖
	 *
	 * @see String
	 */
	private String cover;
}

