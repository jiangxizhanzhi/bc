package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 红点
 *
 * @author SN
 * @date 2023/06/08
 */
/**
 * 这段代码定义了一个名为 `BadgeBo` 的 Java 类，用于表示“红点”相关的业务对象。以下是代码的简要分析： 
 * 1. **类的基本信息**： 
 * - 类名：`BadgeBo` 
 * - 包名：`com.cyberpeace.cs.service.context` 
 * - 实现了 `Serializable` 接口，表明这是一个可序列化的对象。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段 `show` 添加了描述性注释，表示该字段用于指示是否显示红点。 
 * 3. **字段说明**： 
 * - `show`：布尔类型字段，用于标识红点是否需要显示。字段上有 `@ApiModelProperty` 注解，描述其用途。 
 * - 注释部分提到还有一个字段 `number`，但被注释掉了，可能暂时未使用。 
 * 4. **功能概述**： 
 * - 这个类主要用于封装与红点相关的数据信息（如是否显示），便于在系统中传递和使用。 
 * 总结：`BadgeBo` 是一个简单的数据传输对象（DTO），用于表示红点的状态信息，支持序列化，并通过注解增强了可读性和文档生成能力。 
**/
@Getter
@Setter
public class BadgeBo implements Serializable {

	/**
	 * 显示
	 */
	@ApiModelProperty(value = "是否显示")
	private Boolean show;

	// @ApiModelProperty(value = "")
	// private Integer number;
}

