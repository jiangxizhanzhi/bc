package com.cyberpeace.cr.tool.service.context;

import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 战术工具
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `TacticTool` 的 Java 类，用于表示战术工具的相关信息。以下是对其主要特性的简要分析： 
 * 1. **包声明**：该类位于 `com.cyberpeace.cr.tool.service.context` 包中。 
 * 2. **导入语句**： 
 * - 导入了 Lombok 库中的 `Getter` 和 `Setter` 注解，用于自动生成 getter 和 setter 方法。 
 * - 导入了 Swagger 注解库中的 `ApiModelProperty` 注解，用于描述 API 中的属性。 
 * - 导入了 `java.util.List` 类。 
 * 3. **类注释**： 
 * - 使用 Javadoc 标签定义了类的描述信息，包括作者（SN）和创建日期（2024年11月8日）。 
 * 4. **字段**： 
 * - `resourceId`：资源ID，用于标识工具的唯一性。 
 * - `name`：工具的名称。 
 * - `type`：工具的类型。 
 * - `category`：工具类型，使用 Swagger 注解进行描述。 
 * - `categoryName`：工具类型的名称，使用 Swagger 注解进行描述。 
 * - `categoryI18nName`：工具类型的国际化名称，使用 Swagger 注解进行描述。 
 * - `introduction`：工具的介绍，使用 Swagger 注解进行描述。 
 * - `platforms`：工具支持的平台列表，使用 Swagger 注解进行描述。 
 * - `toolFiles`：与该工具相关的文件列表，使用 Swagger 注解进行描述。 
 * 5. **注解**： 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解自动生成 getter 和 setter 方法。 
 * - 使用 Swagger 的 `@ApiModelProperty` 注解对属性进行描述，便于生成 API 文档。 
 * 6. **功能**：`TacticTool` 类主要用于封装战术工具的信息，并且通过 Swagger 注解为这些信息提供详细的描述，以便于 API 文档的生成。 
 * 总结来说，这个类是一个数据传输对象（DTO），用于在系统内部或与外部系统交互时传递战术工具的相关信息。 
**/
@Getter
@Setter
public class TacticTool {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	private String type;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "工具类型")
	private String category;

	/**
	 * 工具类型名称
	 */
	@ApiModelProperty(value = "工具类型")
	private String categoryName;

	/**
	 * 工具类型名称
	 */
	@ApiModelProperty(value = "工具类型名称")
	private String categoryI18nName;

	/**
	 * 介绍
	 */
	@ApiModelProperty("介绍")
	private String introduction;

	@ApiModelProperty("平台名称")
	private List<String> platforms;

	@ApiModelProperty("工具文件")
	private List<ToolFile> toolFiles;
}

