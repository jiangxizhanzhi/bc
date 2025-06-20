package com.cyberpeace.cr.tool.controller.res;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 工具类别响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ToolCategoryRsp` 的 Java 类，该类用于表示工具类别的响应数据。以下是详细的分析和总结： 
 * ### 类名与包名 
 * - **包名**：`com.cyberpeace.cr.tool.controller.res` 
 * - **类名**：`ToolCategoryRsp` 
 * ### 类的功能 
 * - 这个类主要用于封装工具类别的响应信息。 
 * ### 类的属性 
 * - **cnName** (String)：工具类别的中文名称。 
 * - **enName** (String)：工具类别的英文名称。 
 * - **resourceId** (String)：资源 ID。 
 * - **type** (String)：工具类别的类型，具体值包括 `probe`（探测类）、`attack`（攻击类）、`control`（控制类）和 `defense`（防御类）。 
 * ### 注解 
 * - 使用了 Lombok 库中的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 注解 `@ApiModelProperty` 来描述每个属性的作用。 
 * ### 实现的接口 
 * - `Serializable` 接口：表明这个类的对象可以被序列化。 
 * ### 总结 
 * - `ToolCategoryRsp` 类是一个简单的数据传输对象（DTO），用于在工具类别相关的前后端交互中传递工具类别的详细信息。 
 * - 类中的属性通过 Swagger 注解进行了详细的描述，方便文档生成和接口测试。 
 * - 通过 Lombok 注解简化了 getter 和 setter 方法的编写。 
 * ### 作者信息 
 * - 作者：SN 
 * - 日期：2024年11月8日 
 * ### 示例用法 
 * ```java 
 * ToolCategoryRsp toolCategory = new ToolCategoryRsp(); 
 * toolCategory.setCnName("探测工具"); 
 * toolCategory.setEnName("Probe Tool"); 
 * toolCategory.setResourceId("12345"); 
 * toolCategory.setType("probe"); 
 * System.out.println(toolCategory.getCnName()); // 输出: 探测工具 
 * ``` 
 * 这段代码简洁明了，功能明确，符合常见的 API 响应对象的设计规范。 
**/
@Getter
@Setter
public class ToolCategoryRsp implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 中文名称
	 */
	@ApiModelProperty("分类中文名称")
	private String cnName;

	/**
	 * 中文名称
	 */
	@ApiModelProperty("分类英文名称")
	private String enName;

	/**
	 * 资源id
	 */
	@ApiModelProperty("ID")
	private String resourceId;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型， probe：探测类,attack：攻击类，control：控制类，defense：防御类")
	private String type;

}

