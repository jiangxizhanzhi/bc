package com.cyberpeace.gateway.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 理论列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `TheoryListReq` 的 Java 类，用于表示理论列表请求的数据模型。以下是对其主要特性的简要分析： 
 * 1. **继承关系**: 
 * - `TheoryListReq` 类继承自 `PageReqBase` 类。这表明该类可能用于分页查询，包含一些与分页相关的属性或方法。 
 * 2. **注解使用**: 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解，这简化了属性的访问器和修改器的生成，无需手动编写 getter 和 setter 方法。 
 * - 使用了 Swagger 注解 (`@ApiModel` 和 `@ApiModelProperty`) 来描述数据模型和字段信息，便于文档生成和 API 测试工具识别。 
 * 3. **属性**: 
 * - `categoryId`: 字符串类型，表示类别 ID，用于过滤理论列表中的某个类别。 
 * - `difficulty`: 整型，表示难度等级，取值为 0（初级）、1（中级）或 2（高级）。 
 * - `theoryType`: 整型，表示理论类型，允许的值有 0（单选题）、1（多选题）、2（判断题）、3（填空题）和 4（简答题）。 
 * 4. **功能**: 
 * - 这个类主要用于封装从客户端传递过来的查询参数，以便后端处理逻辑可以方便地获取这些参数进行数据检索或过滤。 
 * 总结来说，`TheoryListReq` 是一个数据传输对象（DTO），用于接收客户端请求中关于理论列表查询的参数，并提供了良好的可读性和文档支持。 
**/
@Getter
@Setter
@ApiModel
public class TheoryListReq extends PageReqBase {

	/**
	 * 类别id
	 */
	@ApiModelProperty(name = "category_id", value = "方向")
	private String categoryId;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	private Integer difficulty;

	/**
	 * 理论类型
	 */
	@ApiModelProperty(name = "theory_type", value = "题目类型:0-单选, 1-多选, 2-判断, 3-填空, 4-简答", allowableValues = "0, 1, 2, 3, 4")
	private Integer theoryType;

}

