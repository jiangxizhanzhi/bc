package com.cyberpeace.cr.tool.dao.context;

import com.cyberpeace.common.model.PatternBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 工具列表查询
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ToolListQuery` 的 Java 类，该类继承自 `PatternBase`。这个类主要用于工具列表查询，并包含了一些字段用于存储查询条件。下面是详细的分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.cr.tool.dao.context` 
 * - **导入语句**： 
 * - `com.cyberpeace.common.model.PatternBase`: 这个类可能是一个基类或接口。 
 * - `io.swagger.annotations.ApiModelProperty`: 用于 Swagger 文档注解。 
 * - `lombok.Getter` 和 `lombok.Setter`: Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * ### 类注释 
 * - `ToolListQuery` 类用于工具列表查询。 
 * - 作者是 `SN`，日期是 `2024年11月8日`。 
 * ### 类属性 
 * - **category**: 分类，类型为 `String`。 
 * - **platforms**: 平台，类型为 `String`。 
 * - **cracked**: 授权状态，类型为 `Boolean`。 
 * - **type**: 类型，类型为 `String`。 
 * - **tag**: 标记，类型为 `String`。 
 * ### 注解 
 * - 使用了 `@ApiModelProperty` 注解来描述这些字段在 Swagger 文档中的名称。 
 * ### 继承 
 * - `ToolListQuery` 继承自 `PatternBase`，可能是为了复用一些基础方法或属性。 
 * ### 总结 
 * 这个类是一个数据传输对象（DTO），用于封装工具列表查询的参数。它通过继承 `PatternBase` 类和使用 Lombok 注解简化了代码编写。Swagger 注解使得这个类在生成 API 文档时能够提供更好的文档说明。 
**/
@Getter
@Setter
public class ToolListQuery extends PatternBase {

	/**
	 * 类别
	 */
	@ApiModelProperty("分类")
	private String category;

	/**
	 * 平台
	 */
	@ApiModelProperty("平台")
	private String platforms;

	/**
	 * 破获
	 */
	@ApiModelProperty("授权")
	private Boolean cracked;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 标记
	 */
	private String tag;

}

