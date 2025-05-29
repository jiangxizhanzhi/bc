package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Space Action 脱机工具
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceActionOfflineTool` 的 Java 类，位于包 `com.cyberpeace.space.service.context.frame` 中。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 这个类是一个脱机工具类，用于表示与 `Space Action` 相关的操作。 
 * - 它主要用于存储与 `Space Action` 相关的信息。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，简化代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为类的字段添加元数据描述，便于 API 文档生成。 
 * 3. **字段说明**： 
 * - `name`：表示工具的名称，通过 `@ApiModelProperty` 注解描述其含义。 
 * - `introduction`：表示工具的简述，同样通过 `@ApiModelProperty` 注解描述其含义。 
 * 4. **作者与版本信息**： 
 * - 类的文档注释中指明了作者为 `SN`，版本为 `1.0.0`，并且提供了日期和版本控制信息。 
 * 总结：这是一个简单的 POJO（Plain Old Java Object）类，主要用于存储 `Space Action` 脱机工具的相关信息，并通过注解增强了类的功能性和可读性。 
**/
@Getter
@Setter
public class SpaceActionOfflineTool {
    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "简述")
    private String introduction;
}

