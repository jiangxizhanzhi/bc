/**
 * 这段代码定义了一个名为 `ToolTypeCount` 的 Java 类，该类实现了 `Serializable` 接口。这个类主要用于表示工具类型的计数信息，并且使用了 Lombok 注解来简化 getter 和 setter 方法的编写。 
 * ### 主要组成部分 
 * 1. **包声明**： 
 * ```java 
 * package com.cyberpeace.cr.tool.controller.res; 
 * ``` 
 * 这行代码指定了类所在的包。 
 * 2. **导入语句**： 
 * ```java 
 * import com.cyberpeace.cr.tool.dao.context.MonthCount; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import java.io.Serializable; 
 * import java.util.List; 
 * ``` 
 * 导入了所需的类和注解。 
 * 3. **类声明**： 
 * ```java 
 * @Getter 
 * @Setter 
 * public class ToolTypeCount implements Serializable { 
 * ``` 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解自动生成 getter 和 setter 方法。 
 * - 实现了 `Serializable` 接口，表示这个类的对象可以被序列化。 
 * 4. **字段声明**： 
 * ```java 
 * /** 
 * * 名称 
 * *
 * @ApiModelProperty("名称") 
 * private String name; 
 * /** 
 * * 计数 
 * *
 * @ApiModelProperty("数量") 
 * private Integer count; 
 * /** 
 * * 计数列表 
 * *
 * @ApiModelProperty("变化数量") 
 * private List<MonthCount> countList; 
 * ``` 
 * - `name` 字段用于存储工具类型的名称。 
 * - `count` 字段用于存储工具类型的计数。 
 * - `countList` 字段是一个 `List` 类型的 `MonthCount` 对象列表，用于存储工具类型的计数变化情况。 
 * 5. **文档注释**： 
 * ```java 
 * /** 
 * * 工具类型计数 
 * * 
 * * @author SN 
 * * @date 2024/11/08 
 * *
 * ``` 
 * 提供了类的简短描述以及作者和日期信息。 
 * ### 总结 
 * - `ToolTypeCount` 类主要用于存储工具类型及其相关计数信息。 
 * - 使用了 Lombok 注解来简化代码，避免手动编写 getter 和 setter 方法。 
 * - 包含三个字段：`name`、`count` 和 `countList`，分别用于存储工具类型名称、计数以及计数变化列表。 
 * - 通过实现 `Serializable` 接口，使得对象可以被序列化。 
**/
package com.cyberpeace.cr.tool.controller.res;

import com.cyberpeace.cr.tool.dao.context.MonthCount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 工具类型计数
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class ToolTypeCount implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 计数
	 */
	@ApiModelProperty("数量")
	private Integer count;

	/**
	 * 计数列表
	 */
	@ApiModelProperty("变化数量")
	private List<MonthCount> countList;

}
